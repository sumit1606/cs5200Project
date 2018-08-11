package edu.neu.cs5200.orm.jpa.repositories;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Patient;

public interface PatientRepository extends CrudRepository<Patient, Integer> {

	@Query("select patient from Patient patient where patient.fName=:fn AND patient.lName=:ln")
	public Patient findPatientByName(@Param("fn") String firstName,
											@Param("ln") String lastName);
										
}
