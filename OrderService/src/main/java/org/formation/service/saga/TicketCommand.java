package org.formation.service.saga;

import java.util.List;

import org.formation.service.ProductRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketCommand {

	private long orderId;
	
	private String command;
	
	List<ProductRequest> productRequest;
	
}
