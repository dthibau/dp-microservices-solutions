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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Ticket {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	String orderId;
	
	@Enumerated(EnumType.STRING)
	private TicketStatus status;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	List<ProductRequest> productRequests;
	
	
}
