package com.booking.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class CustomizedResponseExceptionHandler {
	
	@ExceptionHandler(value = {ResourceNotFoundException.class})
	public final ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request){
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage(ex.getLocalizedMessage());
		response.setTimestamp(LocalDate.now());
		response.setHttpCodeMessage("Not Found");
		response.setDetails("uri= " +  ((ServletWebRequest)request).getRequest().getRequestURI().toString());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(value = {Exception.class, MethodArgumentNotValidException.class})
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		
		System.out.println(ex);
		
		ExceptionResponse response = new ExceptionResponse(); 
		response.setMessage(ex.getLocalizedMessage());
		response.setTimestamp(LocalDate.now());
		response.setHttpCodeMessage("Not Found all exception");
		response.setDetails("uri=" + ((ServletWebRequest)request).getRequest().getRequestURI().toString());
				
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
}
