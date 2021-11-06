package main;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class KeyNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2095793919029767140L;

	public KeyNotFoundException() {
	}

	public KeyNotFoundException(String message) {
		super(message);
	}

	public KeyNotFoundException(Throwable cause) {
		super(cause);
	}

	public KeyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}