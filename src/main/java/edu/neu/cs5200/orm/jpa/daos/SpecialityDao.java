/**
 * 
 */
package edu.neu.cs5200.orm.jpa.daos;

import org.springframework.beans.factory.annotation.Autowired;
import edu.neu.cs5200.orm.jpa.entities.Specialty;
import edu.neu.cs5200.orm.jpa.repositories.SpecialtyRepository;

/**
 * @author sumitbhanwala
 *
 */
public class SpecialityDao {
	@Autowired
	SpecialtyRepository specialtyRepository;
	
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
		if(this.findSpecialtyByName(s.getSpecialtyName()) != null) {
			return specialtyRepository.save(s);
		} else {
			return this.findSpecialtyByName(s.getSpecialtyName());
		}
	}
	
	
	
	
	
	
	
}
