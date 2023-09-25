package com.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.security.model.Register;

public interface UserRepository extends MongoRepository<Register, Integer> {
	
	Register findByUsername(String username);
	
	Register findByPhoneNumber(String phoneNumber);
	
}
