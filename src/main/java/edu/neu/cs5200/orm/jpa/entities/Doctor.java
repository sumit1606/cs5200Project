/**
 * 
 */
package edu.neu.cs5200.orm.jpa.entities;

import java.util.List;
import java.sql.Date;

import javax.persistence.*;

/**
 * @author sumitbhanwala
 *
 */
@Entity
@Table(name = "Doctor")
public class Doctor extends Person {
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "bio")
	private String bio;
	
	
	@OneToMany(mappedBy="doctor", cascade=CascadeType.ALL)
	private List<Blog> blogs;
	

		@ManyToMany
		@JoinTable(name="doctor_plan", joinColumns = @JoinColumn(name="doc_id", referencedColumnName="id"), inverseJoinColumns = @JoinColumn(name="plan_id", referencedColumnName="id"))
		private List<Plan> plansSupported;
	/**
	 * @return the blogs
	 */
	public List<Blog> getBlogs() {
		return blogs;
	}


	/**
	 * @param blogs the blogs to set
	 */
	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}


	@ManyToMany
	@JoinTable(name = "doctor_specialty",joinColumns = @JoinColumn(name = "doc_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "specialty_id", referencedColumnName = "id"))
	private List<Specialty> docSpecialties;
	

	public Doctor(String dtype, String fName, String lName, Date dob, String address, String email, String title, String bio) {
		super(dtype, fName, lName, dob, address, email);
		this.bio = bio;
		this.title = title;
		// TODO Auto-generated constructor stub
	}


	public Doctor() {
		// TODO Auto-generated constructor stub
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

	
	/**
	 * @return the plansSupported
	 */
	public List<Plan> getPlansSupported() {
		return plansSupported;
	}


	/**
	 * @param plansSupported the plansSupported to set
	 */
	public void setPlansSupported(List<Plan> plansSupported) {
		this.plansSupported = plansSupported;
	}


	/**
	 * @return the docSpecialties
	 */
	public List<Specialty> getDocSpecialties() {
		return docSpecialties;
	}


	/**
	 * @param docSpecialties the docSpecialties to set
	 */
	public void setDocSpecialties(List<Specialty> docSpecialties) {
		this.docSpecialties = docSpecialties;
	}
	
}
