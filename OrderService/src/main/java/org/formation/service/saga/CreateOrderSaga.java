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

	@Value("${app.channel.ticket}")
	String TICKET_CHANNEL;

	@Value("${app.channel.payment}")
	String PAYMENT_CHANNEL;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	KafkaTemplate<Long, TicketCommand> ticketCommandTemplate;

	@Autowired
	KafkaTemplate<Long, PaymentCommand> paymentCommandTemplate;

	public void publishTicketCreatedEvent(Order order) {

		ticketCommandTemplate.send(TICKET_CHANNEL,
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
		log.info("handleCreateTicketResponse " + ticketResponse);
		Order order = orderRepository.findById(ticketResponse.getOrderId())
				.orElseThrow(() -> new EntityNotFoundException());

		if (ticketResponse.isOk()) {
			paymentCommandTemplate.send(PAYMENT_CHANNEL,
					new PaymentCommand(order.getId(), order.getAmount(), order.getPaymentInformation()));
		} else {
			// Rejecting order
			order.setStatus(OrderStatus.REJECTED);
			orderRepository.save(order);
		}
	}

	public void handlePaymentResponse(CommandResponse paymentResponse) {
		log.info("handlePaymentResponse " + paymentResponse);

		Order order = orderRepository.findById(paymentResponse.getOrderId())
				.orElseThrow(() -> new EntityNotFoundException());

		if (paymentResponse.isOk()) {
			ticketCommandTemplate.send(TICKET_CHANNEL,
					new TicketCommand(order.getId(), "TICKET_APPROVE", order.getProductRequests()));
			order.setStatus(OrderStatus.APPROVED);
			orderRepository.save(order);

		} else {
			// Rejecting order
			order.setStatus(OrderStatus.REJECTED);
			orderRepository.save(order);
			// Rejacting ticket
			ticketCommandTemplate.send(TICKET_CHANNEL,
					new TicketCommand(order.getId(), "TICKET_REJECT", order.getProductRequests()));
		}
	}
}
