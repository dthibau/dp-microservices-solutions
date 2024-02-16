package org.formation.service.saga;

import org.formation.domain.Ticket;
import org.formation.service.TicketService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.java.Log;

/**
 * Response to CreateOrderSaga commands
 */
@Service
@Log
public class CreateOrderSaga {

	@Value("${channels.order-response}")
	public String ORDER_RESPONSE_CHANNEL;

	private final TicketService ticketService;
	private KafkaTemplate<Long, CommandResponse> commandResponseTemplate;

	public CreateOrderSaga(TicketService ticketService, KafkaTemplate<Long, CommandResponse> commandResponseTemplate) {
		this.ticketService = ticketService;
		this.commandResponseTemplate = commandResponseTemplate;
	}

	@KafkaListener(topics = "#{'${channels.ticket-command}'}", id = "handleCreate")
	public void handleTicketCommand(TicketCommand ticketCommand) throws JsonProcessingException {
		log.info("Receiving command : " + ticketCommand);
		Ticket ticket = null;
		switch (ticketCommand.getCommande()) {
		case "TICKET_CREATE":
			ticket = ticketService.createTicket(ticketCommand.getOrderId(), ticketCommand.getProductRequest());
			break;
		case "TICKET_APPROVE":
			ticket = ticketService.approveTicket(ticketCommand.getOrderId());
			break;
		case "TICKET_REJECT":
			ticket = ticketService.rejectTicket(ticketCommand.getOrderId());
			break;

		}
		commandResponseTemplate.send(ORDER_RESPONSE_CHANNEL,
				new CommandResponse(ticket.getOrderId(), 0, ticketCommand.getCommande()));

	}

}
