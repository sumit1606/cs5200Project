package edu.neu.cs5200.orm.jpa.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.neu.cs5200.orm.jpa.entities.Patient;

public interface PatientRepository extends CrudRepository<Patient, Integer> {

}
