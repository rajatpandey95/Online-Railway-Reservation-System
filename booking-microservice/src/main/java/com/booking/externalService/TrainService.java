package com.booking.externalService;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.booking.exception.ResourceNotFoundException;
import com.booking.model.Train;

@FeignClient(name = "TRAIN-DETAILS")
public interface TrainService {
	
	@PutMapping("/train/updateSeats/{trainNumber}/{noOfPassengers}")
	ResponseEntity<String> updateSeats(@PathVariable String trainNumber, @PathVariable int noOfPassengers) throws ResourceNotFoundException;

	@GetMapping(value="/train/{trainNumber}",produces = "application/json")
	public Train getTrainByTrainNumber( @PathVariable String trainNumber ) throws ResourceNotFoundException;
}
