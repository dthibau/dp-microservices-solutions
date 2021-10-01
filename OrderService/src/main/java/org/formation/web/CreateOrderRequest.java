package org.formation.web;

import java.util.List;

import org.formation.domain.DeliveryInformation;
import org.formation.domain.OrderItem;
import org.formation.domain.PaymentInformation;

import lombok.Data;

@Data
public class CreateOrderRequest {

	  private long consumerId;
	  private DeliveryInformation deliveryInformation;
	  private List<OrderItem> lineItems;
	  private PaymentInformation paymentInformation;
}
