package com.details.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.details.exception.ResourceAlreadyExistsException;
import com.details.exception.ResourceNotFoundException;
import com.details.model.Station;
import com.details.repository.StationRepository;

@Service
public class StationService {
	
	@Autowired
	private StationRepository stationRepository;
		
	public boolean isStationValid(String stationNumber) throws ResourceNotFoundException  {
		Station st = stationRepository.findByStationNumber(stationNumber);
		if(st == null) {
			throw new ResourceNotFoundException("Invalid station number");
		}
		return true;
	}
	
	public Station addStationDetails(Station station) throws ResourceAlreadyExistsException {
		Station st = stationRepository.findByStationNumber(station.getStationNumber());
		if(st != null) {
			throw new ResourceAlreadyExistsException("Station with station number : " + station.getStationName()  + ", already exists");
		}
		return stationRepository.save(station);
	}
	
	public Station getStationDetails(String stationNumber) throws ResourceNotFoundException {
		Station station = stationRepository.findByStationNumber(stationNumber);
		if(station == null) {
			throw new ResourceNotFoundException("Invalid Station Number");
		}
		return station;
	}
	
	public Station addTrainStopInStation(String stationNumber, String trainNumber) throws ResourceNotFoundException {
		Station st = stationRepository.findByStationNumber(stationNumber);
		List<String> trainStops = st.getTrainStops();
		
		if(!trainStops.contains(trainNumber)) {			
			trainStops.add(trainNumber);
		}
				
		return stationRepository.save(st);
	}
}
