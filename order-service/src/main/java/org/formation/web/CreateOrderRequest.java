package org.formation.web;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.formation.domain.Address;
import org.formation.domain.DeliveryInformation;
import org.formation.domain.Order;
import org.formation.domain.OrderItem;
import org.formation.domain.PaymentInformation;

import lombok.Data;

@Data
public class CreateOrderRequest {

	private long restaurantId;
	private long consumerId;
	private LocalDateTime deliveryTime;
	private List<OrderItem> lineItems;
	private Address deliveryAddress;
	private PaymentInformation paymentInformation;

	public Order getOrder() {
		Order order = new Order();
		order.setDate(Instant.now());
		DeliveryInformation df = new DeliveryInformation();
		df.setAddress(deliveryAddress);
		order.setDeliveryInformation(df);
		order.setOrderItems(lineItems);
		order.setPaymentInformation(paymentInformation);

		return order;
	}
}
