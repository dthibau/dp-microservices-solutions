package org.formation.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Produit {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String reference;
	
	private String nom;
	
	private String description;
	
	private Float prixUnitaire;
	
	@OneToMany(cascade = CascadeType.ALL)
	List<Exemplaire> exemplaires;
	
	@Embedded
	private Dimension dimension;
	
	@ManyToOne
	private Fournisseur fournisseur;
	
}
