package com.speakit.exception;

import com.speakit.dataobject.exception.ApiException;
import org.springframework.http.HttpStatus;

public final class ResourceAlreadyExistsException extends ApiException {
	public ResourceAlreadyExistsException() {
		super(HttpStatus.BAD_REQUEST, 4);
		addError("Requested resource already exists");
	}
}