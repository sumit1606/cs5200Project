package edu.neu.cs5200.orm.jpa.daos;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.repositories.PersonRepository;
import edu.neu.cs5200.utitlities.Utility;

@Component
public class PersonDao {
	@Autowired
	PersonRepository personRepository;
	

	
	

	
}
