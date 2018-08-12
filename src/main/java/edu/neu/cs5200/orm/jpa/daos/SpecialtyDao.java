/**
 * 
 */
package edu.neu.cs5200.orm.jpa.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.Specialty;
import edu.neu.cs5200.orm.jpa.repositories.SpecialtyRepository;

/**
 * @author sumitbhanwala
 *
 */

@Component
public class SpecialtyDao {
	@Autowired
	SpecialtyRepository specialtyRepository;
	
	@Autowired
	DoctorDao doctorDao;
	
	
	
	public Specialty findSpecialtybyId(int id) {
		if(specialtyRepository.findById(id) != null)
			return specialtyRepository.findById(id).get();
		else
			return null;
	}
	
	public Specialty findSpecialtyByName(String specialtyName) {
		return specialtyRepository.findSpecialtyByName(specialtyName);	
	}
	
	public void deleteSpecialtyById(int id) {
		specialtyRepository.deleteById(id);
	}
	
	public Specialty createSpecialty(Specialty s) {
		if(this.findSpecialtyByName(s.getSpecialtyName()) == null) {
			return specialtyRepository.save(s);
		} else {
			return this.findSpecialtyByName(s.getSpecialtyName());
		}
	}
	
	
	public Specialty addDoctorToTheSpecialty(int id, Doctor d) {
		Specialty spec = this.findSpecialtybyId(id);
		Doctor doc = doctorDao.findDoctorbyId(d.getId());
		if (spec != null && doc != null) {
				doctorDao.addSpecialty(doc.getId(), spec);
				return this.findSpecialtybyId(id);
		}
		
		return spec;

	}
	
	public Specialty removeDoctorFromSpecialty(int id, Doctor d) {
		Specialty spec = this.findSpecialtybyId(id);
		Doctor doc = doctorDao.findDoctorbyId(d.getId());
		if (spec != null && doc != null) {
			doctorDao.removeSpecialty(doc.getId(), spec);
			return this.findSpecialtybyId(id);
		}
		return null; 
	}
	
	
	
	
	
	
	
}
