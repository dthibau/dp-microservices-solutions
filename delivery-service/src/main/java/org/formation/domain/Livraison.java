package org.formation.domain;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Livraison {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private long ticketId;
	
	private long orderId;
	
	@ManyToOne
	private Livreur livreur;
	@Enumerated(EnumType.STRING)
	private Status status;
	

	private Instant creationDate;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livraison other = (Livraison) obj;
		if (id != other.id)
			return false;
		return true;
	}


}
