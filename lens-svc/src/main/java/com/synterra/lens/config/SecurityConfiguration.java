package com.synterra.lens.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private static final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

	private final AuthenticationProvider authenticationProvider;

	private final JwtAuthFilter jwtAuthFilter;

	@Value("${swagger.url.patterns}")
	private String[] swaggerUrlPatterns;
	
	@Value("${security.endpoint.config.file}")
	private String endpoindClassPath;
	
	@Value("${security.endpoint.config.enabled:false}")
	private String apiSecure;

	@Value("${cors.allowed-origins:http://localhost:3000}")
	private List<String> allowedOrigins;

	private List<EndpointConfig> endpointConfigs;
	private boolean configurationLoaded = false;
	private boolean isApiSecure;

	@PostConstruct
	public void initializeApiSecure() {
		this.isApiSecure = Boolean.parseBoolean(apiSecure);
		// Load endpoint configurations only if API is secure
		if (isApiSecure) {
			loadEndpointConfigurations();
		}
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		if (isApiSecure) {
			// Secure API configuration with dynamic endpoints
			return http
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(auth -> {
					// Public endpoints - always accessible
					auth.requestMatchers(swaggerUrlPatterns).permitAll()
						.requestMatchers("/auth/authenticate", "/auth/resetPassword").permitAll()
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers("/error").permitAll();

					// Configure endpoints from JSON file
					configureDynamicEndpoints(auth);

					// Default DENY all other requests
					auth.anyRequest().denyAll();
				})
				.sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(ex -> ex
					.authenticationEntryPoint((request, response, authException) -> {
						log.warn("Unauthorized access: {} {}", request.getMethod(), request.getRequestURI());
						response.sendError(401, "Unauthorized");
					})
					.accessDeniedHandler((request, response, accessDeniedException) -> {
						log.warn("Access denied: {} {}", request.getMethod(), request.getRequestURI());
						response.sendError(403, "Access Denied");
					})
				)
				.build();
		} else {
			// Non-secure API configuration (original simple config)
			return http
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.authorizeHttpRequests(auth -> auth
					.requestMatchers(swaggerUrlPatterns).permitAll()
					.requestMatchers(
						"/auth/authenticate",
						"/auth/resetPassword",
						"/lens/**",
						"/user/**")
					.permitAll()
					.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
		}
	}

	public void loadEndpointConfigurations() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ClassPathResource resource = new ClassPathResource(endpoindClassPath);

			if (!resource.exists()) {
				log.error("SECURITY ALERT: endpoint-config.json NOT FOUND! All API endpoints will be COMPLETELY BLOCKED!");
				configurationLoaded = false;
				return;
			}

			InputStream inputStream = resource.getInputStream();
			endpointConfigs = mapper.readValue(inputStream, new TypeReference<List<EndpointConfig>>() {
			});

			log.info("Successfully loaded {} endpoint configurations", endpointConfigs.size());
			configurationLoaded = true;

			// Validate configurations
			validateConfigurations();

		} catch (IOException e) {
			log.error("CRITICAL ERROR: Failed to load endpoint configurations: {}", e.getMessage());
			configurationLoaded = false;
			throw new RuntimeException("Failed to load endpoint configurations", e);
		}
	}

	private void validateConfigurations() {
		if (endpointConfigs == null)
			return;

		for (EndpointConfig config : endpointConfigs) {
			if (config.getPath() == null || config.getMethods() == null || config.getAuthorities() == null) {
				log.warn("Invalid configuration found: {}", config.getPath());
			}
		}
	}

	private void configureDynamicEndpoints(
			AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
		if (!configurationLoaded || endpointConfigs == null || endpointConfigs.isEmpty()) {
			log.error("No endpoint configurations available - ALL API ACCESS BLOCKED");
			return;
		}

		log.info("Applying security rules for {} endpoints...", endpointConfigs.size());

		for (EndpointConfig config : endpointConfigs) {
			String path = config.getPath();
			List<String> methods = config.getMethods();
			List<String> authorities = config.getAuthorities();

			// Skip invalid configurations
			if (path == null || methods == null || authorities == null || methods.isEmpty() || authorities.isEmpty()) {
				log.warn("Skipping invalid configuration: {}", path);
				continue;
			}

			String[] authoritiesArray = authorities.toArray(new String[0]);

			// Apply security rule for each HTTP method
			for (String method : methods) {
				HttpMethod httpMethod = parseHttpMethod(method);

				if (httpMethod != null) {
					try {
						if (authoritiesArray.length == 1) {
							auth.requestMatchers(httpMethod, path).hasAuthority(authoritiesArray[0]);
							log.info("{} {} → Requires: {}", httpMethod, path, authoritiesArray[0]);
						} else {
							auth.requestMatchers(httpMethod, path).hasAnyAuthority(authoritiesArray);
							log.info("{} {} → Requires ANY: {}", httpMethod, path,
									String.join(", ", authoritiesArray));
						}
					} catch (Exception e) {
						log.error("Failed to configure {} {}: {}", httpMethod, path, e.getMessage());
					}
				} else {
					log.warn("Invalid HTTP method: {} for path: {}", method, path);
				}
			}
		}

		log.info("Security configuration completed - undefined paths will be DENIED");
	}

	private HttpMethod parseHttpMethod(String method) {
		try {
			return HttpMethod.valueOf(method.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	private UrlBasedCorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(allowedOrigins);
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public CorsFilter corsFilter() {
		return new CorsFilter(corsConfigurationSource());
	}

	public static class EndpointConfig {
		
		private String path;
		private List<String> methods;
		private List<String> authorities;

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public List<String> getMethods() {
			return methods;
		}

		public void setMethods(List<String> methods) {
			this.methods = methods;
		}

		public List<String> getAuthorities() {
			return authorities;
		}

		public void setAuthorities(List<String> authorities) {
			this.authorities = authorities;
		}
	}
}