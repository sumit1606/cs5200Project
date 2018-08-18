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
public class AdminService {

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

	
	@PostMapping("/api/admin")
	public Admin createAdmin(@RequestBody Admin a) {
		System.out.println("Admin created");
		return adminDao.createAdmin(a);
	}
	
	
	@GetMapping("/api/admin/{aid}")
	public Admin getAdminById(@PathVariable("aid") int aid) throws IOException {
		return adminDao.findAdminById(aid);
	}
	
	
	@PutMapping("api/admin/{aid}")
	public void updateAdmin(@RequestBody Admin a,@PathVariable("aid") int aid) {
		Admin old = adminDao.findAdminById(aid);
		old.setfName(a.getfName());
		old.setlName(a.getlName());
		old.setPassword(a.getPassword());
		old.setAddress(a.getAddress());
		adminDao.save(old);
	}
	
	@GetMapping("/api/appointments")
	public List<Appointment> getAdminAllAppointments() throws IOException {
		return appointmentDao.findAllAppointments();
	}
	
	@GetMapping("/api/doctors")
	public List<Doctor> getAllDotors() {
		return doctorDao.findAllDoctors();
	}
	
	@GetMapping("/api/patients")
	public List<Patient> getAllPatients() {
		return patientDao.findAllPatients();
	}
	
	
	@GetMapping("/api/healthPersonnels")
	public List<HealthPersonnel> getAllHealthPersonnels() {
		return hpersonalDao.findAllHealthPersonnel();
	}
	
	@DeleteMapping("/api/plan/{pid}")
	public List<Plan> deletePlanById(@PathVariable("pid") int pid) {
		planDao.deletePlanById(pid);;
		return planDao.findAllPlan();
	}	
	
	@DeleteMapping("/api/appointment/{aid}")
	public void deleteAppointmentById(@PathVariable("aid") int aid) {
		appointmentDao.deleteAppointmentById(aid);
	}
	
	
}
