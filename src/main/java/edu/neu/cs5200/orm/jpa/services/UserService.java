package edu.neu.cs5200.orm.jpa.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

import javax.servlet.http.HttpServletRequest;

import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserService {

	private String user_key = "c12b88e61fb35b5690f55e6e1886bcc0";
	@GetMapping("/api/user/test")
	public  void testUser() {
		System.out.println("Got here");
	}
	
	
	@GetMapping("/api/doctor")
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
	
	private List<Doctor> processDoctor(JsonObject rootobj){
		List<Doctor> allDoctors = new ArrayList<>();
		JsonArray userMap = (JsonArray) rootobj.get("data");
		for (Object o :userMap) {
			JsonObject profile = (JsonObject) ((JsonObject) o).get("profile");
			String fName = profile.get("first_name").getAsString();
			String lName = profile.get("last_name").getAsString();
			String title = profile.get("title").getAsString();
			String bio = profile.get("bio").getAsString();
			
			JsonArray practices = (JsonArray) ((JsonObject) o).get("practices");
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
				allDoctors.add(temp);
			}
				
		}
		return allDoctors;
	}

	
}
