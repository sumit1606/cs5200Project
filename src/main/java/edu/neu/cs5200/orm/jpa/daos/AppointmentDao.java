package edu.neu.cs5200.orm.jpa.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Appointment;
import edu.neu.cs5200.orm.jpa.entities.Patient;
import edu.neu.cs5200.orm.jpa.repositories.AppointmentRepository;

@Component
public class AppointmentDao {

	@Autowired
	AppointmentRepository appointmentRepository;
	
	@Autowired
	PatientDao patientDao;
	
	@Autowired
	DoctorDao doctorDao;
	
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
	
	public Map<Integer, String> getAppointmnetsForThisPatient(int pid) {
		List<Appointment> apts = appointmentRepository.getAppointmnetsForThisPatient(patientDao.findPatientById(pid));
		Map<Integer, String> aptMp = new HashMap<>();
		for (Appointment a : apts) {
			aptMp.put(a.getId(), a.getDate()+" "+a.getDoctor().getfName()+" "+a.getDoctor().getlName()+" "+a.getReason());
		}
		
		return aptMp;
	}
	
	public Map<Integer, String> getAppointmnetsForThisDoctor(int pid) {
		List<Appointment> apts = appointmentRepository.getAppointmnetsForThisDoctor(doctorDao.findDoctorbyId(pid));
		Map<Integer, String> aptMp = new HashMap<>();
		for (Appointment a : apts) {
			aptMp.put(a.getId(), a.getDate()+" "+a.getPatient().getfName()+" "+a.getPatient().getlName()+" "+a.getReason());
		}
		
		return aptMp;
	}
	
}
