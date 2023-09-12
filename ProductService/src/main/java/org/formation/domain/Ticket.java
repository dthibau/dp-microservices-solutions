package org.formation.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private long orderId;

	@Enumerated(EnumType.STRING)
	private TicketStatus status;

	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	List<ProductRequest> productRequests;

	@Transient
	public static Float MAX_WEIGHT = 3.0f;
	
	public static ResultDomain createTicket(Long orderId, List<ProductRequest> productRequest) throws MaxWeightExceededException {
		_checkWeight(productRequest);
		Ticket t = new Ticket();
		t.setOrderId(orderId);
		t.setProductRequests(productRequest);
		t.setStatus(TicketStatus.PENDING);
		return new ResultDomain(t);
	}

	public ResultDomain approveTicket() {
		setStatus(TicketStatus.APPROVED);
		return new ResultDomain(this);	
		}

	public ResultDomain rejectTicket() {
		setStatus(TicketStatus.REJECTED);
		return new ResultDomain(this);	
	}

	public ResultDomain readyToPickUp() {
		setStatus(TicketStatus.READY_TO_PICK);
		return new ResultDomain(this);	
	}
	
	private static void _checkWeight(List<ProductRequest> productRequest) throws MaxWeightExceededException {
		int total = productRequest.stream().map(i -> i.getQuantity()).reduce(0, (a, b) -> a + b) ;
		if ( total > MAX_WEIGHT.intValue() ) {
			throw new MaxWeightExceededException();
		}
	}
}
