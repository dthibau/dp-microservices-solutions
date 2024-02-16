package org.formation.service.saga;

import org.formation.domain.PaymentInformation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentCommand {
	
		private long orderId;
		
		private Float amount;
		
		PaymentInformation paymentInformation;
		
}
