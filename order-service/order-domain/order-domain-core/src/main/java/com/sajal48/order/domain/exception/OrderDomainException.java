package com.sajal48.order.domain.exception;

import com.sajal48.common.domain.exception.DomainException;

public class OrderDomainException extends DomainException {
	public OrderDomainException(String message) {
		super(message);
	}

	public OrderDomainException(String message, Throwable cause) {
		super(message, cause);
	}
}
