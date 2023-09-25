package com.details.service;

import java.util.Date;
import java.util.List;

import com.details.exception.ResourceAlreadyExistsException;
import com.details.exception.ResourceNotFoundException;
import com.details.model.Train;
import com.details.model.TrainSearch;

public interface TrainService {
	public boolean validateTrainNumber(String trainNumber) throws ResourceNotFoundException;
	public List<Train> getAllTrains();
	public Train getTrainByNumber(String trainNumber)  throws ResourceNotFoundException;
	public boolean validateStations(String st) throws ResourceNotFoundException;
	public Train addTrain(Train train) throws ResourceNotFoundException, ResourceAlreadyExistsException;
	public List<Train> trainBetweenStation(TrainSearch trainSearch) throws ResourceNotFoundException;
	public Train updateTrainDetails(String trainNumber, Train train) throws ResourceNotFoundException;
	public String deleteTrain(String trainNumber) throws ResourceNotFoundException;
	public String updateSeats(String trainNumber, int noOfPassangers) throws ResourceNotFoundException;
}
