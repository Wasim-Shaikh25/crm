package com.synterra.lens.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.synterra.lens.exception.LensServiceException;
import com.synterra.lens.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);


	private final JwtService jwtService;

	private final UserDetailsService userDetailsService;

	@Value("${security.endpoint.config.enabled}")
	private String apiSecure;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// If security is disabled, skip all JWT processing
		boolean isApiSecure = Boolean.parseBoolean(apiSecure);
		if (!isApiSecure) {
			filterChain.doFilter(request, response);
			return;
		}

		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String empId;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(7);
		empId = jwtService.extractEmpId(jwt);
		if (empId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(empId);
			if (userDetails == null) {
				throw new LensServiceException("User not found", HttpStatus.UNAUTHORIZED);
			}
			if (jwtService.isTokenValid(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			} else {
				throw new LensServiceException("Invalid JWT token", HttpStatus.UNAUTHORIZED);
			}
		}
		filterChain.doFilter(request, response);
	}

	// public void logout(HttpServletRequest request, HttpServletResponse response)
	// {
	// String authHeader = request.getHeader("Authorization");
	// if (authHeader != null && authHeader.startsWith("Bearer ")) {
	// String jwt = authHeader.substring(7);
	// String empId = jwtService.extractEmpId(jwt);
	// if (empId != null) {
	// UserDetails userDetails = userDetailsService.loadUserByUsername(empId);
	// if (jwtService.isTokenValid(jwt, userDetails)) {
	// jwtService.invalidateToken(jwt);
	// SecurityContextHolder.clearContext();
	// Cookie cookie = new Cookie("Authorization", null);
	// cookie.setPath("/");
	// cookie.setHttpOnly(true);
	// cookie.setMaxAge(0);
	// response.addCookie(cookie);
	// }
	// }
	// }
	// }
}