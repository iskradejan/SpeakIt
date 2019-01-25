package com.speakit.dataobject.exception;

import java.util.UUID;

public class ApiError {
	private String id;
	private int code;
	private String message;

	public ApiError() {
		id = UUID.randomUUID().toString();
	}

	public String getId() {
		return id;
	}

	public ApiError setId(String id) {
		this.id = id;
		return this;
	}

	public int getCode() {
		return code;
	}

	public ApiError setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public ApiError setMessage(String message) {
		this.message = message;
		return this;
	}
}
