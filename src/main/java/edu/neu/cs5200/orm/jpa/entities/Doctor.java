/**
 * 
 */
package edu.neu.cs5200.orm.jpa.entities;

import javax.persistence.Entity;

/**
 * @author sumitbhanwala
 *
 */
@Entity
public class Doctor extends Person {

	/**
	 * 
	 */
	private String title;
	private String bio;
	
	
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

}
