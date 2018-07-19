package au.gov.vic.ecodev.mrt.web.controller.rest.advice;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebRestControllerAdvice {

	private static final Logger LOGGER = Logger.getLogger(WebRestControllerAdvice.class);
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<String> handleEmptyDataResult(EmptyResultDataAccessException e) {
		LOGGER.error(e.getMessage(), e);
		
		return new ResponseEntity<>("Not Data found", HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
