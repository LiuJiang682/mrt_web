package au.gov.vic.ecodev.mrt.web.repository.rest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionControllerAdvice {

	private static final Logger LOGGER = Logger.getLogger(RestExceptionControllerAdvice.class);
	
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<Object> handleNumberFormatException(NumberFormatException e) {
		LOGGER.error(e.getMessage(), e);
		return new ResponseEntity<Object>("Invalid request!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}
