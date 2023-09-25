package com.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.booking.exception.ResourceNotFoundException;
import com.booking.externalService.PaymentService;
import com.booking.externalService.TrainService;
import com.booking.model.DatabaseSequence;
import com.booking.model.Passenger;
import com.booking.model.Payment;
import com.booking.model.PaymentStatus;
import com.booking.model.Train;
import com.booking.model.Booking;
import com.booking.repository.BookingRepository;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.*;

import java.util.List;
import java.util.Objects;
import java.util.Date;

@Service
public class BookingServiceImpl {
	
	
	@Autowired
	public BookingRepository bookingRepository;
	
	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private TrainService trainService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Value("${spring.mail.username}") 
	private String sender;
	 
	@Autowired
	private JavaMailSender javaMailSender;
	 
	public List<Booking> getAllTickets(String mobileNumber){
		List<Booking> tickets = bookingRepository.findByMobileNumber(mobileNumber);
		return tickets;
	}

	public Booking getUserDetailsByPnrNo(long pnrNo) throws ResourceNotFoundException {
		Booking bookingDetails = bookingRepository.findByPnrNo(pnrNo);
		if(bookingDetails == null) {
			throw new ResourceNotFoundException("Invalid PnrNo, No ticket booked with PNR Number: " + pnrNo);
		}
		return bookingDetails;
	}
	
	public boolean bookTicket(Booking bookingDetails) throws ResourceNotFoundException {
		bookingDetails.setId(generateSequence(Booking.sequence_name));
		bookingDetails.setValid(true);
		long pnr = new Date().getTime()/1000;
		bookingDetails.setPnrNo(pnr);
		bookingDetails.setCreatedAt(new Date());
		bookingRepository.save(bookingDetails);
		
		int totalPassengers = bookingDetails.getPassengerDetails().size();
		
		trainService.updateSeats(bookingDetails.getTrainNumber(), totalPassengers);
		
		sendBookingEmail(bookingDetails.getPnrNo(), bookingDetails.getMailId(), bookingDetails.getPassengerDetails());

//		return "Your ticket with PNR Number : " + bookingDetails.getPnrNo() + " booked successfully";
		return true;
	}
	
	public String updateTicket(Booking bookingDetails) {
//		Date date = bookingDetails.getCreatedAt();
//		System.out.println("get time: " + date.getTime());
		bookingRepository.save(bookingDetails);
		return "Updated";
	}
	
	
	
	public String cancelTicket(long pnrNo) throws ResourceNotFoundException {
		Booking bookingDetails = bookingRepository.findByPnrNo(pnrNo);
		if(bookingDetails == null) {
			throw new ResourceNotFoundException("No reservation exists with PNR Number: " + pnrNo);
		}
		
		// means ticket not valid
		bookingDetails.setValid(false);
		bookingRepository.save(bookingDetails);
		
		
		// sending payment service request to return the amount of ticket
//		paymentService.cancel(pnrNo);
		int totalPassengers = bookingDetails.getPassengerDetails().size();
		trainService.updateSeats(bookingDetails.getTrainNumber(), (-1 * totalPassengers));
						
		sendCancellationEmail(bookingDetails.getPnrNo(), bookingDetails.getMailId());
		
		return "Ticket cancelled";
	}
	
	public PaymentStatus makePayment(Payment paymentDetails) {
		return null;
	}
		
	public int calculateBill(String trainNumber, String classType, int noOfPassengers) throws ResourceNotFoundException {
		Train train = trainService.getTrainByTrainNumber(trainNumber);
		System.out.println(train.toString());
		if(classType.equals("ac1")) {
			System.out.println("Train Number: " + trainNumber + " ClassType: " + classType + " Passengers: " + noOfPassengers + " CostPerHead: " + train.getFirstClassFare() );
			System.out.println(train.getFirstClassFare() * noOfPassengers);
			return train.getFirstClassFare() * noOfPassengers;
		}
		else if(classType.equals("ac2")) {
			System.out.println("Train Number: " + trainNumber + " ClassType: " + classType + " Passengers: " + noOfPassengers + " CostPerHead: " + train.getTwoTierFare() );
			System.out.println(train.getTwoTierFare() * noOfPassengers);
			return train.getTwoTierFare() * noOfPassengers;
		}
		else if(classType.equals("ac3")) {
			System.out.println("Train Number: " + trainNumber + " ClassType: " + classType + " Passengers: " + noOfPassengers + " CostPerHead: " + train.getThreeTierFare() );
			System.out.println(train.getThreeTierAcFare() * noOfPassengers);
			return train.getThreeTierFare() * noOfPassengers;
		}
		else if(classType.equals("sl")) {
			System.out.println("Train Number: " + trainNumber + " ClassType: " + classType + " Passengers: " + noOfPassengers + " CostPerHead: " + train.getSleeperFare() );
			System.out.println(train.getSleeperFare() * noOfPassengers);
			return train.getSleeperFare() * noOfPassengers;
		}
		else {
			return 0;
		}
	}
	
	// To Get auto generated id
	public int generateSequence(String seqName) {
		Query query = new Query(Criteria.where("id").is(seqName));
			
		Update update = new Update().inc("seq", 1);
			
		DatabaseSequence counter = mongoOperations.findAndModify(query, update, options().returnNew(true).upsert(true), DatabaseSequence.class);
			
		return !Objects.isNull(counter) ? counter.getSeq() : 1;
	}
	
	public String sendBookingEmail(long pnrNo, String recipient, List<Passenger> passengers) {
		String msgSubject = "Ticket Booking Successful";
		
		StringBuilder msgSb = new StringBuilder();
		msgSb.append("Your train ticket booking is successful with PNR Number: " + pnrNo + "\n");
		for(Passenger p : passengers) {
			msgSb.append(p).append("\n");
		}
		
		try {
			
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(sender);
			mailMessage.setTo(recipient);
			mailMessage.setText(msgSb.toString());
			mailMessage.setSubject(msgSubject);
			
			javaMailSender.send(mailMessage);
			
			System.out.println("success");
			return "Mail Sent Successfully...";
		}
		catch(Exception e) {
			return "Error while sending mail";
		}
		
	}
	public String sendCancellationEmail(long pnrNo, String recipient) {
		
		String msgSubject = "Ticket Cancellation";
		String msgBody =  "Your ticket with PNR Number: " + pnrNo + "is cancelled." + "Your payment amount will be credited to your account within 3 working days..!!!";
		
		try {
			
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(sender);
			mailMessage.setTo(recipient);
			mailMessage.setText(msgBody);
			mailMessage.setSubject(msgSubject);
			
			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		}
		catch(Exception e) {
			return "Error while sending mail";
		}
	}
}
