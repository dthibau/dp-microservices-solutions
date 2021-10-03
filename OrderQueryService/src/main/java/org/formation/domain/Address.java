package org.formation.domain;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address {

	private String rue;
	private String ville;
	private String codePostal;

}