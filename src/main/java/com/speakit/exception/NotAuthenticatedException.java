package com.speakit.exception;

import com.speakit.dataobject.exception.ApiException;
import org.springframework.http.HttpStatus;

public final class NotAuthenticatedException extends ApiException {
	public NotAuthenticatedException() {
		super(HttpStatus.UNAUTHORIZED, 1);
		addError("User not authenticated");
	}
}