package br.com.silvaaraujo.exception.utils;

import java.io.Serializable;

public class GenericError implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer httpStatus;
	private String message;
	
	public GenericError(Integer httpStatus, String message) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
