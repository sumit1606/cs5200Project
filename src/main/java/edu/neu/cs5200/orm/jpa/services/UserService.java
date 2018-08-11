package edu.neu.cs5200.orm.jpa.services;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserService {

	
	@GetMapping("/api/user/test")
	public  void testUser() {
		System.out.println("Got here");
	}

	
}
