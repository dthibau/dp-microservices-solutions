package org.formation.service.saga;

import org.formation.domain.Order;
import org.formation.domain.OrderStatus;
import org.formation.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;

@Service
@Log
@Transactional
public class CreateOrderSaga {

	@Value("${channels.order-response}")
	public String ORDER_RESPONSE_CHANNEL;
	
	@Value("${channels.ticket-command}")
	public String TICKET_COMMAND_CHANNEL;
	
	@Value("${channels.payment-command}")
	public String PAYMENT_COMMAND_CHANNEL;
	
	private final KafkaTemplate<Long,TicketCommand> ticketKafkaTemplate;
	
	private final KafkaTemplate<Long,PaymentCommand> paymentKafkaTemplate;
	
	private final OrderRepository orderRepository;
	
	public CreateOrderSaga(OrderRepository orderRepository, KafkaTemplate<Long,TicketCommand> ticketKafkaTemplate, KafkaTemplate<Long,PaymentCommand> paymentKafkaTemplate) {
		this.orderRepository = orderRepository;
		this.ticketKafkaTemplate = ticketKafkaTemplate;
		this.paymentKafkaTemplate = paymentKafkaTemplate;
	}
	
	
	public void startSaga(Order order) {
		log.info("SAGA STARTED for order " + order);

		ticketKafkaTemplate.send(TICKET_COMMAND_CHANNEL,
				new TicketCommand(order.getId(), "TICKET_CREATE", order.getProductRequests()));
	}

	@KafkaListener(topics = "#{'${channels.order-response}'}", id = "order")
	public void handleCommandResponse(CommandResponse commandResponse) {
		try {
		switch (commandResponse.getCommand()) {
		case "TICKET_CREATE":
			handleTicketCreateResponse(commandResponse);
			break;
		case "PAYMENT_AUTHORIZE":
			handlePaymentResponse(commandResponse);
			break;
		}
		} catch (Exception e) {
			log.severe("Disgarding Saga Message Response" + e);
		}
	}

	public void handleTicketCreateResponse(CommandResponse ticketResponse) {
		Order order = orderRepository.findById(ticketResponse.getOrderId())
				.orElseThrow(() -> new EntityNotFoundException());

		if (ticketResponse.isOk()) {
			log.info("SAGA Ticket OK  : sending Payment command" + order.getPaymentInformation());
			paymentKafkaTemplate.send(PAYMENT_COMMAND_CHANNEL,
					new PaymentCommand(order.getId(), order.total(), order.getPaymentInformation()));
		} else {
			log.info("SAGA Ticket NOK : rejecting command"  +order.getId());
			// Rejecting order
			order.setStatus(OrderStatus.REJECTED);
			orderRepository.save(order);
		}
	}

	public void handlePaymentResponse(CommandResponse paymentResponse) {

		Order order = orderRepository.findById(paymentResponse.getOrderId())
				.orElseThrow(() -> new EntityNotFoundException());

		if (paymentResponse.isOk()) {
			log.info("SAGA Payment OK : Sending TICKET_APPROVE  APPROVE Command locally " + order.getPaymentInformation());
			ticketKafkaTemplate.send(TICKET_COMMAND_CHANNEL,
					new TicketCommand(order.getId(), "TICKET_APPROVE", order.getProductRequests()));
			order.setStatus(OrderStatus.APPROVED);
			orderRepository.save(order);

		} else {
			log.info("SAGA Payment NOK : Sending TICKET_Reject  REJECT Command locally " + order.getPaymentInformation());
			// Rejecting order
			order.setStatus(OrderStatus.REJECTED);
			orderRepository.save(order);
			// Rejacting ticket
			ticketKafkaTemplate.send(TICKET_COMMAND_CHANNEL,
					new TicketCommand(order.getId(), "TICKET_REJECT", order.getProductRequests()));
		}
	}
}
