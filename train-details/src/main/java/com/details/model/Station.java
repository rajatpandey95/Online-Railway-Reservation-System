package com.details.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "stations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Station {
	
	@Id
	@NotEmpty(message = "Please provide station number")
	private String stationNumber;
	@NotEmpty(message = "Please provide station name")
	private String stationName;
	
	@NotNull(message = "train stops list cannot be null")
	private List<String> trainStops;
}
