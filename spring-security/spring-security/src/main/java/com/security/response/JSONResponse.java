package com.security.response;

import java.util.List;

import com.security.model.Register;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JSONResponse {
	
	private String token;
	private String type = "Bearer";
	
	private String username;
	private List<String> roles;
	
	private Register userDetails;

	public JSONResponse(String token, String username, List<String> roles, Register userDetails) {
		this.token = token;
		this.username = username;
		this.roles = roles;
		this.userDetails = userDetails;
	}
}
