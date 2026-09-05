package com.synterra.lens.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.synterra.lens.config.CustomUserDetails;
import com.synterra.lens.entity.User;

@Component
public class UserDetailUtils {
	
	public User getUserDetail() {
		CustomUserDetails customUserDetails = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal().getClass().equals(CustomUserDetails.class)) {
			customUserDetails = (CustomUserDetails) authentication.getPrincipal();
			return customUserDetails.getUser();
		}
		return null;
	}
}
