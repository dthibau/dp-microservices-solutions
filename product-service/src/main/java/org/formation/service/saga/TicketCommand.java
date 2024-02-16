package org.formation.service.saga;

import java.util.List;

import org.formation.domain.ProductRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketCommand {

	private long orderId;
	
	private String commande;
	
	List<ProductRequest> productRequest;
	
}
