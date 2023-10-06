package dev.opal.app.exceptions;

public class UsersRetrievalException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UsersRetrievalException(String message) {
		super(message);
	}
}
