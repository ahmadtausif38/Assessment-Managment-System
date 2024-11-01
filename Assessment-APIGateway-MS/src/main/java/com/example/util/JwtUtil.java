package com.example.util;


import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	private SecretKey getKey() {
		
		byte[] keyBytes=Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
	// Validate the token
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUserName(token);
        System.out.println("*********** JWT Token Validation is going in API Gateway *************");
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
	

	// extract the username from jwt token
	public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract claims
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    
    // Check if token is expired
    private Boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

 
   
}
