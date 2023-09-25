package com.details.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.details.exception.ResourceAlreadyExistsException;
import com.details.exception.ResourceNotFoundException;
import com.details.model.Station;
import com.details.service.StationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/stations")
public class StationController {
	
	@Autowired
	private StationService stationService;
	
//	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/addStation")
	public ResponseEntity<Station> addStation(@Valid @RequestBody Station station) throws ResourceAlreadyExistsException{
		Station st = stationService.addStationDetails(station);
		return ResponseEntity.status(HttpStatus.CREATED).body(st);
	}
	
//	@PreAuthorize("hasAuthority('ROLE_ADMIN') || hasAuthority('ROLE_USER')")
	@GetMapping("/details/{stationNumber}")
	public ResponseEntity<Station> getStationDetails(@PathVariable String stationNumber) throws ResourceNotFoundException{
		Station station = stationService.getStationDetails(stationNumber);
		return ResponseEntity.status(HttpStatus.OK).body(station);
	}
}
