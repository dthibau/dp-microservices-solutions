package org.formation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Review {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int note;
	
	private String commentaire;
}
