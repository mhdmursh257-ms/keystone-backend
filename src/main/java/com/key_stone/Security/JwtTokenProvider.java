package com.key_stone.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt.secret:9a2f8c7e1d4b6a8c0e2f4a6b8d0c2e4f6a8b0c2d4e6f8a0b2c4d6e8f0a2b4c6e}")
	private String jwtSecret;
	
	@Value("${app.jwt.expiration-ms:86400000}")
	private long jwtExpirationMs;
	
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
		
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String generateToken(String email, String role) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
		
		return Jwts.builder()
				.setSubject(email)
				.claim("role", role)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String getUsernameFromJwt(String token) {
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
	}
	
	public boolean validateToken(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
			return true;
		} catch (JwtException | IllegalArgumentException ex) {
			return false;
		}
	}
}
