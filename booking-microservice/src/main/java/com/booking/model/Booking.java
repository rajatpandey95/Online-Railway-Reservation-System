package com.booking.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "bookingDetails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
	
	@Transient
	public static final String sequence_name = "users_sequence";
	
	@Id
	private int id;
	
	@NotEmpty(message = "mobile number cannot be empty")
	@Size(min = 10, max = 10, message = "Please provide valid mobile number")
	private String mobileNumber;
	
	private long pnrNo;
	
	@NotEmpty
	@Size(min = 1, message = "Atleast 1 passenger to book tickets")
	private List<Passenger> passengerDetails;
	
	@NotNull(message = "please provide bill value")
	@Min(value = 10, message = "bill value cannot be less than 10")
	private int bill;
	
	@NotEmpty
	@Size(max = 3)
	private String trainNumber;
	
	@NotEmpty(message = "source station cannot be empty")
	private String sourceStation;
	
	@NotEmpty(message = "destination station cannot be null")
	private String destinationStation;
	
	@NotEmpty(message = "class type cannot be empty")
	@Size(max = 3, min = 2, message = "provide valid class type")
	private String classType;  //1AC, 2AC, 3AC, SL
	
	@NotEmpty(message = "provide valid email")
	private String mailId;
	
	@NotNull
	private boolean isValid;
	
	@JsonFormat(timezone = "Asia/Kolkata")
	private Date createdAt;
	
	@NotEmpty(message = "please provide payment id")
	private String paymentId;
}
