package edu.neu.cs5200.orm.jpa.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.neu.cs5200.orm.jpa.daos.AdminDao;
import edu.neu.cs5200.orm.jpa.daos.AppointmentDao;
import edu.neu.cs5200.orm.jpa.daos.DoctorDao;
import edu.neu.cs5200.orm.jpa.daos.HealthPersonnelDao;
import edu.neu.cs5200.orm.jpa.daos.HealthProviderDao;
import edu.neu.cs5200.orm.jpa.daos.PatientDao;
import edu.neu.cs5200.orm.jpa.daos.PersonDao;
import edu.neu.cs5200.orm.jpa.daos.PlanDao;
import edu.neu.cs5200.orm.jpa.daos.SpecialtyDao;
import edu.neu.cs5200.orm.jpa.entities.Admin;
import edu.neu.cs5200.orm.jpa.entities.Appointment;
import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.HealthPersonnel;
import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.entities.Patient;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.entities.Plan;
import edu.neu.cs5200.orm.jpa.entities.Specialty;


@RestController
public class HealthPersonnelService {

	@Autowired
	DoctorDao doctorDao;
	
	@Autowired
	HealthProviderDao hpDao;
	
	@Autowired
	AdminDao adminDao;
	
	
	@Autowired
	HealthPersonnelDao hpersonalDao;
	
	@Autowired
	PlanDao planDao;
	
	@Autowired
	SpecialtyDao specialtyDao;
	
	@Autowired
	PatientDao patientDao;
	
	@Autowired
	AppointmentDao appointmentDao;
	
	@Autowired
	PersonDao personDao;
	
	private String user_key = "8959e0a6be0bece2f59e51c7d159ce53";
	
	
	@PutMapping("api/healthPersonnel/{hpid}")
	public void updateHealthPersonnelById(@RequestBody HealthPersonnel a,@PathVariable("hpid") int hpid) {
		HealthPersonnel old = hpersonalDao.findHealthPersonnelById(hpid);
		old.setfName(a.getfName());
		old.setlName(a.getlName());
		old.setPassword(a.getPassword());
		old.setAddress(a.getAddress());
		hpersonalDao.save(old);
	}
	
	@DeleteMapping("/api/healthPersonnel/{hpid}")
	public List<HealthPersonnel> deleteHealthPersonnelById(@PathVariable("hpid") int hpid) throws IOException {
		hpersonalDao.deleteHealthPersonnel(hpid);
		 return hpersonalDao.findAllHealthPersonnel();
	
	}
	
	@GetMapping("/api/healthPersonnel/{hpid}/doctors") 
	public Map<String, List<Doctor>> getAllDoctorsEnrolledInThisPlan(@PathVariable("hpid") int hpid) {
		HealthProvider hp = hpDao.findHealthProviderById(hpersonalDao.findHealthPersonnelById(hpid).getHprovider().getId());
		if (hp != null) {
			return hpDao.findAllDoctorsUsingThisProvider(hp.getId());
		}
		
		return null;
				
	}
	
	@GetMapping("/api/healthPersonnel/{hpid}/patients") 
	public Map<String, List<Patient>> getAllPatientsEnrolledInThisPlan(@PathVariable("hpid") int hpid) {
		HealthProvider hp = hpDao.findHealthProviderById(hpersonalDao.findHealthPersonnelById(hpid).getHprovider().getId());
		if (hp != null) {
			return hpDao.findAllPatientsUsingThisProvider(hp.getId());
		}
		
		return null;
				
	}
	

	@DeleteMapping("/api/plan/{planName}/doctor/{did}")
	public void deleteDoctorFromPlan(@PathVariable("planName") String planName, @PathVariable("did") int did) throws IOException {
		 Plan p = planDao.findPlanByName(planName);
		 doctorDao.removePlan(did, p);
	
	}
	
	@DeleteMapping("/api/plan/{planName}/patient/{pid}")
	public  void deletePatientFromPlan(@PathVariable("planName") String planName,  @PathVariable("pid") int pid) throws IOException {
		  patientDao.deletePlanFromPatient(pid);
	
	}
	
}