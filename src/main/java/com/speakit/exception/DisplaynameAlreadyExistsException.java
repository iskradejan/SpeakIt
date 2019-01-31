package com.speakit.exception;

import com.speakit.dataobject.exception.ApiException;
import org.springframework.http.HttpStatus;

public final class DisplaynameAlreadyExistsException extends ApiException {
	public DisplaynameAlreadyExistsException() {
		super(HttpStatus.BAD_REQUEST, 5);
		addError("DisplayName resource already exists");
	}
}