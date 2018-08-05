package edu.neu.cs5200.orm.jpa.daos;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.repositories.PersonRepository;

@Component
public class PersonDao {
	@Autowired
	PersonRepository personRepository;
	
	
	/**
	 * Method: modifySQLDate
	 * @param: int day
	 * @param: int month 
	 * @param: int year 
	 * @return: java.sql.Date
	 */
	public Date modifySQLDate (int day, int month, int year) {

		java.util.Calendar cal = Calendar.getInstance();
		java.util.Date utilDate = new java.util.Date(); // util date
		cal.setTime(utilDate);
		cal.set(Calendar.YEAR, year); // set the year
		cal.set(Calendar.MONTH, month-1); // set the month (0 indexed)
		cal.set(Calendar.DAY_OF_MONTH, day); // set the day  
		java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime()); // sql date
		return sqlDate;
	}
	
	
	public void test() {
		Person p = new Person();
		p.setAddress("address");
		p.setDob(modifySQLDate(31,2,1991));
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
