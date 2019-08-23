package com.sap.core.extensions.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sap.core.extensions.successfactors.connectivity.DestinationNotFoundException;

@ControllerAdvice
@RestController
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

	@ExceptionHandler(DestinationNotFoundException.class)
	public ResponseEntity<String> handleIOException(DestinationNotFoundException exception) {
		logError(exception);

		return new ResponseEntity<>("Communication to SuccessFactors system failed. " + exception.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private void logError(Exception exception) {
		LOGGER.error("An operation failed: ", exception);
	}
}