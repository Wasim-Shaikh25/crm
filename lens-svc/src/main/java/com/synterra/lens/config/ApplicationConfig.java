package com.synterra.lens.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.synterra.lens.entity.Authority;
import com.synterra.lens.entity.Role;
import com.synterra.lens.entity.User;
import com.synterra.lens.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final UserRepository userRepository;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailsService() {
	    return empId -> {
	        User user = userRepository.findByEmpId(empId)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	        Set<GrantedAuthority> authorities = new HashSet<>();
	        for (Role role : user.getRoles()) {
	            authorities.add(new SimpleGrantedAuthority(role.getRoleName())); 
	            for (Authority authority : role.getAuthorities()) {
	                authorities.add(new SimpleGrantedAuthority(authority.getAuthorityName())); 
	            }
	        }
	        return new CustomUserDetails(user, authorities);
	    };
	}



	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
