package edu.neu.cs5200.orm.jpa.daos;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Patient;
import edu.neu.cs5200.orm.jpa.entities.Plan;
import edu.neu.cs5200.orm.jpa.repositories.PatientRepository;
import edu.neu.cs5200.orm.jpa.repositories.PlanRepository;
import edu.neu.cs5200.utitlities.Utility;

@Component
public class PatientDao {
	
	private Utility utility = new Utility();
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	PlanRepository planRepo;
	
	public Patient createPatient(Patient p) {
		Patient ps = patientRepository.findPatientByName(p.getfName(), p.getlName());
		if (ps == null) {
			 ps = new Patient();
			 ps.setfName(p.getfName());
			 ps.setlName(p.getlName());
			 ps.setAddress(p.getAddress());
			 
//			 Discuss how to handle this
//			 ps.setDob(p.getDob());
			 
			 ps.setDtype("patient");
			 ps.setEmail(p.getEmail());
			if (p.getHealthInsurancePlan() !=  null) {
				Optional<Plan> plsRes = planRepo.findById(p.getHealthInsurancePlan().getId());
				if (plsRes.isPresent()) {
					Plan pls = plsRes.get();
					ps.addHealthPlan(pls);
					planRepo.save(pls);
				}
				
			}
		
			return patientRepository.save(ps);
		}
		return null;
	}
	
	
	

}
