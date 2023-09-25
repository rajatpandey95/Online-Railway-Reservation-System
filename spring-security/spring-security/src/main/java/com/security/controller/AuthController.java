package com.security.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.security.exception.NumberAlreadyExists;
import com.security.exception.UsernameAlreadyExists;
import com.security.jwt.JwtUtility;
import com.security.model.Login;
import com.security.model.Register;
import com.security.repository.UserRepository;
import com.security.response.JSONResponse;
import com.security.service.AuthService;
import com.security.service.UserDetailsImpl;
import com.security.service.UserDetailsServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.POST, RequestMethod.GET})
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtility jwtUtility;

	@Autowired
	private AuthService authService;
		
	@PostMapping("/register")
	public ResponseEntity<Register> saveCredentials(@Valid @RequestBody Register register) throws UsernameAlreadyExists, NumberAlreadyExists {
		Register registered = authService.saveCredentials(register);
		return ResponseEntity.status(HttpStatus.CREATED).body(registered);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JSONResponse> validateUser(@RequestBody Login login){
		
		// BadCredentail Exception will can occur
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

		UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();

		final String jwt = jwtUtility.generateToken(userDetails);
	
		List<String> listRoles = new ArrayList<>();
		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
		for(GrantedAuthority authority : roles) {
			String role = authority.getAuthority();
			listRoles.add(role);
		}
		
		Register userData = authService.getUser(login.getUsername());
		userData.setPassword(null);
		
		JSONResponse jsonResponse = new JSONResponse(jwt, userDetails.getUsername(), listRoles, userData);

		return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
	}
}
