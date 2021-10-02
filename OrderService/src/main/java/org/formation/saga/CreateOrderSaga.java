package org.formation.saga;

import java.util.List;
import java.util.stream.Collectors;

import org.formation.domain.Order;
import org.formation.domain.OrderRepository;
import org.formation.domain.OrderStatus;
import org.formation.domain.event.OrderEvent;
import org.formation.domain.event.PaymentRequestEvent;
import org.formation.domain.event.PaymentResponseEvent;
import org.formation.domain.event.TicketStatusEvent;
import org.formation.service.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class CreateOrderSaga {

	@Value("${app.channel.order-status}")
	String ORDER_STATUS_CHANNEL;
	
	@Value("${app.channel.payment-request}")
	String PAYMENT_REQUEST_CHANNEL;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	KafkaTemplate<Long, OrderEvent> kafkaOrderTemplate;
	
	@Autowired
	KafkaTemplate<Long, PaymentRequestEvent> kafkaPaymentTemplate;
	
	public Order startSaga(Order order) {
		
		// Save in local DataBase
		order.setStatus(OrderStatus.PENDING);
		order = orderRepository.save(order);
		List<ProductRequest> productRequest = order.getOrderItems().stream().map(i -> new ProductRequest(i)).collect(Collectors.toList());

		OrderEvent event = new OrderEvent(order.getId(), productRequest, order.getStatus());
		
		kafkaOrderTemplate.send(ORDER_STATUS_CHANNEL,event);
		
		return order;
	}
	
	@KafkaListener(topics = "#{'${app.channel.ticket-status}'}", id = "handleTicket")
	public void handleTicketResponseAndPatmentRequest(TicketStatusEvent ticketStatusEvent) {
		log.info("TICKET_STATUS :" +ticketStatusEvent.getNewStatus());
		if ( ticketStatusEvent.getNewStatus().equals("PENDING") ) {
			Order order = orderRepository.fullLoad(ticketStatusEvent.getOrderId()).orElseThrow();
			PaymentRequestEvent event = new PaymentRequestEvent(order.getId(),order.getPaymentInformation(),order.getAmount());
			kafkaPaymentTemplate.send(PAYMENT_REQUEST_CHANNEL,event);
		}
	}
	
	@KafkaListener(topics = "#{'${app.channel.payment-response}'}", id = "handlePayment")
	public void handlePaymentResponse(PaymentResponseEvent paymentResponseEvent) {
		
		log.info("PAYMENT_RESPONSE :" +paymentResponseEvent.getOutcome());
		Long orderId = paymentResponseEvent.getRequestId();
		Order order = orderRepository.findById(orderId).orElseThrow();
		
		if ( paymentResponseEvent.getOutcome() ) {
			order.setStatus(OrderStatus.APPROVED);
		} else {
			order.setStatus(OrderStatus.REJECTED);
		}
		orderRepository.save(order);
		
		OrderEvent event = new OrderEvent(order.getId(), null, order.getStatus());
		
		kafkaOrderTemplate.send(ORDER_STATUS_CHANNEL,event);
		
	}
}
