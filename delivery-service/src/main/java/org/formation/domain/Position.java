package org.formation.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Position {

	private Float latitude;
	private Float longitude;
	
}
