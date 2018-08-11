package edu.neu.cs5200.orm.jpa.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Blog {
	@Id
	@GeneratedValue
	(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String title;
	private String content;
	
	@ManyToMany(mappedBy="blogpostsLiked")
	private List<Person> peopleLiked;
	
	@ManyToOne
	private Doctor doctor;

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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	

	/**
	 * @return the doctor
	 */
	public Doctor getDoctor() {
		return doctor;
	}

	/**
	 * @param doctor the doctor to set
	 */
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	/**
	 * @return the peopleLiked
	 */
	public List<Person> getPeopleLiked() {
		return peopleLiked;
	}

	/**
	 * @param peopleLiked the peopleLiked to set
	 */
	public void setPeopleLiked(List<Person> peopleLiked) {
		this.peopleLiked = peopleLiked;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Blog [id=" + id + ", content=" + content + "]";
	}
}
