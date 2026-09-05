package com.synterra.lens.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.synterra.lens.config.CustomUserDetails;
import com.synterra.lens.entity.AuthenticationRequest;
import com.synterra.lens.entity.AuthenticationResponse;
import com.synterra.lens.entity.Authority;
import com.synterra.lens.entity.ResetPasswordRequest;
import com.synterra.lens.entity.Role;
import com.synterra.lens.entity.User;
import com.synterra.lens.exception.LensServiceException;
import com.synterra.lens.repository.UserRepository;
import com.synterra.lens.utils.DateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;

	private final JwtService jwtService;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final DateUtil dateUtil;


	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmpId(), request.getPassword()));
			User user = userRepository.findByEmpId(request.getEmpId())
					.orElseThrow(() -> new LensServiceException("User not found", HttpStatus.NOT_FOUND));
			if (user.isResetPasswordRequired()) {
				return AuthenticationResponse.builder().errorMessage("Please reset your password").build();
			}
			Set<GrantedAuthority> authorities = getAuthoritiesByEmpId(user.getEmpId());
			if (authorities == null || authorities.isEmpty()) {
				throw new LensServiceException("User authorities not found", HttpStatus.NOT_FOUND);
			}
			CustomUserDetails userDetails = new CustomUserDetails(user, authorities);
			String jwtToken = jwtService.generateToken(userDetails);
			return AuthenticationResponse.builder().accessToken(jwtToken).build();
		} catch (UsernameNotFoundException e) {
			return AuthenticationResponse.builder().errorMessage("User not found!").build();
		}
	}

	private Set<GrantedAuthority> getAuthoritiesByEmpId(String empId) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		User user = userRepository.findByEmpId(empId)
				.orElseThrow(() -> new LensServiceException("User not found", HttpStatus.NOT_FOUND));
		for (Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			for (Authority authority : role.getAuthorities()) {
				authorities.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
			}
		}
		return authorities;
	}

	public String resetPassword(ResetPasswordRequest resetRequest) {
		User user = userRepository.findByEmpId(resetRequest.getEmpId())
				.orElseThrow(() -> new LensServiceException("User not found", HttpStatus.NOT_FOUND));
		if (!passwordEncoder.matches(resetRequest.getOldPassword(), user.getPassword())) {
			throw new LensServiceException("Old password is incorrect", HttpStatus.UNAUTHORIZED);
		}
		user.setPassword(passwordEncoder.encode(resetRequest.getNewPassword()));
		user.setResetPasswordRequired(false);
		user.setUpdatedOn(dateUtil.getCurrentDateTime());
		user.setUpdatedByUser(resetRequest.getEmpId());
		userRepository.save(user);
		return "Password reset successfully";
	}
}
