package com.sajal48.order.application.ports.input.message.listener.payment;

import com.sajal48.order.application.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {
	void paymentCompleted(PaymentResponse paymentResponse);
	void paymentCancelled(PaymentResponse paymentResponse);
}
