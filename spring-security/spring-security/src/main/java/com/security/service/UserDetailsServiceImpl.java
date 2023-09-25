package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.model.Register;
import com.security.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Register user = userRepository.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
		
		UserDetails userDetails = UserDetailsImpl.getUser(user);
		
		return userDetails;
	}
}
