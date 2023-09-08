package org.formation.domain;

import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Courier {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String nom;
	
	private String telephone;

	@Embedded
	private Position position;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Review> reviews;
}
