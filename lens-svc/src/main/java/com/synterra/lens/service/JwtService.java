package com.synterra.lens.service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.synterra.lens.config.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}") 
    private long expiration; 

    // NOTE: in-memory denylist - cleared on restart and not shared across instances.
    // For multi-instance deployments move this to Redis/DB or use short-lived tokens + refresh tokens.
    private final Set<String> invalidatedTokens = ConcurrentHashMap.newKeySet();

    public String generateToken(CustomUserDetails user) {
        return Jwts.builder().setSubject(user.getUsername())
                .claim("authorities", populateAuthorities(user.getAuthorities()))
                .claim("designation", user.getUser().getDesignation())
                .claim("departments", user.getUser().getDepartments())
                .claim("branchs", user.getUser().getBranches())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) 
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }
 
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractEmpId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenInvalidated(token));
    }

    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }

    private boolean isTokenInvalidated(String token) {
        return invalidatedTokens.contains(token);
    }
}
