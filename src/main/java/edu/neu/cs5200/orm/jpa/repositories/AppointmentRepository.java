package edu.neu.cs5200.orm.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Appointment;
import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.Patient;



public interface AppointmentRepository extends CrudRepository<Appointment, Integer>{
//	@Query(value="select * from appointment where patient_id=:pid", nativeQuery = true)
	@Query(value="select appointment from Appointment appointment where appointment.patient=:patient")
	public List<Appointment> getAppointmnetsForThisPatient(@Param("patient") Patient patient);
	
	@Query(value="select appointment from Appointment appointment where appointment.doctor=:doctor")
	public List<Appointment> getAppointmnetsForThisDoctor(@Param("doctor") Doctor doctor);
}



