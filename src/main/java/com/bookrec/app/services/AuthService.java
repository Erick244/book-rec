package com.bookrec.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookrec.app.models.dto.auth.SignInDto;
import com.bookrec.app.models.dto.auth.SignInResponseDto;
import com.bookrec.app.models.dto.auth.SignUpDto;
import com.bookrec.app.models.entities.User;
import com.bookrec.app.models.repositories.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	public ResponseEntity<?> signUp(SignUpDto signUpDto) {
		try {
			String username = signUpDto.username();
			String password = signUpDto.password();
			String confirmPassword = signUpDto.confirmPassword();
			
			User user = this.userRepository.findByUsername(username);
			
			if (user != null) {
				return ResponseEntity.badRequest().body("Username already used");
			}
			
			if (confirmPassword == null) {
				return ResponseEntity.badRequest().body("The \"confirmPassword\" field has not been filled in.");
			}
			
			Boolean passwordNotIsValid = !signUpDto.passwordIsValid();
			if (passwordNotIsValid) {
				return ResponseEntity.badRequest().body("The password must be a minimum of 8 characters and a maximum of 16");
			}
			
			Boolean passwordNotMatch = !signUpDto.passwordsMatch();
			if (passwordNotMatch) {
				return ResponseEntity.badRequest().body("Passwords don't match");
			}
			
			String passwordEncrypted = new BCryptPasswordEncoder().encode(password);
			
			User newUser = new User(username, passwordEncrypted);
			this.userRepository.save(newUser);
			
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	public ResponseEntity<SignInResponseDto> signIn(SignInDto signInDto) {
		String username = signInDto.username();
		String password = signInDto.password();
		
		User user = this.userRepository.findByUsername(username);
		
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		
		UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication auth = this.authenticationManager.authenticate(loginToken);
		
		String jwtAuthToken = this.jwtService.createToken((UserDetails) auth.getPrincipal());
		
		return ResponseEntity.ok(new SignInResponseDto(user, jwtAuthToken));
	}
}
