package com.payment.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.payment.model.Transaction;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentService {
	
	@Value("${razorpay.keyId}")
	private String KEY;
	@Value("${razorpay.keySecret}")
	private String KEY_SECRET;
	
	private final String CURRENCY = "INR";
	
	public Transaction createTransaction(Double amount) {
		
		try {
			
			JSONObject jsonObject = new JSONObject();
			
			// razor pay takes into account smallest unit of particular currency(means amount=1000 will be considered as 1000Paisa not as ruppes)
			jsonObject.put("amount", (amount * 100));
			jsonObject.put("currency", CURRENCY);
			
			
			RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);
			
			Order order = razorpayClient.orders.create(jsonObject);
			
			Transaction transactionDetails = prepareTransactionDetails(order);
			System.out.println(order.toString());
			return transactionDetails;
		}
		catch (RazorpayException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Transaction prepareTransactionDetails(Order order) {
		String orderId = order.get("id");
		String currency = order.get("currency");
		Integer amount = order.get("amount");
		
		Transaction transactionDetails = new Transaction(orderId, currency, amount, KEY);
		return transactionDetails;
	}
}