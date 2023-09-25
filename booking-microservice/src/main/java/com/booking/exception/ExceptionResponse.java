package com.booking.exception;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
	private LocalDate timestamp;
	  private String message;
	  private String details;
	  private String httpCodeMessage;
}
