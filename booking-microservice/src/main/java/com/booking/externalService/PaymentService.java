package com.booking.externalService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.booking.model.Payment;
import com.booking.model.PaymentStatus;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentService {

	
}
