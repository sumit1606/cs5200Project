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
import edu.neu.cs5200.orm.jpa.entities.Blog;
import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.HealthPersonnel;
import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.entities.Patient;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.entities.Plan;
import edu.neu.cs5200.orm.jpa.entities.Specialty;


@RestController
public class PersonService {

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
	
	
	@GetMapping("/api/person/{pid}")
	public Person getPersonById(@PathVariable("pid") int pid) throws IOException {
		Object o = personDao.findPersonbyId(pid);
//		Person p;
//		if(o instanceof Patient) {
//			 = 
//		}
		return (Person) o;
	}
	
	@GetMapping("/api/person/{pid}/blogs")
	public List<Blog> getAllBlogs(@PathVariable("pid") int pid) throws IOException {
		List<Blog> allVisibleBlogs = new ArrayList<Blog>();
		Person p = personDao.findPersonbyId(pid);
		if(p.getFollows() != null) {
			for(Person followedPerson :p.getFollows()) {
				Doctor d = (Doctor)followedPerson;
				if(d.getBlogs() !=null) {
					for (Blog b: d.getBlogs()) {
						allVisibleBlogs.add(b);
					}
				}
			}
		}
		return allVisibleBlogs;
	}
		
	
}
