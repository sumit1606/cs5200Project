package edu.neu.cs5200.orm.jpa.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;

import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.entities.Plan;
import edu.neu.cs5200.orm.jpa.entities.Specialty;

import javax.servlet.http.HttpServletRequest;

import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserService {

	private String user_key = "c12b88e61fb35b5690f55e6e1886bcc0";
	@GetMapping("/api/user/test")
	public  void testUser() {
		System.out.println("Got here");
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
			sb.append("&limit=100");
			sb.append("&user_key=");
			sb.append(user_key);
			System.out.println(sb.toString());
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
		sb.append("&limit=100");
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
				temp.setSpecialties(tempSpecialty);
				temp.setDocPlans(tempPlan);
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
				temp.setSpecialties(tempSpecialty);
				temp.setDocPlans(tempPlan);
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
