package edu.neu.cs5200.orm.jpa.entities;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Plan {
	@Id
	@GeneratedValue
	(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	@OneToMany(mappedBy="healthInsurancePlan")
	List<Patient> patients;
	
	@ManyToMany(mappedBy="plansSupported")
	private List<Doctor> doctorsEnrolled;
	
	@ManyToOne

	private HealthProvider hp;
	
	
	public Plan() {
		
	}
	
	public Plan(String planName, HealthProvider healthProvider) {
		// TODO Auto-generated constructor stub
		this.name=planName;
		this.hp = healthProvider;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the patients
	 */
	@JsonIgnore
	public List<Patient> getPatients() {
		return patients;
	}

	/**
	 * @param patients the patients to set
	 */
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	/**
	 * @return the hp
	 */

	public HealthProvider getHp() {
		return hp;
	}

	/**
	 * @param hp the hp to set
	 */
	public void setHp(HealthProvider hp) {
		this.hp = hp;
	}




	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Plan [id=" + id + ", name=" + name + ", patients=" + patients + "]";
	}

	public void set(Plan p) {
		this.name = p.getName() != null ? p.getName() : this.name;
		this.hp = p.getHp() != null ? p.getHp() : this.hp;
		
	}

	/**
	 * @return the doctorsEnrolled
	 */
	@JsonIgnore
	public List<Doctor> getDoctorsEnrolled() {
		return doctorsEnrolled;
	}

	/**
	 * @param doctorsEnrolled the doctorsEnrolled to set
	 */
	public void setDoctorsEnrolled(List<Doctor> doctorsEnrolled) {
		this.doctorsEnrolled = doctorsEnrolled;
	}
	
	
	
	
}
