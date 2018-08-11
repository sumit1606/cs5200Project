package edu.neu.cs5200.orm.jpa.entities;



import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Person {
	@Id
	@GeneratedValue
	(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private String dtype;
	
	private String fName;
	private String lName;
	private Date dob;
	private String address;
	private String email;
	
	@ManyToMany
	@JoinTable(name="person_follow_doctor", joinColumns = @JoinColumn(name="doc_id", referencedColumnName="id"), inverseJoinColumns = @JoinColumn(name="person_id", referencedColumnName="id"))
	private List<Person> followedBy;
	
	@ManyToMany(mappedBy="followedBy")
	private List<Person> follows;
	
	
	@ManyToMany
	@JoinTable(name="person_liked_blog", joinColumns = @JoinColumn(name="person_id", referencedColumnName="id"), inverseJoinColumns = @JoinColumn(name="blog_id", referencedColumnName="id"))
	private List<Blog> blogpostsLiked;


	/**
	 * @return the followedBy
	 */
	public List<Person> getFollowedBy() {
		return followedBy;
	}


	/**
	 * @param followedBy the followedBy to set
	 */
	public void setFollowedBy(List<Person> followedBy) {
		this.followedBy = followedBy;
	}


	/**
	 * @return the follows
	 */
	public List<Person> getFollows() {
		return follows;
	}


	/**
	 * @param follows the follows to set
	 */
	public void setFollows(List<Person> follows) {
		this.follows = follows;
	}


	/**
	 * @return the blogpostsLiked
	 */
	public List<Blog> getBlogpostsLiked() {
		return blogpostsLiked;
	}


	/**
	 * @param blogpostsLiked the blogpostsLiked to set
	 */
	public void setBlogpostsLiked(List<Blog> blogpostsLiked) {
		this.blogpostsLiked = blogpostsLiked;
	}


	/**
	 * @param dtype
	 * @param fName
	 * @param lName
	 * @param dob
	 * @param address
	 * @param email
	 */
	public Person() {

	}

	
	/**
	 * @param dtype
	 * @param fName
	 * @param lName
	 * @param dob
	 * @param address
	 * @param email
	 */
	public Person(String dtype, String fName, String lName, Date dob, String address, String email) {
		this.dtype = dtype;
		this.fName = fName;
		this.lName = lName;
		this.dob = dob;
		this.address = address;
		this.email = email;
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
	 * @return the fName
	 */
	public String getfName() {
		return fName;
	}
	/**
	 * @param fName the fName to set
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}
	/**
	 * @return the lName
	 */
	public String getlName() {
		return lName;
	}
	/**
	 * @param lName the lName to set
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}
	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}
	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String toString() {
		return this.id+" "+this.fName+" "+this.lName+" "+this.dob+" "+this.address+" "+this.email;
	}
	/**
	 * @return the dtype
	 */
	public String getDtype() {
		return dtype;
	}
	/**
	 * @param dtype the dtype to set
	 */
	public void setDtype(String dtype) {
		this.dtype = dtype;
	}
	
	public void set (Person obj) {
		this.fName = obj.getfName() != null ? obj.getfName() : this.getfName();
		this.lName = obj.getlName() != null ? obj.getlName() : this.getlName();
		this.address = obj.getAddress() != null ? obj.getAddress() : this.getAddress();
		this.dob = obj.getDob() != null ? obj.getDob() : this.getDob();
		this.email = obj.getEmail() != null ? obj.getEmail() : this.getEmail();
	}
	

	
}
