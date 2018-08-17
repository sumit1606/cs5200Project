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
	

	@Query("select p from Person d join d.followedBy p where d.id=:doc_id and p.id=:person_id")
	public List<Person> checkIfPersonFollowThisDoctor(
			@Param("doc_id") int doc_id, 
			@Param("person_id") int person_id);
	
	@Query("select d from Person d join d.blogpostsLiked p where d.id=:pid and p.id=:bid")
	public List<Person> checkIfPersonLikedThisBlog(
			@Param("pid") int pid, 
			@Param("bid") int bid);
	
	
	@Query("select person from Person person where person.email=:email and person.password=:password")
	public Person findByCredentials(@Param("email") String email, @Param("password") String password);
}
