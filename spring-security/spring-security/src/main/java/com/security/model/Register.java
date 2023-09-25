package com.security.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Register {
	
	@Transient
	public static final String sequence_name = "users_sequence";
	
	@Id
	private int userId;
	
	@NotNull
	@Size(min = 1, max = 15, message = "Name should be btw 5 and 20 character")
	private String name;
	
	@NotNull
	@Size(min = 10,max = 10, message = "Provide valid mobile number of size 10")
	@Indexed(unique = true)
	private String phoneNumber;
	
	@NotNull
	@Size(min = 2, message = "Provide username length greater than 2")
	private String username;
	
	@NotNull(message = "Email cannot be null")
	@Email(message = "provide valid email")
	private String email;
	
	@NotNull
	@Size(min=8, max=15, message = "provide valid password with minimum 8 character and maximum 15")
	private String password;
	
	private String role;
}
