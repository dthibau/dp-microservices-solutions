package org.formation.web;

import java.time.LocalDateTime;
import java.util.List;

import org.formation.domain.Address;
import org.formation.domain.OrderItem;

import lombok.Data;

@Data
public class CreateOrderRequest {

	  private long restaurantId;
	  private long consumerId;
	  private LocalDateTime deliveryTime;
	  private List<OrderItem> lineItems;
	  private Address deliveryAddress;
}
