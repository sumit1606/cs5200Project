package edu.neu.cs5200;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.neu.cs5200.orm.jpa.daos.AdminDao;
import edu.neu.cs5200.orm.jpa.daos.BlogDao;
import edu.neu.cs5200.orm.jpa.daos.DoctorDao;
import edu.neu.cs5200.orm.jpa.daos.PatientDao;
import edu.neu.cs5200.orm.jpa.entities.Admin;
import edu.neu.cs5200.orm.jpa.entities.Blog;
import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.Patient;
import edu.neu.cs5200.utitlities.Utility;

@SpringBootApplication
public class Cs5200DoctorManagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Cs5200DoctorManagementApplication.class, args);			
	}
	
	@Autowired
	AdminDao adminDao;
	
	
	@Autowired
	DoctorDao doctorDao;
	
	@Autowired
	PatientDao patientDao;
	
	@Autowired
	BlogDao blogDao;
	
	
	@Override
	public void run(String... args) throws Exception {
		Admin a = new Admin();
		a.setAddress("address");
		a.setDob(new Utility().modifySQLDate(31,2,1991));
		a.setEmail("admin");
		a.setfName("aashish");
		a.setlName("singh");
		a.setDtype("admin");
		a.setPrivilege("CRUD");
		a.setPassword("admin");
		a = adminDao.createAdmin(a);
		
		Doctor d = new Doctor();
		d.setAddress("address");
		d.setDob(new Utility().modifySQLDate(31,2,1991));
		d.setEmail("doc");
		d.setfName("doc");
		d.setlName("doc");
		d.setDtype("doctor");
		d.setPassword("doc");
		
		Doctor d1 = new Doctor();
		d1.setAddress("address");
		d1.setDob(new Utility().modifySQLDate(31,2,1991));
		d1.setEmail("doc1");
		d1.setfName("doc1");
		d1.setlName("doc1");
		d1.setDtype("doctor");
		d1.setPassword("doc1");
		d = doctorDao.save(d);
		d1 = doctorDao.save(d1);
		Blog b = new Blog();
		b.setContent("THis is the firt blog");
		
		Blog b1 = new Blog();
		b1.setContent("THis is the second  blog");
		
		doctorDao.addBlogToDoctor(d.getId(), b);
		doctorDao.addBlogToDoctor(d1.getId(), b1);
		
		
		Patient p = new Patient();
		p.setAddress("address");
		p.setDob(new Utility().modifySQLDate(31,2,1991));
		p.setEmail("pat");
		p.setfName("pat");
		p.setlName("pat");
		p.setDtype("patient");
		p.setPassword("pat");
		patientDao.save(p);
	}
	
}
