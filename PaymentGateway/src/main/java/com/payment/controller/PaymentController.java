package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.payment.model.Transaction;
import com.payment.service.PaymentService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/createTransaction/{amount}")
	public Transaction createTransaction(@PathVariable Double amount) {
		return paymentService.createTransaction(amount);
	}
	
}
