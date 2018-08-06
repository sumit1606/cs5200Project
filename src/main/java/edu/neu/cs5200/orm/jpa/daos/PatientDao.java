package edu.neu.cs5200.orm.jpa.daos;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Patient;
import edu.neu.cs5200.orm.jpa.repositories.PatientRepository;
import edu.neu.cs5200.utitlities.Utility;

@Component
public class PatientDao {
	@Autowired
	PatientRepository patientRepository;
	

	
	public void test() {
		Patient p = new Patient();
		p.setAddress("address");
		p.setDob(new Utility().modifySQLDate(31,2,1991));
		p.setDtype("Patient");
		p.setEmail("a@a.com");
		p.setfName("aashish");
		p.setlName("singh");
		p.setDtype("patient");
		System.out.println(p);
		patientRepository.save(p);

	}
}
