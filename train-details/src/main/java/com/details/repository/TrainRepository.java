package com.details.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.details.model.Train;

public interface TrainRepository extends MongoRepository<Train, String> {
	
	List<Train> findAll();
	
	Train findByTrainNumber(String trainNumber);
	
	@Query("{startStation:{$eq: ?0}, endStation: {$eq: ?1}, arrivalTime:{$gte: ?2}}")
	List<Train> findByStartStationAndEndStation(String startStation, String endStation, Date date);
	
	@Query("{startStation:{$eq: ?0}, endStation: {$eq: ?1}, arrivalTime:  { $gte :  ?2, $lt : ?3},  }")
	List<Train> findTrainBtwStnWithDate(String startStation, String endStation, Date date, Date tomorrow);
}
