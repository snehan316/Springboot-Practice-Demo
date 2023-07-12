package com.sneha.socialmediademo.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request){
		
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		// TODO Auto-generated method stub
		
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getFieldError().getDefaultMessage(), request.getDescription(false));
		
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	

	
	
}
