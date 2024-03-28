package com.zoomout.backend.exception;

public class NotFoundException extends ZoomOutException {
	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException() {
		super();
	}
	
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
