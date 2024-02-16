package org.formation.domain;

import java.util.List;

import org.formation.service.ResultDomain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	Long orderId;

	@Enumerated(EnumType.STRING)
	private TicketStatus status;

	@OneToMany(cascade = CascadeType.ALL)
	List<ProductRequest> productRequests;

	@Transient
	public static Float MAX_WEIGHT = 3.0f;

	public static ResultDomain createTicket(Long orderId, List<ProductRequest> productRequest)
			throws MaxWeightExceededException {
		_checkWeight(productRequest);
		Ticket t = new Ticket();
		t.setOrderId(orderId);
		t.setProductRequests(productRequest);
		t.setStatus(TicketStatus.PENDING);
		return new ResultDomain(null,t);
	}

	public ResultDomain approveTicket() {
		TicketStatus oldStatus = this.status;
		setStatus(TicketStatus.CREATED);
		return new ResultDomain(oldStatus,this);
	}

	public ResultDomain rejectTicket() {
		TicketStatus oldStatus = this.status;
		setStatus(TicketStatus.REJECTED);
		return new ResultDomain(oldStatus,this);
	}

	public ResultDomain readyToPickUp() {
		TicketStatus oldStatus = this.status;
		setStatus(TicketStatus.READY_TO_PICK);
		return new ResultDomain(oldStatus, this);
	}

	private static void _checkWeight(List<ProductRequest> productRequest) throws MaxWeightExceededException {
			int total = productRequest.stream().map(i -> i.getQuantity()).reduce(0, (a, b) -> a + b) ;
			if ( total > MAX_WEIGHT.intValue() ) {
				throw new MaxWeightExceededException();
			}
		}

}
