package com.ebenyx.oraklus.exception;

/**
 * Authentication eception manager.
 *
 * @author Brice-Boris BEDA
 * @version 1.0
 */
public class AuthenticationException extends RuntimeException {
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
