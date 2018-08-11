package edu.neu.cs5200.orm.jpa.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.neu.cs5200.orm.jpa.repositories.PersonRepository;

@Component
public class PersonDao {
	@Autowired
	PersonRepository personRepository;
	

	
	

	
}
