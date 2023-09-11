package com.bookrec.app.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;

@Service
public class JwtService {

	@Value("${jwt.token.secret}")
	private String secret;
	
	public String createToken(UserDetails user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			String token = JWT.create()
					.withIssuer("bookrec")
					.withSubject(user.getUsername())
					.withExpiresAt(this.createExpiryTime(60 * 60 * 24 * 7)) //7 days
					.sign(algorithm);
			
			return token;
		} catch (JWTCreationException e) {
			throw new RuntimeException("[JWTService] Error creating JWT token: ", e);
		}
	}
	
	private Instant createExpiryTime(Integer timeInSeconds) {
		ZoneOffset brazilZoneOffset = ZoneOffset.of("-03:00");
		
		Instant expiryTime = LocalDateTime.now().plusSeconds(timeInSeconds).toInstant(brazilZoneOffset);
		
		return expiryTime;
	}
	
	public String decodeToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			String subject = JWT.require(algorithm)
					.withIssuer("bookrec")
					.build()
					.verify(token)
					.getSubject();
			
			return subject;
		} catch (JWTDecodeException  e) {
			return null;
		}
	}
}
