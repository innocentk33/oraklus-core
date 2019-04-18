package com.ebenyx.oraklus.exception;

import org.springframework.transaction.annotation.Transactional;

/**
 * Data validation exception.
 *
 * @author Brice-Boris BEDA
 * @version 1.0
 */

@Transactional(rollbackFor = ValidationException.class)
public class ValidationException extends RuntimeException {

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
