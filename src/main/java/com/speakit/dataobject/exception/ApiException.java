package com.speakit.dataobject.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ApiException extends RuntimeException {
	private List<ApiError> errors = new ArrayList<>();
	private HttpStatus httpStatus;
	private int errorCode;

	public ApiException(HttpStatus httpStatus, int errorCode) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
	}

//	public ApiException(Throwable cause, HttpStatus httpStatus, int errorCode) {
//		super(cause);
//		this.httpStatus = httpStatus;
//		this.errorCode = errorCode;
//	}

	public List<ApiError> getErrors() {
		return errors;
	}

	public ApiException setErrors(List<ApiError> errors) {
		this.errors = errors;
		return this;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public ApiException setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
		return this;
	}

	public ApiException addError(String message) {
		errors.add(new ApiError().setMessage(message).setCode(errorCode));
		return this;
	}
}
