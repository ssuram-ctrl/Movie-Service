package com.RatingService.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.RatingService.NotFoundException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@Getter
	static class Error {
		
		private final String reason;
		private final String message;
		
		public Error(String reason, String message) {
			super();
			this.reason = reason;
			this.message = message;
		}
		
	}
	
//	404 not found error
    @ExceptionHandler(NotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Error handleNotFoundExceptionError(Exception e) {
		log.warn(e.getMessage());
		return new Error(HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
	}
    
//	500 internal server error
    @ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Error handleInvalidExceptionError(Exception e) {
		log.warn(e.getMessage());
		return new Error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
	}
    
}
