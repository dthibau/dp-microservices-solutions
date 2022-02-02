package org.formation.service.saga;

import org.formation.domain.PaymentInformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCommand {

	private long orderId;
	
	private Float amount;
	
	PaymentInformation paymentInformation;
	
}
