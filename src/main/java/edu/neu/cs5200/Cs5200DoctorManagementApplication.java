package edu.neu.cs5200;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.neu.cs5200.orm.jpa.daos.HealthProviderDao;
import edu.neu.cs5200.orm.jpa.daos.PatientDao;
import edu.neu.cs5200.orm.jpa.daos.PlanDao;
import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.repositories.HealthProviderRepository;

@SpringBootApplication
public class Cs5200DoctorManagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Cs5200DoctorManagementApplication.class, args);
	
	}
	

	
	@Override
	public void run(String... args) throws Exception {

	
		
	}
}
