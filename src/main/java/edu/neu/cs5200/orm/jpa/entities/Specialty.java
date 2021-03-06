package edu.neu.cs5200.orm.jpa.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "Specialty")
public class Specialty {

	@Id
	@GeneratedValue
	(strategy=GenerationType.IDENTITY)
	
	@Column(name = "id")
	private int id;
	
	@Column(name = "specialtyname")
	private String specialtyName;
	
	@ManyToMany(mappedBy = "docSpecialties")
	
	private List<Doctor> doctors;
	
	
	public Specialty() {
		
	}
	
	public Specialty(String name) {
		this.specialtyName = name;
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
	
	
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}
	
	@JsonIgnore
	public List<Doctor> getDoctors() {
		return doctors;
	}
	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

}
