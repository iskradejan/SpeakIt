package com.speakit.exception;

import com.speakit.dataobject.exception.ApiException;
import org.springframework.http.HttpStatus;

public final class UnexpectedException extends ApiException {
	public UnexpectedException() {
		super(HttpStatus.INTERNAL_SERVER_ERROR, 666);
		addError("Unexpected Exception");
	}
}