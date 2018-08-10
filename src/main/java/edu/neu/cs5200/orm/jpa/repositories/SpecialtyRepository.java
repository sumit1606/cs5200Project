/**
 * 
 */
package edu.neu.cs5200.orm.jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.Specialty;;

/**
 * @author sumitbhanwala
 *
 */
	
	public interface SpecialtyRepository extends CrudRepository<Specialty, Integer>{
	
	@Query("SELECT s FROM  Specialty s WHERE s.specialtyName=:name")
	public Specialty findSpecialtyByName(@Param("name") String specialtyName);
		
	}


