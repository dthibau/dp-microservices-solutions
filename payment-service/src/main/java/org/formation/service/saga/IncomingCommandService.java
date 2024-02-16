package org.formation.service.saga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class IncomingCommandService {

	@Value("${channels.order-response}")
	String ORDER_SAGA_CHANNEL;

	@Autowired
	KafkaTemplate<Long, CommandResponse> kafkaTemplate;
	
	@KafkaListener(topics = "#{'${channels.payment-command}'}", id = "payment")
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
