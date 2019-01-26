package com.speakit.exception;

import com.speakit.dataobject.exception.ApiException;
import org.springframework.http.HttpStatus;

public final class ResourceMissingException extends ApiException {
	public ResourceMissingException() {
		super(HttpStatus.NOT_FOUND, 2);
		addError("Requested resource not found");
	}
}