package org.formation.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Livreur {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nom;
	
	private String telephone;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	@Embedded
	private Position position;
}
