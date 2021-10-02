package org.formation.domain.event;

import java.time.Instant;

import org.formation.domain.PaymentInformation;

import lombok.Data;

@Data
public class PaymentRequestEvent {

	Long requestId;
	Instant instant;
	PaymentInformation PaymentInformation;
	Float amount;
	
	public PaymentRequestEvent(Long requestId, PaymentInformation paymentInformation,
			Float amount) {
		super();
		this.requestId = requestId;
		PaymentInformation = paymentInformation;
		this.amount = amount;
	}
	
	
}
