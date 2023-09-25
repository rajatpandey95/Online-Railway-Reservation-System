package com.security.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.exception.NumberAlreadyExists;
import com.security.exception.UsernameAlreadyExists;
import com.security.model.DatabaseSequence;
import com.security.model.Register;
import com.security.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private MongoOperations mongoOperations;
	
	
	public Register saveCredentials(Register register) throws UsernameAlreadyExists, NumberAlreadyExists {
		
		if(userRepository.findByUsername(register.getUsername()) != null) {
			throw new UsernameAlreadyExists("Choose different username");
		}
		
		if(userRepository.findByPhoneNumber(register.getPhoneNumber()) != null) {
			throw new NumberAlreadyExists("Account with phone number already exists");
		}
		
		register.setUserId(generateSequence(Register.sequence_name));
		register.setPassword(encoder.encode(register.getPassword()));
		register.setRole("ROLE_USER");
		
		return userRepository.save(register);
	}
	
	public int generateSequence(String seqName) {
		Query query = new Query(Criteria.where("id").is(seqName));
			
		Update update = new Update().inc("seq", 1);
			
		DatabaseSequence counter = mongoOperations.findAndModify(query, update, options().returnNew(true).upsert(true), DatabaseSequence.class);
			
		return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}
	
	public Register getUser(String username) {
		Register user = userRepository.findByUsername(username);
		return user;
	}
}
