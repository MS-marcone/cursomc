package com.marcone.cursomc.resources.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Integer status, String msg, Instant timestamp) {
		super(status, msg, timestamp);
		
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addErro(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	
	}

}
