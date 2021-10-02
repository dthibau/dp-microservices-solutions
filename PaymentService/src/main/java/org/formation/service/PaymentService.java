package org.formation.service;

import java.time.Instant;

import org.formation.domain.event.PaymentRequestEvent;
import org.formation.domain.event.PaymentResponseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class PaymentService {

	@Value("${app.channel.payment-response}")
	String RESPONSE_CHANNEL;

	@Autowired
	KafkaTemplate<Long, PaymentResponseEvent> kafkaTemplate;
	
	@KafkaListener(topics = "#{'${app.channel.payment-request}'}", id = "paymentService")
	public void handlePayment(PaymentRequestEvent paymentRequestEvent ) {
		log.info("PAYMENT-REQUEST" + paymentRequestEvent.getPaymentInformation());
		PaymentResponseEvent responseEvent;
		
		// Dummy Logic
		if ( paymentRequestEvent.getPaymentInformation().getPaymentToken().startsWith("A") ) {
			responseEvent = new PaymentResponseEvent(Instant.now(), true, "",paymentRequestEvent.getRequestId());
		} else {
			responseEvent = new PaymentResponseEvent(Instant.now(), false, "Insufficient funds !",paymentRequestEvent.getRequestId());
		}
		
		log.info("Sending PAYMENT-RESPONSE" + responseEvent.getOutcome());
		kafkaTemplate.send(RESPONSE_CHANNEL, responseEvent);
		
	}
}
