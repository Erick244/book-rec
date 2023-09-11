package com.bookrec.app.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bookrec.app.models.entities.User;
import com.bookrec.app.models.repositories.UserRepository;
import com.bookrec.app.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
			throws ServletException, IOException {
		String token = this.extractAuthBearerToken(request);
		
		if (token != null) {
			String username = this.jwtService.decodeToken(token);
			User user = this.userRepository.findByUsername(username);
			
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String extractAuthBearerToken(HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		
		if (authToken == null) return null;
		
		Boolean notABearerToken = !authToken.startsWith("Bearer");
		
		if (notABearerToken) return null;
		
		return authToken.replace("Bearer ", "");
		
	}

}
