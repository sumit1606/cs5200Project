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
	

	
	
	public void test() {
		Person p = new Person();
		p.setAddress("address");
		p.setDob(new Utility().modifySQLDate(31,2,1991));
		p.setDtype("Person- test");
		p.setEmail("a@a.com");
		p.setfName("aashish");
		p.setlName("singh");
		System.out.println(p);
		personRepository.save(p);
		
		List<Person> pList = (List<Person>) personRepository.findPersonByFullName(p.getfName(), p.getlName());
		if (pList!=null && pList.size()>0) {
			for(Person thisP : pList) {
				System.out.println("Fectched: ");
				System.out.println(thisP);
		
			}
		}
		
		
	}
	
}
