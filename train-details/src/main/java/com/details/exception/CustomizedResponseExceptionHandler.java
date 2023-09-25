package com.details.exception;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomizedResponseExceptionHandler {
	
	private Logger log = LoggerFactory.getLogger(CustomizedResponseExceptionHandler.class);

	@ExceptionHandler(value = {ResourceNotFoundException.class})
	public final ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request){
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage(ex.getLocalizedMessage());
		response.setTimestamp(LocalDate.now());
		response.setHttpCodeMessage("Not Found");
		response.setDetails("uri= " +  ((ServletWebRequest)request).getRequest().getRequestURI().toString());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(value = {ResourceAlreadyExistsException.class})
	public final ResponseEntity<Object> handleAlreadyExistsException(Exception ex, WebRequest request){
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage(ex.getLocalizedMessage());
		response.setTimestamp(LocalDate.now());
		response.setHttpCodeMessage("BAD REQUEST");
		response.setDetails("uri= " +  ((ServletWebRequest)request).getRequest().getRequestURI().toString());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(value = {Exception.class})
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		System.out.println(ex);
		ExceptionResponse response = new ExceptionResponse(); 
		response.setMessage(ex.getLocalizedMessage());
		response.setTimestamp(LocalDate.now());
		response.setHttpCodeMessage("Not Found all exception");
		response.setDetails("uri=" + ((ServletWebRequest)request).getRequest().getRequestURI().toString());
		
		log.error(ex.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}	
}
