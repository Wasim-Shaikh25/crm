package com.synterra.lens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synterra.lens.entity.AuthenticationRequest;
import com.synterra.lens.entity.AuthenticationResponse;
import com.synterra.lens.entity.ResetPasswordRequest;
import com.synterra.lens.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/authenticate")
	@Tag(name = "auth")
	public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authService.authenticate(request));
	}

	@PostMapping("/resetPassword")
	@Tag(name = "auth")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetRequest) {
		return ResponseEntity.ok(authService.resetPassword(resetRequest));
	}

}
