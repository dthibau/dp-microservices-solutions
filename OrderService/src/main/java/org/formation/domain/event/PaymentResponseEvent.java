package org.formation.domain.event;

import java.time.Instant;

import lombok.Data;

@Data
public class PaymentResponseEvent {
	Long requestId;
	Instant instant;
	Boolean outcome;
	String reason;
	
	public PaymentResponseEvent() {
		super();
	}
	public PaymentResponseEvent(Instant instant, Boolean outcome, String reason, Long requestId) {
		super();
		this.instant = instant;
		this.outcome = outcome;
		this.reason = reason;
		this.requestId = requestId;
	}
	
	
}
