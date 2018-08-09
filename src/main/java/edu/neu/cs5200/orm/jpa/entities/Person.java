package edu.neu.cs5200.orm.jpa.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Person {
	@Id
	@GeneratedValue
	(strategy=GenerationType.IDENTITY)
	private int id;
	private String dtype;
	private String fName;
	private String lName;
	private Date dob;
	private String address;
	private String email;
	
	
	
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
	
}
