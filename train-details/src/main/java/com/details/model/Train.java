package com.details.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "trains")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Train {
	
	@Id
	@NotEmpty(message = "Please provide train number")
	private String trainNumber;
	
	@NotEmpty(message = "Please provide train name")
	private String trainName;

	@NotEmpty(message = "Please enter train start station")
	private String startStation;
	
	@NotEmpty(message = "Please enter train end station")
	private String endStation;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	private Date arrivalTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Kolkata")
	private Date deptTime;
	
	@Min(value=10, message = "Seat capacity should be btw 10 and 200")
	private int noOfSeats;
	
	private int firstClassAcFare;
	private int twoTierAcFare;
	private int threeTierAcFare;
	private int sleeperFare;
	@Override
	public String toString() {
		return "Train [trainNumber=" + trainNumber + ", trainName=" + trainName + ", startStation=" + startStation
				+ ", endStation=" + endStation + ", arrivalTime=" + arrivalTime + ", deptTime=" + deptTime
				+ ", noOfSeats=" + noOfSeats + ", firstClassAcFare=" + firstClassAcFare + ", twoTierAcFare="
				+ twoTierAcFare + ", threeTierAcFare=" + threeTierAcFare + ", sleeperFare=" + sleeperFare + "]";
	}
	
}
