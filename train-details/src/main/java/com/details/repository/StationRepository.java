package com.details.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.details.model.Station;

public interface StationRepository extends MongoRepository<Station, String> {

	Station findByStationNumber(String stationNumber);
	
}
