package br.com.silvaaraujo.exception.utils;

import java.util.ArrayList;
import java.util.List;


public class ValidationException extends GenericError {

private static final long serialVersionUID = 1L;
	
	private List<FieldMessageError> errors = new ArrayList<>();
	
	public ValidationException(Integer httpStatus, String message) {
		super(httpStatus, message);
	}

	public List<FieldMessageError> getErrors() {
		return errors;
	}
	
	public void addError(String field, String message) {
		this.errors.add(new FieldMessageError(field, message));
	}
	
}
