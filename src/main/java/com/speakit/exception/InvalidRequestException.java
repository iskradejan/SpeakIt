package com.speakit.exception;

import com.speakit.dataobject.exception.ApiException;
import org.springframework.http.HttpStatus;

public final class InvalidRequestException extends ApiException {
	public InvalidRequestException() {
		super(HttpStatus.BAD_REQUEST, 3);
		addError("Request is invalid");
	}
}