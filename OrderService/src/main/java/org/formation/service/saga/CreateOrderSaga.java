package org.formation.service.saga;

import javax.persistence.EntityNotFoundException;

import org.formation.domain.Order;
import org.formation.domain.OrderRepository;
import org.formation.domain.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;

@Service
@Transactional
@Log
public class CreateOrderSaga {

	@Value("${app.channel.order-saga}")
	String ORDER_SAGA_CHANNEL;

	@Value("${app.channel.ticket-command}")
	String TICKET_REQUEST_CHANNEL;

	@Value("${app.channel.payment-command}")
	String PAYMENT_REQUEST_CHANNEL;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	KafkaTemplate<Long, TicketCommand> ticketCommandTemplate;

	@Autowired
	KafkaTemplate<Long, PaymentCommand> paymentCommandTemplate;

	public void startSaga(Order order) {
		log.info("SAGA STARTED for order " + order);

		ticketCommandTemplate.send(TICKET_REQUEST_CHANNEL,
				new TicketCommand(order.getId(), "TICKET_CREATE", order.getProductRequests()));
	}

	@KafkaListener(topics = "#{'${app.channel.order-saga}'}", id = "handleTicket")
	public void handleCommandResponse(CommandResponse commandResponse) {
		switch (commandResponse.getCommand()) {
		case "TICKET_CREATE":
			handleTicketCreateResponse(commandResponse);
			break;
		case "PAYMENT_AUTHORIZE":
			handlePaymentResponse(commandResponse);
			break;
		}
	}

	public void handleTicketCreateResponse(CommandResponse ticketResponse) {
		Order order = orderRepository.findById(ticketResponse.getOrderId())
				.orElseThrow(() -> new EntityNotFoundException());

		if (ticketResponse.isOk()) {
			log.info("SAGA Ticket OK  : sending Payment command" + order.getPaymentInformation());
			paymentCommandTemplate.send(PAYMENT_REQUEST_CHANNEL,
					new PaymentCommand(order.getId(), order.total(), order.getPaymentInformation()));
		} else {
			log.info("SAGA Ticket NOK : rejecting command" + order.getId());
			// Rejecting order
			order.setStatus(OrderStatus.REJECTED);
			orderRepository.save(order);
		}
	}

	public void handlePaymentResponse(CommandResponse paymentResponse) {

		Order order = orderRepository.findById(paymentResponse.getOrderId())
				.orElseThrow(() -> new EntityNotFoundException());

		if (paymentResponse.isOk()) {
			log.info("SAGA Payment OK : Sending TICKET_APPROVE + APPROVE Command locally " + order.getPaymentInformation());
			ticketCommandTemplate.send(TICKET_REQUEST_CHANNEL,
					new TicketCommand(order.getId(), "TICKET_APPROVE", order.getProductRequests()));
			order.setStatus(OrderStatus.APPROVED);
			orderRepository.save(order);

		} else {
			log.info("SAGA Payment NOK : Sending TICKET_Reject + REJECT Command locally " + order.getPaymentInformation());
			// Rejecting order
			order.setStatus(OrderStatus.REJECTED);
			orderRepository.save(order);
			// Rejacting ticket
			ticketCommandTemplate.send(TICKET_REQUEST_CHANNEL,
					new TicketCommand(order.getId(), "TICKET_REJECT", order.getProductRequests()));
		}
	}
}
