package org.formation.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "torder")
public class Order {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Instant date;
	
	private float discount;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Embedded
	private PaymentInformation paymentInformation;
	
	@Embedded
	private DeliveryInformation deliveryInformation;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
	List<OrderItem> orderItems = new ArrayList<>();

	@Transient
	float total() {
		float total = 0;
		for ( OrderItem item : orderItems ) {
			total += item.getQuantity() * item.getPrice();
		}
		return total - discount*total;
	}
}
