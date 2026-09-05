package com.synterra.lens.utils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.synterra.lens.config.CustomUserDetails;
import com.synterra.lens.entity.User;

@Component
public class EntityHelper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            return customUserDetails.getUser();
        }

        return null; 
    }
    public LocalDateTime getCurrentDateTime() {
        String formattedDateTime = LocalDateTime.now().format(FORMATTER);
        return LocalDateTime.parse(formattedDateTime, FORMATTER);
    }

    public void setCommonFields(Object entity) {
        LocalDateTime now = getCurrentDateTime();
        User currentUser = getCurrentUser();
        String currentUserName = (currentUser != null) ? currentUser.getEmpId() : "System";

        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                if ("createdOn".equals(field.getName())) {
                    Object fieldValue = field.get(entity);
                    if (fieldValue == null) {
                        field.set(entity, now);
                    }
                } else if ("updatedOn".equals(field.getName())) {
                    // Always set updatedOn
                    field.set(entity, now);
                } else if ("createdByUser".equals(field.getName())) {
                    Object fieldValue = field.get(entity);
                    if (fieldValue == null || ((String)fieldValue).trim().isEmpty()) {
                        field.set(entity, currentUserName);
                    }
                } else if ("updatedByUser".equals(field.getName())) {
                    // Always set updatedByUser
                    field.set(entity, currentUserName);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error setting field value for " + field.getName(), e);
            }
        }
    }
    
    public void setUpdateFields(Object entity) {
        LocalDateTime now = getCurrentDateTime();
        User currentUser = getCurrentUser();
        String currentUserName = (currentUser != null) ? currentUser.getEmpId() : "System";

        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {

                if ("updatedOn".equals(field.getName())) {
                    field.set(entity, now);               
                } else if ("updatedByUser".equals(field.getName())) {
                    field.set(entity, currentUserName);  
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(
                    "Error setting field value for " + field.getName(), e);
            }
        }
    }

}
