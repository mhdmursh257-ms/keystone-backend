package com.key_stone.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.key_stone.Entity.UserAuth;
import com.key_stone.Repository.UserAuthRepository;
import com.key_stone.Security.JwtTokenProvider;
import com.key_stone.dto.LoginRequest;
import com.key_stone.dto.LoginResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final UserAuthRepository userAuthRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	
	public AuthController(UserAuthRepository userAuthRepository, 
							PasswordEncoder passwordEncoder,
							JwtTokenProvider jwtTokenProvider) {
		this.userAuthRepository = userAuthRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
		
		UserAuth user = userAuthRepository.findByUserEmail(loginRequest.getEmail())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
		
		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
		}
		
		// token generate
		String token = jwtTokenProvider.generateToken(user.getUserEmail(), user.getRole().name());
		
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(token);
		loginResponse.setUserId(user.getId());
		loginResponse.setEmail(user.getUserEmail());
		loginResponse.setRole(user.getRole().name());
		
		return ResponseEntity.ok(loginResponse);
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody UserAuth user) {
		if(userAuthRepository.findByUserEmail(user.getUserEmail()).isPresent()) {
			return ResponseEntity.badRequest().body("Email is already in use: " + user.getUserEmail());
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userAuthRepository.save(user);
		
		return ResponseEntity.ok("User registered successfully");
	}
	
	@PostMapping("/register/bulk")
	public ResponseEntity<String> register(@Valid @RequestBody List<UserAuth> users) {
		if (users == null || users.isEmpty()) {
			return ResponseEntity.badRequest().body("Invalid JSON payload format");
		}
		
		for(UserAuth user : users) {
			if(userAuthRepository.findByUserEmail(user.getUserEmail()).isPresent()) {
				return ResponseEntity.badRequest().body("Email is already in use: " + user.getUserEmail());
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		userAuthRepository.saveAll(users);
		return ResponseEntity.ok(users.size() + " user(s) registered successfully");
	}
	
}
