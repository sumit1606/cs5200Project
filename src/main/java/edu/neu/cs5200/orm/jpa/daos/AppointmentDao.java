package edu.neu.cs5200.orm.jpa.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Appointment;
import edu.neu.cs5200.orm.jpa.repositories.AppointmentRepository;

@Component
public class AppointmentDao {

	@Autowired
	AppointmentRepository appointmentRepository;
	
	public Appointment createAppointment(Appointment ap) {
		return appointmentRepository.save(ap);
		
	}
	
	public List<Appointment> findAllAppointments() {
		return (List<Appointment>)appointmentRepository.findAll();
	}
	
	public void deleteAllAppointments() {
		appointmentRepository.deleteAll();
	}
	
	public Appointment findAppointmentById(int id) {
		Optional<Appointment> ap = appointmentRepository.findById(id);
		if (ap.isPresent()) {
			return ap.get();
		}
		return null;
	}
	
	public void deleteAppointmentById(int id) {
		Appointment ap = this.findAppointmentById(id);
		if (ap != null) {
			appointmentRepository.deleteById(ap.getId());
		}

	}
	

	
}
