
package edu.neu.cs5200.orm.jpa.daos;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.repositories.DoctorRepository;
import edu.neu.cs5200.orm.jpa.repositories.PersonRepository;

/**
 * @author sumitbhanwala
 *
 */
@Component
public class DoctorDao {
	@Autowired
	DoctorRepository doctorRepository;
	
	public Doctor findDoctorbyId(int id) {
		if(doctorRepository.findById(id) != null)
			return doctorRepository.findById(id).get();
		else
			return null;
	}
	
	
	public Doctor findDoctorByName(String fname, String lname) {
		if(doctorRepository.findDoctorByName(fname, fname)!= null)
			return doctorRepository.findDoctorByName(fname, lname);
		else
			return null;
	}
	
	
	public void deleteDoctorById(int id) {
		doctorRepository.deleteById(id);
	}
	
	
	public Doctor createDoctor(Doctor d) {
		if (this.findDoctorByName(d.getfName(), d.getlName()) !=null) {
			return doctorRepository.save(d);
		}
		return null;
	}
	
	public void test() {
				
	}
	
}
