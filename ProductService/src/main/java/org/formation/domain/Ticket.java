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

import org.formation.domain.event.OrderEvent;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Ticket {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	Long orderId;
	
	@Enumerated(EnumType.STRING)
	private TicketStatus status;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	List<ProductRequest> productRequests;
	
	public static Ticket createTicket(OrderEvent orderEvent) {
		Ticket t = new Ticket();
		t.setOrderId(orderEvent.getOrderId());
		t.setProductRequests(orderEvent.getProductRequest());
		t.setStatus(TicketStatus.PENDING);
		return t;
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
}
