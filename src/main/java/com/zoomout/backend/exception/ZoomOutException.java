package com.zoomout.backend.exception;

public class ZoomOutException extends RuntimeException {
	public ZoomOutException(String message) {
		super(message);
	}
	
	public ZoomOutException() {
		super();
	}
	
	public ZoomOutException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ZoomOutException(Throwable cause) {
		super(cause);
	}
}
