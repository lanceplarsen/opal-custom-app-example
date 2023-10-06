package dev.opal.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.opal.app.codegen.model.Error;
import dev.opal.app.exceptions.NotFoundException;
import dev.opal.app.exceptions.UserAlreadyExistsException;
import dev.opal.app.exceptions.UsersRetrievalException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Error> handleNotFoundException(NotFoundException e) {
		logger.error(e.getMessage(), e);
		return new ResponseEntity<>(new Error(404, e.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UsersRetrievalException.class)
	public ResponseEntity<Error> handleUsersRetrievalException(UsersRetrievalException e) {
		logger.error(e.getMessage(), e);
		return new ResponseEntity<>(new Error(400, "Failed to get users"), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<Error> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
		logger.error(e.getMessage(), e);
		return new ResponseEntity<>(new Error(400, e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Error> handleGenericException(Exception e) {
		logger.error(e.getMessage(), e);
		return new ResponseEntity<>(new Error(500, "Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}