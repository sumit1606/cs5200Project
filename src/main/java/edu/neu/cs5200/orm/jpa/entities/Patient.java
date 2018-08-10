package edu.neu.cs5200.orm.jpa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Patient extends Person {
	
	@ManyToOne
	private Plan healthInsurancePlan;

	/**
	 * @return the healthInsurancePlan
	 */
	public Plan getHealthInsurancePlan() {
		return healthInsurancePlan;
	}

	/**
	 * @param healthInsurancePlan the healthInsurancePlan to set
	 */
	public void setHealthInsurancePlan(Plan healthInsurancePlan) {
		this.healthInsurancePlan = healthInsurancePlan;
	}
	
	public void addHealthPlan(Plan plan) {
		this.setHealthInsurancePlan(plan);
		List<Patient> patientList = plan.getPatients() ;
		if (patientList == null) {
			patientList = new ArrayList<Patient>();
			patientList.add(this);
			plan.setPatients(patientList);
		} else {
			List<Patient> ptList = plan.getPatients();
			for (Patient p : ptList) {
				if (p.getfName() == this.getfName() && p.getlName() == this.getlName()) {
					return;
				}
			}
			plan.getPatients().add(this);
		}
		
	}
}
