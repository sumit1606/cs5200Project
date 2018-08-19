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
public class DoctorService {

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

	

	
	@PutMapping("api/doctor/{did}")
	public void updateDoctor(@RequestBody Doctor d,@PathVariable("did") int did) {
		Doctor old = doctorDao.findDoctorbyId(did);
		old.setfName(d.getfName());
		old.setlName(d.getlName());
		old.setPassword(d.getPassword());
		old.setAddress(d.getAddress());
		old.setTitle(d.getTitle());
		old.setBio(d.getBio());
		doctorDao.save(old);
	}
	
	@GetMapping("/api/doctor/{did}")
	public Doctor findDoctorbyId(@PathVariable("did") int did) throws IOException {
		return doctorDao.findDoctorbyId(did);
	}
	
	@GetMapping("/api/doctor/{pid}/appointments")
	public  Map<Integer, String> getAppointmentForDoctorById(@PathVariable("pid") int pid) throws IOException {
		return appointmentDao.getAppointmnetsForThisDoctor(pid);	
	}
	
	@DeleteMapping("/api/doctor/{pid}/appointments/{aid}")
	public void deleteAppointment(@PathVariable("pid") int pid, @PathVariable("aid") int aid) throws IOException {
		 appointmentDao.deleteAppointmentById(aid);	
	
	}
	
	@GetMapping("/api/doctor/{id}/plans")
	public  List<Plan> getAllPlansForDoctor(@PathVariable("id") int id) throws IOException {
		 return doctorDao.findDoctorbyId(id).getPlansSupported();
	}
	
	
	@DeleteMapping("/api/doctor/{did}/plans/{pid}")
	public Doctor deletePlanFromDoctor(@PathVariable("did") int did, @PathVariable("pid") int pid) throws IOException {
		 return doctorDao.removePlan(did, planDao.findPlanById(pid));
	
	}
	

	@PutMapping("/api/doctor/{did}/plans/{pid}")
	public Doctor addPlanToSupported(@PathVariable("did") int did, @PathVariable("pid") int pid) throws IOException {
		return doctorDao.AddPlan(did, planDao.findPlanById(pid));
	}
	
	@DeleteMapping("/api/doctor/{did}")
	public List<Doctor> deleteDoctorById(@PathVariable("did") int did) throws IOException {
		 doctorDao.deleteDoctorById(did);
		 return doctorDao.findAllDoctors();
	}
	
	
	@GetMapping("/api/doctorByName")
	public  List<Doctor> getDoctor(HttpServletRequest request) throws IOException {
		Map<String, String[]> parameters = request.getParameterMap();
		String fName = parameters.get("fName")[0];
		String lName = parameters.get("lName")[0];
		List<Doctor> foundedDocs = new ArrayList<Doctor>();
		Doctor fromLocalDataBase = doctorDao.findDoctorByName(fName,lName);
		foundedDocs.add(fromLocalDataBase);
		return foundedDocs;	
	}
	
	@GetMapping("/api/doctor/{did}/follow/{pid}")
	public void followDoctor(@PathVariable("did") int did, @PathVariable("pid") int pid) throws IOException {
		personDao.followDoctor(did, pid);
		return;
	}
	
	@GetMapping("/api/doctor/{did}/unfollow/{pid}")
	public void unfollowDoctor(@PathVariable("did") int did, @PathVariable("pid") int pid) throws IOException {
		personDao.unfollowDoctor(did, pid);
		return;
	}
	
}
