package edu.neu.cs5200.orm.jpa.daos;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.neu.cs5200.orm.jpa.entities.Patient;
import edu.neu.cs5200.orm.jpa.entities.Plan;
import edu.neu.cs5200.orm.jpa.repositories.PatientRepository;
import edu.neu.cs5200.orm.jpa.repositories.PlanRepository;

@Component
public class PatientDao {
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	PlanRepository planRepo;
	
	@Autowired
	PersonDao personDao;
	
	
	public Patient findPatientByName(Patient p) {
		return patientRepository.findPatientByName(p.getfName(), p.getlName());
	
	}
	
	public Patient findPatientById(int id) {
		 Optional<Patient> pRes = patientRepository.findById(id);
		 if (pRes.isPresent())
			 return pRes.get();
		 return null;
	}
	
	public List<Patient> findAllPatients() {

		return (List<Patient>)patientRepository.findAll();
	}
	
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
				}
				
			}
		
			return patientRepository.save(ps);
		}
		return ps;
	}
	
	public Patient updatePatient(Patient p) {
		Patient ps = patientRepository.findPatientByName(p.getfName(), p.getlName());
		if (ps == null)
			return null;
		
		ps.set(p);
		if (p.getHealthInsurancePlan() != null) {
			Optional<Plan> plsRes = planRepo.findById(p.getHealthInsurancePlan().getId());
			if (plsRes.isPresent()) {
				ps.setHealthInsurancePlan(plsRes.get());
			}
		}
		
		return patientRepository.save(ps);
	}
	
	public void deletePatientById(int id) {
		 Optional<Patient> p = patientRepository.findById(id);
		 if (p.isPresent()) {
			personDao.removeFollowingMappingIfPersonDeleted(p.get().getId());
			patientRepository.deleteById(p.get().getId());
		 }
			 
	}

	
	public void deleteAll() {
		List<Patient> pts = this.findAllPatients();
		for(Patient pt: pts) {
			personDao.removeFollowingMappingIfPersonDeleted(pt.getId());
			
		}
		patientRepository.deleteAll();
	}
	
}
