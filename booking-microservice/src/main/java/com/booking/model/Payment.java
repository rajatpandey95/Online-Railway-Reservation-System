package com.booking.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {
	
	@NotNull
	@Size(min = 10, max=10, message = "Enter valid mobile number")
	private String phoneNumber;
	
	@NotEmpty
	private String cardOwnerName;
	
	@NotNull
	@Size(min=16,max=16,message = "Enter valid 16 digit card number")
	private String cardNumber;
	
	@NotNull
	private int cvv;
	
	@NotNull
	private String bankName;
	
	@NotNull
	private int bill;

}