package org.formation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Review {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int note;
	
	private String commentaire;
}
