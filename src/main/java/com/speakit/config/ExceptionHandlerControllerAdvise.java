package com.speakit.config;

import com.speakit.dataobject.exception.ApiError;
import com.speakit.dataobject.exception.ApiException;
import com.speakit.exception.InvalidRequestException;
import com.speakit.exception.UnexpectedException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvise {
	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerControllerAdvise.class);

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<?> argumentHandler(MethodArgumentNotValidException exception, HttpServletRequest request) {
		ApiError apiError = new ApiError();
		apiError.setCode(3).setMessage("Invalid request");

		return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<?> runtimeHandler(RuntimeException runtimeException, HttpServletRequest request) {
		boolean includeStacktrace = false;
		HashMap<String, Object> logMessage = new HashMap<>();
		logMessage.put("exception", runtimeException.getClass().getName());

		List<HashMap> errors = new ArrayList<>();
		if(runtimeException instanceof ApiException) {
			((ApiException) runtimeException).getErrors().forEach(apiError -> {
				HashMap<String, Object> error = new HashMap<>();
				error.put("code", apiError.getCode());
				error.put("message", apiError.getMessage());
				errors.add(error);
			});
		} else {
			// These are exceptions where we may not know what's gone wrong - include the stack trace
			logMessage.put("error-message", runtimeException.getLocalizedMessage());

			if(runtimeException instanceof HttpClientErrorException) {
				HashMap<String, Object> error = new HashMap<>();
				error.put("message", ((HttpClientErrorException)runtimeException).getResponseBodyAsString());
				errors.add(error);
			} else if(runtimeException instanceof HttpMessageNotReadableException) {
				// Special case - if we can't parse the JSON object, turn that into InvalidInputException
				runtimeException = new InvalidRequestException();
			} else if(runtimeException instanceof HttpServerErrorException) {
				// This is an unexpected error - include the stacktrace
				includeStacktrace = true;
				runtimeException = new UnexpectedException();
			} else {
				includeStacktrace = true;
				runtimeException = new UnexpectedException();
			}
		}

		if (includeStacktrace) {
			StringWriter exceptionTrace = new StringWriter();
			runtimeException.printStackTrace(new PrintWriter(exceptionTrace));
			logMessage.put("stacktrace", exceptionTrace.toString());
		}

		if(!errors.isEmpty()) {
			logMessage.put("errors", errors);
		}

		HashMap<String, String> parameters = new HashMap<>();
		Collections.list(request.getParameterNames()).forEach(parameterName -> {
			parameters.put(parameterName, request.getParameter(parameterName));
		});
		logMessage.put("parameters", parameters);

		HashMap<String, String> headers = new HashMap<>();
		Collections.list(request.getHeaderNames()).forEach(headerName -> {
			headers.put(headerName, request.getHeader(headerName));
		});
		logMessage.put("headers", headers);

		logMessage.put("url", request.getRequestURL());

		log.error(new JSONObject(logMessage).toString());

		return new ResponseEntity<>(((ApiException) runtimeException).getErrors(), ((ApiException) runtimeException).getHttpStatus());
	}
}