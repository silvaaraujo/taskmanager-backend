package br.com.silvaaraujo.exception.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.silvaaraujo.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<GenericError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		GenericError error = new GenericError(HttpStatus.NOT_FOUND.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<GenericError> validationError(MethodArgumentNotValidException e, HttpServletRequest request) {
		ValidationException error = new ValidationException(HttpStatus.BAD_REQUEST.value(), "Erro de validação");
		
		e.getBindingResult()
		 .getFieldErrors()
		 .forEach(x -> error.addError(x.getField(), x.getDefaultMessage()));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
}
