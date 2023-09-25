package com.booking.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.booking.model.Booking;

public interface BookingRepository extends MongoRepository<Booking, Integer>{

	Booking findByPnrNo(long pnrNo);
	
	Integer deleteByPnrNo(long pnrNo);
	
	List<Booking> findByMobileNumber(String mobileNumber);
}
