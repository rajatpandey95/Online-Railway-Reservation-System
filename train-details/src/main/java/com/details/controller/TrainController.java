package com.details.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.details.exception.ResourceAlreadyExistsException;
import com.details.exception.ResourceNotFoundException;
import com.details.model.Train;
import com.details.model.TrainSearch;
import com.details.service.TrainServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/train")
@CrossOrigin(origins = "http://localhost:4200")
public class TrainController {
	
	@Autowired
	private TrainServiceImpl trainService;
	
	@GetMapping("/allTrains")
	public ResponseEntity<List<Train>> getAllTrains(){
		System.out.println("--------------------------in get all train---------------------------");
		List<Train> trainsList =  trainService.getAllTrains();
		System.out.println(trainsList.toString());
		return ResponseEntity.ok(trainsList);
	}
	
	@GetMapping(value="/{trainNumber}")
	public ResponseEntity<Train> getTrainByTrainNumber( @PathVariable String trainNumber ) throws ResourceNotFoundException {
		System.out.println("--------------------------in get by train number---------------------------");
		Train train =  trainService.getTrainByNumber(trainNumber);
		return ResponseEntity.status(HttpStatus.OK).body(train);
	}
	
	@PostMapping("/addTrain")
	public ResponseEntity<Train> addTrain(@Valid @RequestBody Train train) throws ResourceNotFoundException, ResourceAlreadyExistsException {
		System.out.println("--------------------------in add train---------------------------");
		Train train1 = trainService.addTrain(train);
		return ResponseEntity.status(HttpStatus.CREATED).body(train1);
	}
	
	@PostMapping("/update/{trainNumber}")
	public ResponseEntity<Train> updateTrainDetails(@PathVariable String trainNumber, @RequestBody Train train) throws ResourceNotFoundException{
		System.out.println("--------------------------in update train---------------------------");
		Train updatedTrainDetails = trainService.updateTrainDetails(trainNumber, train);
		return ResponseEntity.ok(updatedTrainDetails);
	}
	
	@PostMapping("/findTrainBtwStn")
	public ResponseEntity<List<Train>> trainBetweenStations( @RequestBody TrainSearch trainSearch ) throws ResourceNotFoundException{
		System.out.println("--------------------------in train btw station---------------------------");
		System.out.println(trainSearch);
		List<Train> trainsList = trainService.trainBetweenStation(trainSearch);
		return ResponseEntity.ok(trainsList);
	}

	@DeleteMapping("/deleteTrain/{trainNumber}")
	public ResponseEntity<String> deleteTrainDetails(@PathVariable String trainNumber) throws ResourceNotFoundException{
		System.out.println("--------------------------in delete train---------------------------");
		String str = trainService.deleteTrain(trainNumber);
		return ResponseEntity.ok(str);
	}
	
	@PutMapping("/updateSeats/{trainNumber}/{noOfPassengers}")
	public ResponseEntity<String> updateSeats(@PathVariable String trainNumber, @PathVariable int noOfPassengers) throws ResourceNotFoundException{
		System.out.println("--------------------------in update seats---------------------------");
		String str = trainService.updateSeats(trainNumber, noOfPassengers);
		return ResponseEntity.ok(str);
	}
}
