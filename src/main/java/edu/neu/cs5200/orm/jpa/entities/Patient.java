package edu.neu.cs5200.orm.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Patient extends Person {
	
	@ManyToOne
	private Plan healthInsurancePlan;
}
