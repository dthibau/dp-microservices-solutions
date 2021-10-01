package org.formation.web;

import java.time.LocalDateTime;
import java.util.List;

import org.formation.domain.Address;
import org.formation.domain.OrderItem;
import org.formation.domain.PaymentInformation;

import lombok.Data;

@Data
public class CreateOrderRequest {

	  private List<OrderItem> lineItems;
	  private Address deliveryAddress;
	  private PaymentInformation paymentInformation;
}
