package edu.neu.cs5200.orm.jpa.daos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
	
	public Admin findAdminByName(Admin p) {
		return adminRepository.findAdminByName(p.getfName(), p.getlName());
	
	}
	
	public Admin createAdmin(Admin a) {
		a.setPrivilege("CRUD");
		Admin ad = this.findAdminByName(a);
		if( ad == null)
			return adminRepository.save(a);
		return ad;
	}
	
	public Admin findAdminRoleExists() {
		 Admin admin = adminRepository.findByIfAdminExists("admin");
		
		 return admin;
	}
	
	public Admin findAdminById(int aid) {
		 Optional<Admin> admin = adminRepository.findById(aid);
		 if (admin.isPresent())
			 return admin.get();
		 return null;
	}

	public void save(Admin old) {
		adminRepository.save(old);
	}
	
	
}
