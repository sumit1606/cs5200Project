package edu.neu.cs5200.orm.jpa.daos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Admin;
import edu.neu.cs5200.orm.jpa.entities.Blog;
import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.HealthPersonnel;
import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.entities.Patient;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.repositories.AdminRepository;
import edu.neu.cs5200.orm.jpa.repositories.DoctorRepository;
import edu.neu.cs5200.orm.jpa.repositories.PersonRepository;

@Component
public class AdminDao {
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	DoctorDao doctorDao;
	
	@Autowired
	DoctorRepository drepo;
	
	@Autowired
	BlogDao blogDao;
	
	@Autowired
	PatientDao patientDao;
	
	@Autowired
	HealthPersonnelDao hpPersonDao;
	
	
	/*1. create different users
	 * 2. update different users
	 * 3
	 * 
	 * 
	 * 
	 * */
	
	public Person createUser (Person p) {
		if (p == null)
			return null;
		
		switch(p.getDtype()) {
			case  "patient": return patientDao.createPatient((Patient)p);
			case "doctor":  return doctorDao.createDoctor((Doctor)p);
			case "healthPersonnel": return hpPersonDao.createHealthPersonnel((HealthPersonnel)p);
			default: return null;
		}
	}
	
	public Admin createAdmin(Admin a) {
		a.setPrivilege("CRUD");
		return adminRepository.save(a);
	}

	

	
	
	

	
}
