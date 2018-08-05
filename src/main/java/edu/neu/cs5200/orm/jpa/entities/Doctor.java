/**
 * 
 */
package edu.neu.cs5200.orm.jpa.entities;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sumitbhanwala
 *
 */
@Entity
@Table(name = "Doctor")
public class Doctor extends Person {

	/**
	 * 
	 */
	@Column(name = "title")
	private String title;
	
	@Column(name = "bio")
	private String bio;
	

	@ManyToMany
	@JoinTable(name = "doctor_specialty",joinColumns = @JoinColumn(name = "doc_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "specialty_id", referencedColumnName = "id"))
	private List<Specialty> docspecialties;

	public List<Specialty> getSpecialties() {
		return docspecialties;
	}

	public void setSpecialties(List<Specialty> docspecialties) {
		this.docspecialties = docspecialties;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBio() {
		return bio;
	}


	public void setBio(String bio) {
		this.bio = bio;
	}

}
