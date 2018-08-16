package edu.neu.cs5200.orm.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Appointment;
import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.Patient;



public interface AppointmentRepository extends CrudRepository<Appointment, Integer>{
	@Query("select a from Appointment a where a.patient=:patient")
	public List<Appointment> getAppointmnetsForThisPatient(@Param("patient") Patient patient);
}
