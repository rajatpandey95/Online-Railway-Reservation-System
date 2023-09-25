package com.security.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.mongodb.MongoException;

@ControllerAdvice
public class CustomResponseExceptionHandler {
	
	@ExceptionHandler(value = {UsernameAlreadyExists.class})
	public ResponseEntity<ExceptionResponse> handleAlreadyExists(Exception ex, WebRequest request){
		
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage("Username already exists");
		response.setTimestamp(LocalDate.now());
		response.setHttpCodeMessage("BAD REQUEST");
		response.setDetails("uri= " +  ((ServletWebRequest)request).getRequest().getRequestURI().toString());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(value = {NumberAlreadyExists.class})
	public ResponseEntity<ExceptionResponse> handleDuplicateNumber(Exception ex, WebRequest request){
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage("Account with phone number already exists");
		response.setTimestamp(LocalDate.now());
		response.setHttpCodeMessage("BAD REQUEST");
		response.setDetails("uri= " +  ((ServletWebRequest)request).getRequest().getRequestURI().toString());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(value = {BadCredentialsException.class})
	public ResponseEntity<ExceptionResponse> handleBadCredentials(Exception ex, WebRequest request){
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage("Bad Credentials");
		response.setTimestamp(LocalDate.now());
		response.setHttpCodeMessage("BAD REQUEST");
		response.setDetails("uri= " +  ((ServletWebRequest)request).getRequest().getRequestURI().toString());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	
	@ExceptionHandler(value = MongoException.class)
	public ResponseEntity<ExceptionResponse> databaseException(Exception ex, WebRequest request){
		System.out.println("in mongo exception");
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage(ex.getLocalizedMessage());
		response.setTimestamp(LocalDate.now());
		response.setHttpCodeMessage("BAD REQUEST");
		response.setDetails("uri= " +  ((ServletWebRequest)request).getRequest().getRequestURI().toString());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	
//	@ExceptionHandler(value = {Exception.class})
//	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
//		System.out.println("in handle all exception");
//		ExceptionResponse response = new ExceptionResponse(); 
//		response.setMessage(ex.getLocalizedMessage());
//		response.setTimestamp(LocalDate.now());
//		response.setHttpCodeMessage("Not Found");
//		response.setDetails("uri=" + ((ServletWebRequest)request).getRequest().getRequestURI().toString());
//				
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//	}	

}
