package org.formation.service;

import org.formation.service.saga.CommandResponse;
import org.formation.service.saga.PaymentCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class PaymentService {

	@Value("${app.channel.order-saga}")
	String ORDER_SAGA_CHANNEL;

	@Autowired
	KafkaTemplate<Long, CommandResponse> kafkaTemplate;
	
	@KafkaListener(topics = "#{'${app.channel.payment-command}'}", id = "paymentService")
	public void handlePayment(PaymentCommand paymentCommand ) {
		log.info("PAYMENT-REQUEST " + paymentCommand.getPaymentInformation());
		CommandResponse paymentResponse;
		
		// Dummy Logic
		if ( paymentCommand.getPaymentInformation().getPaymentToken().startsWith("A") ) {
			paymentResponse = new CommandResponse(paymentCommand.getOrderId(), 0, "PAYMENT_AUTHORIZE");
		} else {
			paymentResponse = new CommandResponse(paymentCommand.getOrderId(), -1, "PAYMENT_AUTHORIZE");
		}
		
		log.info("Sending PAYMENT-RESPONSE : " + paymentResponse.isOk());
		kafkaTemplate.send(ORDER_SAGA_CHANNEL, paymentResponse);
		
	}
}
