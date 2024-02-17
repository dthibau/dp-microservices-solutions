package org.formation.service;

import org.formation.domain.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {

	private long orderId;
 	private String status;
 	
 	private Order order;
}
