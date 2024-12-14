package com.sajal48.order.domain.exception;

import com.sajal48.common.domain.exception.DomainException;

public class OrderNotFoundException extends DomainException {
	public OrderNotFoundException(String message) {
		super(message);
	}

	public OrderNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
