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
	
	

	public Doctor(String dtype, String fName, String lName, Date dob, String address, String email, String title, String bio) {
		super(dtype, fName, lName, dob, address, email);
		this.bio = bio;
		this.title = title;
		
		// TODO Auto-generated constructor stub
	}


	public Doctor() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * 
	 */
	@Column(name = "title")
	private String title;
	
	@Column(name = "bio")
	private String bio;
	
	
	@OneToMany(mappedBy="doctor", cascade=CascadeType.ALL)
	private List<Blog> blogs;
	

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

	public List<Specialty> getSpecialties() {
		return docSpecialties;
	}

	public void setSpecialties(List<Specialty> docspecialties) {
		this.docSpecialties = docspecialties;
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
