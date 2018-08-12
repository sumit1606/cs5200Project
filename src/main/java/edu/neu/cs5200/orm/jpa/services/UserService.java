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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.neu.cs5200.orm.jpa.daos.DoctorDao;
import edu.neu.cs5200.orm.jpa.daos.HealthProviderDao;
import edu.neu.cs5200.orm.jpa.daos.PatientDao;
import edu.neu.cs5200.orm.jpa.daos.PlanDao;
import edu.neu.cs5200.orm.jpa.daos.SpecialtyDao;
import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.entities.Patient;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.entities.Plan;
import edu.neu.cs5200.orm.jpa.entities.Specialty;


@RestController
public class UserService {

	@Autowired
	DoctorDao doctorDao;
	
	@Autowired
	HealthProviderDao hpDao;
	
	@Autowired
	PlanDao planDao;
	
	@Autowired
	SpecialtyDao specialtyDao;
	
	@Autowired
	PatientDao patientDao;
	
	private String user_key = "8959e0a6be0bece2f59e51c7d159ce53";

	
	@PostMapping("/api/user")
	public Patient createUser(@RequestBody Patient p) {
		return patientDao.createPatient(p);
	}
	
	
	
	@PostMapping("/api/user/appointment")
	public void bookAppointment(@RequestBody Doctor d) {
//		String dtype, String fName, String lName, Date dob, String address, String email, String title, String bio
		
		Doctor temp = new Doctor(d.getDtype(), d.getfName(), d.getlName(), d.getDob(), d.getAddress(),d.getEmail(),d.getTitle(),d.getBio());
		Doctor doc = doctorDao.createDoctor(temp);
		
		
//		for(Plan p: d.getPlansSupported()) {
//			HealthProvider hp = hpDao.createHealthProvider(p.getHp());
//			Plan pres = planDao.createPlan(hp, p);
//			planDao.addDoctorToThePlan(pres.getId(), doc);
//		}
		
		for(Specialty s: d.getDocSpecialties()) {
			Specialty tempSpec = specialtyDao.createSpecialty(s);
			doctorDao.addSpecialty(doc.getId(), tempSpec);
		}
		System.out.println(d.getfName() +"  " + d.getlName());
		
	}
	
	
	// getting the doctor from the specialty	
	@GetMapping("/api/doctor/allPlans")
	public  List<Plan> getAllInsurances(HttpServletRequest request) throws IOException {
		Map<String, String[]> parameters = request.getParameterMap();
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.betterdoctor.com/2016-03-01/insurances?");
//		sb.append("&limit=100");
		sb.append("&user_key=");
		sb.append(user_key);
		try {
			URL url = new URL(sb.toString());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        con.setRequestMethod("GET");
	        con.connect();
	        JsonParser jp = new JsonParser(); //from gson
	        JsonElement root = jp.parse(new InputStreamReader((InputStream) con.getContent())); //Convert the input stream to a json element
	        JsonObject rootobj = root.getAsJsonObject();
	        return this.processAllPlans(rootobj);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	
	
	
	private List<Plan> processAllPlans(JsonObject rootobj) {
		List<Plan> allPlans = new ArrayList<>();
		JsonArray userMap = (JsonArray) rootobj.get("data");
		// Object o will be a health provider and each object
		// will have a health Provider and each health provider will have plans
		for (Object o :userMap) {
			String providerName =  ((JsonObject) o).get("name").getAsString();
//			String providerName = providerObject.get("name").getAsString();
			HealthProvider healthProvider = new HealthProvider(providerName);
			JsonArray plans = (JsonArray) ((JsonObject) o).get("plans");
			for(Object a1: plans) {
				String planName = ((JsonObject) a1).get("name").getAsString();
				Plan p = new Plan();
				p.setName(planName);
				p.setHp(healthProvider);
				allPlans.add(p);
			}		
		}
		return allPlans;
	}


		// getting the doctor from the specialty	
		@GetMapping("/api/doctor/specialty")
		public  List<Doctor> getDoctorBySpecialty(HttpServletRequest request) throws IOException {
			Map<String, String[]> parameters = request.getParameterMap();
			StringBuilder sb = new StringBuilder();
			sb.append("https://api.betterdoctor.com/2016-03-01/practices?");
			sb.append("name=");
			String [] strArr= parameters.get("name")[0].split("\\s+");
			sb.append(String.join("%20", strArr));
//			sb.append("&limit=100");
			sb.append("&user_key=");
			sb.append(user_key);
			try {
				URL url = new URL(sb.toString());
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
		        con.setRequestMethod("GET");
		        con.connect();
		        JsonParser jp = new JsonParser(); //from gson
		        JsonElement root = jp.parse(new InputStreamReader((InputStream) con.getContent())); //Convert the input stream to a json element
		        JsonObject rootobj = root.getAsJsonObject();
		        
		        return this.processDoctorBySpecialty(rootobj);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;	
		}
	
	
	// getting the doctor from the first name and the last name
	// first name and the last name are required
	
	@GetMapping("/api/doctor/name")
	public  List<Doctor> getDoctorByName(HttpServletRequest request) throws IOException {
		Map<String, String[]> parameters = request.getParameterMap();
		StringBuilder sb = new StringBuilder();
		sb.append("https://api.betterdoctor.com/2016-03-01/doctors?first_name=");
		sb.append(parameters.get("fName")[0]);
		sb.append("&last_name=");
		sb.append(parameters.get("lName")[0]);
		sb.append("&user_key=");
		sb.append(user_key);
		try {
			URL url = new URL(sb.toString());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        con.setRequestMethod("GET");
	        con.connect();
	        JsonParser jp = new JsonParser(); //from gson
	        JsonElement root = jp.parse(new InputStreamReader((InputStream) con.getContent())); //Convert the input stream to a json element
	        JsonObject rootobj = root.getAsJsonObject();
	        return this.processDoctor(rootobj);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	
	
	private List<Doctor> processDoctorBySpecialty(JsonObject rootobj){
		List<Doctor> allDoctors = new ArrayList<>();
		JsonArray userMap = (JsonArray) rootobj.get("data");
		// Iterate over each object where each object is a nested json object in itself
		for (Object o :userMap) {
			// o is an doctor object
			// get the profile json inside it
			JsonObject address = (JsonObject) ((JsonObject) o).get("visit_address");
			String city = address.get("city").getAsString();
			String state = address.get("state").getAsString();
			String street = address.get("street").getAsString();
			String zip = address.get("zip").getAsString();
			//practice , specialities, insurance are the Json array so we have to iterate over them
			// to get all the data
			JsonArray doctors = (JsonArray) ((JsonObject) o).get("doctors");
			
			for(Object d1: doctors) {
				JsonObject doctor = (JsonObject) ((JsonObject) d1).get("profile");
				String fName= null;
				String lName = null;
				String title = null;
				String bio = null;

				fName = doctor.get("first_name").getAsString();
				lName = doctor.get("last_name").getAsString();
				if(doctor.get("title") != null) {
					title = doctor.get("title").getAsString();
				}
				 bio = doctor.get("bio").getAsString();
				
				JsonArray specialties = (JsonArray) ((JsonObject) d1).get("specialties");
				JsonArray insurances = (JsonArray) ((JsonObject) d1).get("insurances");
				List<Plan> tempPlan = this.getPlans(insurances);
				List<Specialty> tempSpecialty = this.getSpecialities(specialties);
				
				Doctor temp = new Doctor("d",fName, lName,null,street,null,title,bio);
				temp.setDocSpecialties(tempSpecialty);
				temp.setPlansSupported(tempPlan);
				allDoctors.add(temp);
			}		
		}
		return allDoctors;
	}
	
	private List<Doctor> processDoctor(JsonObject rootobj){
		List<Doctor> allDoctors = new ArrayList<>();
		JsonArray userMap = (JsonArray) rootobj.get("data");
		// Iterate over each object where each object is a nested json object in itself
		for (Object o :userMap) {
			// o is an doctor object
			// get the profile json inside it
			JsonObject profile = (JsonObject) ((JsonObject) o).get("profile");
			String fName = profile.get("first_name").getAsString();
			String lName = profile.get("last_name").getAsString();
			String title = profile.get("title").getAsString();
			String bio = profile.get("bio").getAsString();
			//practice , specialities, insurance are the Json array so we have to iterate over them
			// to get all the data
			JsonArray practices = (JsonArray) ((JsonObject) o).get("practices");
			JsonArray specialties = (JsonArray) ((JsonObject) o).get("specialties");
			JsonArray insurances = (JsonArray) ((JsonObject) o).get("insurances");
			
			List<Plan> tempPlan = this.getPlans(insurances);
			List<Specialty> tempSpecialty = this.getSpecialities(specialties);
			
			JsonObject address = null;
			for(Object a1: practices) {
				address = (JsonObject) ((JsonObject) a1).get("visit_address");
				if(address == null)
					break;
				
				String city = address.get("city").getAsString();
				String state = address.get("state").getAsString();
				String street = address.get("street").getAsString();
				String zip = address.get("zip").getAsString();
				Doctor temp = new Doctor("d",fName, lName,null,street,null,title,bio);
				temp.setDocSpecialties(tempSpecialty);
				temp.setPlansSupported(tempPlan);
				allDoctors.add(temp);
				break;
			}		
		}
		return allDoctors;
	}
	
	
	/**
	 * 
	 * @param Jsonarray of insurances
	 * @return List of insurances parsed from that Json array
	 */
	List<Plan> getPlans(JsonArray insurances) {
		List<Plan> lOfPlans = new ArrayList<>();
		for(Object p1: insurances) {
			JsonObject insurance_plan = (JsonObject) ((JsonObject) p1).get("insurance_plan");
			JsonObject insurance_provider = (JsonObject) ((JsonObject) p1).get("insurance_provider");
			String providerName = insurance_provider.get("name").getAsString();
			String planName = insurance_plan.get("name").getAsString();
			HealthProvider currProvider = new HealthProvider(providerName);
			Plan temp = new Plan(planName,currProvider);
			lOfPlans.add(temp);
		}
		return lOfPlans;	
	}
	
	
	
	/**
	 * 
	 * @param Jsonarray of specialities
	 * @return List of speciality parsed from that Json array
	 */
	List<Specialty> getSpecialities(JsonArray specialities) {
		List<Specialty> lOfSpecialty = new ArrayList<>();
		for(Object s1: specialities) {
			String name = ((JsonObject) s1).get("name").getAsString();
			Specialty temp = new Specialty(name);
			lOfSpecialty.add(temp);
		}
		return lOfSpecialty;	
	}

	
}
