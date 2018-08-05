package edu.neu.cs5200.orm.jpa.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Integer>{
	@Query("select person from Person person where person.fName=:first_name and person.lName=:last_name")
	public Iterable<Person> findPersonByFullName(
			@Param("first_name") String first_name,
			@Param("last_name") String last_name);
}
