package edu.neu.cs5200;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.neu.cs5200.orm.jpa.daos.AdminDao;
import edu.neu.cs5200.orm.jpa.entities.Admin;
import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.utitlities.Utility;

@SpringBootApplication
public class Cs5200DoctorManagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Cs5200DoctorManagementApplication.class, args);			
	}
	
	@Autowired
	AdminDao adminDao;
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
	}
	
}
