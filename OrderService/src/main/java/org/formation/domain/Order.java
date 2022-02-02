package org.formation.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.formation.service.ProductRequest;

import lombok.Data;

@Entity
@Data
@Table(name = "torder")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	public float total() {
		float total = orderItems.stream().map(i -> i.getPrice() * i.getQuantity()).reduce(0f, (a, b) -> a + b) ;
		return total - discount*total;
	}

	@Transient
	public List<ProductRequest> getProductRequests() {
		return getOrderItems().stream().map(i -> new ProductRequest(i)).collect(Collectors.toList());

	}
}
