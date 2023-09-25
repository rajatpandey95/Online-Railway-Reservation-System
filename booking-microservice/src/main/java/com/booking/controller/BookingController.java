package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.exception.ResourceNotFoundException;
import com.booking.model.Booking;
import com.booking.model.Payment;
import com.booking.model.PaymentStatus;
import com.booking.service.BookingServiceImpl;

import jakarta.validation.Valid;
import jakarta.ws.rs.Path;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {
	
	@Autowired
	private BookingServiceImpl bookingService;
	
	@GetMapping("/getDetailsByPnrNo/{pnrNo}")
	public ResponseEntity<Booking> getUserDetailsById(@PathVariable long pnrNo) throws ResourceNotFoundException{
		Booking userDetails = bookingService.getUserDetailsByPnrNo(pnrNo);
		return ResponseEntity.ok(userDetails);
	}
	
	@GetMapping("/getAllTickets/{mobileNumber}")
	public ResponseEntity<List<Booking>> getAllTickets(@PathVariable String mobileNumber){
		List<Booking> bookings = bookingService.getAllTickets(mobileNumber);
		return ResponseEntity.ok(bookings);
	}
	
	@PostMapping("/bookTicket")
	public ResponseEntity<Boolean> bookTicket(@Valid @RequestBody Booking userDetails) throws ResourceNotFoundException {
		System.out.println("in book ticket");
		boolean successMsg = bookingService.bookTicket(userDetails);
		return ResponseEntity.ok(successMsg);
	}
	
	@PostMapping("/updateTicket")
	public ResponseEntity<String> updateTicket(@Valid @RequestBody Booking userDetails) throws ResourceNotFoundException {
		String successMsg = bookingService.updateTicket(userDetails);
		return ResponseEntity.ok(successMsg);
	}
	
	
	@PostMapping("/ticket/payment")
	public ResponseEntity<PaymentStatus> makePayment(@Valid @RequestBody Payment payment){
		PaymentStatus status = bookingService.makePayment(payment);
		return ResponseEntity.ok(status);
	}
	
	@GetMapping("/calculateBill/{trainNumber}/{classType}/{noOfPassengers}")
	public ResponseEntity<Integer> calculateBill( @PathVariable String trainNumber, @PathVariable String classType, @PathVariable int noOfPassengers) throws ResourceNotFoundException{
		System.out.println("came in calculate bill");
		int bill = bookingService.calculateBill(trainNumber, classType, noOfPassengers);
		System.out.println(bill);
		return ResponseEntity.ok(bill);
	}
	
	@PutMapping("/cancelTrain/{pnrNo}")
	public ResponseEntity<String> cancelTicket(@PathVariable long pnrNo) throws ResourceNotFoundException{
		String successMsg = bookingService.cancelTicket(pnrNo);
		return ResponseEntity.ok(successMsg);
	}
}
