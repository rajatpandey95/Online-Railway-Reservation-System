package com.booking.service;

import com.booking.exception.ResourceNotFoundException;
import com.booking.model.Booking;

public interface BookingService {

	public Booking getUserDetailsByPnrNo(long pnrNo) throws ResourceNotFoundException;
	
	public String bookTicket(Booking bookingDetails) throws ResourceNotFoundException;
	
	public String cancelTicket(long pnrNo) throws ResourceNotFoundException;
	
	public void sendBookingEmail(long pnrNo);
	
	public void sendCancellationEmail(long pnrNo);
}
