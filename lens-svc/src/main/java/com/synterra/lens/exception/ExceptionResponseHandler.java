package com.synterra.lens.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionResponseHandler.class);

    // YOUR EXISTING EXCEPTION HANDLERS - KEEPING THEM AS IS
    @ExceptionHandler(LensServiceException.class)
    public final ResponseEntity<ErrorResponse> handleLensServiceException(LensServiceException ex,
            WebRequest request) {
        logger.error("LensServiceException: {} - Path: {}", ex.getErrrorMessage(), request.getDescription(false));
        
        ErrorResponse errorResponse = new ErrorResponse(LocalDate.now(),
                ex.getHttpStatus(), ex.getErrrorMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllException(Exception ex,
            WebRequest request) {
        logger.error("General Exception: {} - Path: {}", ex.getMessage(), request.getDescription(false));
        
        ErrorResponse errorResponse = new ErrorResponse(LocalDate.now(), 
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ADDITIONAL SECURITY-SPECIFIC EXCEPTION HANDLERS
    
    /**
     * Handle JWT related exceptions with detailed response
     */
    @ExceptionHandler({
        io.jsonwebtoken.ExpiredJwtException.class,
        io.jsonwebtoken.MalformedJwtException.class,
        io.jsonwebtoken.UnsupportedJwtException.class,
        io.jsonwebtoken.security.SignatureException.class
    })
    public ResponseEntity<Map<String, Object>> handleJwtException(Exception ex, WebRequest request) {
        logger.error("JWT Exception: {} - Path: {}", ex.getMessage(), request.getDescription(false));
        
        String message;
        String errorCode;
        String suggestion;
        
        if (ex instanceof io.jsonwebtoken.ExpiredJwtException) {
            message = "Token has expired. Please login again";
            errorCode = "TOKEN_EXPIRED";
            suggestion = "Please login again to get a new token";
        } else if (ex instanceof io.jsonwebtoken.MalformedJwtException) {
            message = "Invalid token format";
            errorCode = "MALFORMED_TOKEN";
            suggestion = "Please check your token format and try again";
        } else if (ex instanceof io.jsonwebtoken.UnsupportedJwtException) {
            message = "Unsupported token type";
            errorCode = "UNSUPPORTED_TOKEN";
            suggestion = "Please use a supported token type";
        } else if (ex instanceof io.jsonwebtoken.security.SignatureException) {
            message = "Invalid token signature";
            errorCode = "INVALID_SIGNATURE";
            suggestion = "Token signature is invalid, please login again";
        } else {
            message = "Invalid token";
            errorCode = "INVALID_TOKEN";
            suggestion = "Please provide a valid token";
        }
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", "Authentication Error");
        errorResponse.put("message", message);
        errorResponse.put("errorCode", errorCode);
        errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("path", request.getDescription(false));
        errorResponse.put("suggestion", suggestion);
        
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle access denied exception
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {
        
        logger.error("Access Denied: {} - Path: {}", ex.getMessage(), request.getDescription(false));
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", "Authorization Error");
        errorResponse.put("message", "You don't have permission to access this resource");
        errorResponse.put("errorCode", "INSUFFICIENT_PRIVILEGES");
        errorResponse.put("status", HttpStatus.FORBIDDEN.value());
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("path", request.getDescription(false));
        errorResponse.put("suggestion", "Please contact administrator to get required permissions");
        
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * Handle illegal argument exception
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        logger.error("Illegal Argument: {} - Path: {}", ex.getMessage(), request.getDescription(false));
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", "Invalid request parameters");
        errorResponse.put("errorCode", "INVALID_PARAMETERS");
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("path", request.getDescription(false));
        errorResponse.put("suggestion", "Please check your request parameters and try again");
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle security-related runtime exceptions
     */
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Map<String, Object>> handleSecurityException(
            SecurityException ex, WebRequest request) {
        
        logger.error("Security Exception: {} - Path: {}", ex.getMessage(), request.getDescription(false));
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", "Security Error");
        errorResponse.put("message", "Security constraint violation");
        errorResponse.put("errorCode", "SECURITY_VIOLATION");
        errorResponse.put("status", HttpStatus.FORBIDDEN.value());
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("path", request.getDescription(false));
        errorResponse.put("suggestion", "Please ensure you have proper permissions for this operation");
        
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * Handle null pointer exceptions that might occur during authentication
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> handleNullPointerException(
            NullPointerException ex, WebRequest request) {
        
        logger.error("Null Pointer Exception: {} - Path: {}", ex.getMessage(), request.getDescription(false));
        
        // Check if it's authentication related
        String path = request.getDescription(false);
        if (path.contains("auth") || path.contains("jwt") || path.contains("token")) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Authentication Error");
            errorResponse.put("message", "Invalid authentication data");
            errorResponse.put("errorCode", "INVALID_AUTH_DATA");
            errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
            errorResponse.put("timestamp", LocalDateTime.now().toString());
            errorResponse.put("path", request.getDescription(false));
            errorResponse.put("suggestion", "Please check your authentication credentials and try again");
            
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
        
        // For other null pointer exceptions, return generic error
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", "An unexpected error occurred");
        errorResponse.put("errorCode", "NULL_POINTER_ERROR");
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("path", request.getDescription(false));
        errorResponse.put("suggestion", "Please try again or contact support if the problem persists");
        
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle authentication related exceptions
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(
            BadCredentialsException ex, WebRequest request) {
        
        logger.error("Bad Credentials: {} - Path: {}", ex.getMessage(), request.getDescription(false));
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", "Authentication Error");
        errorResponse.put("message", "Invalid username or password");
        errorResponse.put("errorCode", "INVALID_CREDENTIALS");
        errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("path", request.getDescription(false));
        errorResponse.put("suggestion", "Please check your credentials and try again");
        
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle user not found exception
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFoundException(
            UsernameNotFoundException ex, WebRequest request) {
        
        logger.error("User Not Found: {} - Path: {}", ex.getMessage(), request.getDescription(false));
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", "Authentication Error");
        errorResponse.put("message", "User not found");
        errorResponse.put("errorCode", "USER_NOT_FOUND");
        errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("path", request.getDescription(false));
        errorResponse.put("suggestion", "Please check your employee ID or contact administrator");
        
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Bean-validation failures: return a field -> message map.
     * Overrides the inherited handler (annotating this with @ExceptionHandler
     * alongside the base class method causes an ambiguous-mapping startup error).
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            org.springframework.web.bind.MethodArgumentNotValidException ex,
            org.springframework.http.HttpHeaders headers,
            org.springframework.http.HttpStatusCode status,
            WebRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> fieldErrors.put(fe.getField(), fe.getDefaultMessage()));

        logger.error("Validation failed: {} - Path: {}", fieldErrors, request.getDescription(false));

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("error", "Validation Error");
        errorResponse.put("message", "One or more fields failed validation");
        errorResponse.put("fieldErrors", fieldErrors);
        errorResponse.put("errorCode", "VALIDATION_FAILED");
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("path", request.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
//     */
//    @ExceptionHandler(DisabledException.class)
//    public ResponseEntity<Map<String, Object>> handleDisabledException(
//            DisabledException ex, WebRequest request) {
//        
//        logger.error("Account Disabled: {} - Path: {}", ex.getMessage(), request.getDescription(false));
//        
//        Map<String, Object> errorResponse = new HashMap<>();
//        errorResponse.put("success", false);
//        errorResponse.put("error", "Authentication Error");
//        errorResponse.put("message", "User account is disabled");
//        errorResponse.put("errorCode", "ACCOUNT_DISABLED");
//        errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
//        errorResponse.put("timestamp", LocalDateTime.now().toString());
//        errorResponse.put("path", request.getDescription(false));
//        errorResponse.put("suggestion", "Please contact administrator to activate your account");
//        
//        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
//    }
//
//    /**
//     * Handle account locked exception
//     */
//    @ExceptionHandler(LockedException.class)
//    public ResponseEntity<Map<String, Object>> handleLockedException(
//            LockedException ex, WebRequest request) {
//        
//        logger.error("Account Locked: {} - Path: {}", ex.getMessage(), request.getDescription(false));
//        
//        Map<String, Object> errorResponse = new HashMap<>();
//        errorResponse.put("success", false);
//        errorResponse.put("error", "Authentication Error");
//        errorResponse.put("message", "User account is locked");
//        errorResponse.put("errorCode", "ACCOUNT_LOCKED");
//        errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
//        errorResponse.put("timestamp", LocalDateTime.now().toString());
//        errorResponse.put("path", request.getDescription(false));
//        errorResponse.put("suggestion", "Please contact administrator to unlock your account");
//        
//        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
//    }
}