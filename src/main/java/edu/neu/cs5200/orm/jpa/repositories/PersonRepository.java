package edu.neu.cs5200.orm.jpa.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Integer>{
	@Query("select person from Person person where person.fName=:first_name and person.lName=:last_name")
	public Iterable<Person> findPersonByFullName(
			@Param("first_name") String first_name,
			@Param("last_name") String last_name);
	
//	@Query(value = "SELECT * from doctor_followedby where doc_id=:doc_id and person_id=:person_id ", nativeQuery=true)
	@Query("select p from Person d join d.followedBy p where d.id=:doc_id and p.id=:person_id")
	public List<Person> checkIfPersonFollowThisDoctor(
			@Param("doc_id") int doc_id, 
			@Param("person_id") int person_id);
}
