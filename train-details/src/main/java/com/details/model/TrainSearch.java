package com.details.model;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainSearch {
	
	@NotNull
	private String from;
	
	@NotNull
	private String destination;
	
	@NotNull
	private Date date;
	
	private boolean flexibleDate;

	@Override
	public String toString() {
		return "TrainSearch [from=" + from + ", destination=" + destination + ", date=" + date + "]";
	}

	
}
