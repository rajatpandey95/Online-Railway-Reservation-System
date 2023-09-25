package com.details.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.details.exception.ResourceAlreadyExistsException;
import com.details.exception.ResourceNotFoundException;
import com.details.model.Train;
import com.details.model.TrainSearch;
import com.details.repository.TrainRepository;

@Service
public class TrainServiceImpl implements TrainService{
	
	@Autowired
	private TrainRepository trainRepository;
	
	@Autowired
	private StationService stationService;
	
	@Override
	public boolean validateTrainNumber(String trainNumber) throws ResourceNotFoundException {
		Train train = trainRepository.findByTrainNumber(trainNumber);
		if(train == null) {
			throw new ResourceNotFoundException("Invalid train number");
		}
		return true;
	}
	
	@Override
	public List<Train> getAllTrains() {
		return trainRepository.findAll();
	}
	
	@Override
	public Train getTrainByNumber(String trainNumber) throws ResourceNotFoundException {
		validateTrainNumber(trainNumber);
		return trainRepository.findByTrainNumber(trainNumber);
	}
	
	@Override
	public boolean validateStations(String st) throws ResourceNotFoundException {
		// throws error if not valid
		stationService.isStationValid(st);
		return true;
	}
	
	@Override
	public Train addTrain(Train train) throws ResourceNotFoundException, ResourceAlreadyExistsException {
		
		Train tr = trainRepository.findByTrainNumber(train.getTrainNumber());
		if(tr != null) {
			throw new ResourceAlreadyExistsException("Train with train number : " + train.getTrainNumber() + ", already exists.");
		}
		
		String startStation = train.getStartStation();
		String endStation = train.getEndStation();
		
		validateStations(startStation);
		validateStations(endStation);
		
		
		// adding train in stations also
		stationService.addTrainStopInStation(startStation, train.getTrainNumber());
		stationService.addTrainStopInStation(endStation, train.getTrainNumber());
		
		return trainRepository.save(train);
	}
	
	@Override
	public List<Train> trainBetweenStation(TrainSearch trainSearch) throws ResourceNotFoundException {
		validateStations(trainSearch.getFrom());
		validateStations(trainSearch.getDestination());
		
		if(trainSearch.isFlexibleDate()) {
			Date date = new Date();
			return trainRepository.findByStartStationAndEndStation(trainSearch.getFrom(), trainSearch.getDestination(), date);
			
		}
		Date chosenDate = trainSearch.getDate();
		Date tomorrow = new Date(chosenDate.getTime() + 86400000);
		
		return trainRepository.findTrainBtwStnWithDate(trainSearch.getFrom(), trainSearch.getDestination(), chosenDate, tomorrow);
	}

	@Override
	public Train updateTrainDetails(String trainNumber, Train train) throws ResourceNotFoundException {
		validateTrainNumber(trainNumber);
		return trainRepository.save(train);
	}
	
	@Override
	public String deleteTrain(String trainNumber) throws ResourceNotFoundException {
		Train train = trainRepository.findByTrainNumber(trainNumber);
		if(train == null) {
			throw new ResourceNotFoundException("Train details with Train number : " + trainNumber + ", does not exists" );
		}
		trainRepository.delete(train);
		return "Deleted Train details with train number : " + trainNumber;
	}
	
	@Override
	public String updateSeats(String trainNumber, int noOfPassangers) throws ResourceNotFoundException {
		
		Train train = getTrainByNumber(trainNumber);
		int initialCapacity = train.getNoOfSeats();
		int newCapacity = initialCapacity - noOfPassangers;
		train.setNoOfSeats(newCapacity);
		trainRepository.save(train);
		
		return "Train seat capacity updated";
	}
	
//	public List<Train> trainBtwStationWithDate(String startStation, String endStation, Date date){
//		
//		Query query = new Query();
//		query.addCriteria(Criteria.where("arrivalTime").gte(date));
//		
//				
//	}
}
