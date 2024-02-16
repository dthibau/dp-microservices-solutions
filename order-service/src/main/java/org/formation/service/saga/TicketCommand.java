package org.formation.service.saga;

import java.util.List;

import org.formation.service.ProductRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketCommand {

	Long orderId; 
	
	String commande;
	
	List<ProductRequest> productRequest;
	
}
