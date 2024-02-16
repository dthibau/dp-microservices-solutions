package org.formation.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.formation.service.ProductRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name="t_order")
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
	
	@OneToMany(cascade = CascadeType.ALL)
	List<OrderItem> orderItems = new ArrayList<>();
	
 	@Transient
	public float total() {
		float total = orderItems.stream().map(i -> i.getPrice() * i.getQuantity()).reduce(0f, (a, b) -> a + b) ;
 		return total - discount*total;
 	}

	@Transient
	public List<ProductRequest> getProductRequests() {
		return getOrderItems().stream().map(i -> new ProductRequest(i)).toList();
	}
	
}
