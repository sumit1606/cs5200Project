package edu.neu.cs5200;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.neu.cs5200.orm.jpa.daos.HealthPersonnelDao;

@SpringBootApplication
public class Cs5200DoctorManagementApplication implements CommandLineRunner {

	@Autowired
	HealthPersonnelDao hpr;
	
	public static void main(String[] args) {

		SpringApplication.run(Cs5200DoctorManagementApplication.class, args);			

	}
	

	
	@Override
	public void run(String... args) throws Exception {
		hpr.test();
	}
}
