
package edu.neu.cs5200.orm.jpa.daos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Blog;
import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.Plan;
import edu.neu.cs5200.orm.jpa.repositories.DoctorRepository;
/**
 * @author sumitbhanwala
 *
 */
@Component
public class DoctorDao {
	@Autowired
	DoctorRepository doctorRepository;
	
	@Autowired
	PersonDao personDao;
	
	@Autowired
	BlogDao bdao;
	
	@Autowired
	PlanDao planDao;
	
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
		Doctor d = this.findDoctorbyId(id);
		if (d != null)
		{
			personDao.removeFollowingMappingIfPersonDeleted(d.getId());
			doctorRepository.deleteById(id);
		}
		
	}
	

	
	public void deleteAll() {
		List<Doctor> pts = (List<Doctor>) doctorRepository.findAll();
		for(Doctor pt: pts) {
			personDao.removeFollowingMappingIfPersonDeleted(pt.getId());
			
		}
		doctorRepository.deleteAll();
	}
	
	
	// fixed: there should be == check with null
	public Doctor createDoctor(Doctor d) {
		if (this.findDoctorByName(d.getfName(), d.getlName()) ==null) {
			return doctorRepository.save(d);
		}
		return null;
	}
	
	public List<Blog> findAllBlogsForThisDoctor(int id) {
		Doctor doc = findDoctorbyId(id);
		if (doc != null) {
			return doc.getBlogs();
		}
		return null;
	}
	
	public Doctor updateDoctor(int id, Doctor d) {
		Doctor doc = findDoctorbyId(id);
		if (doc != null) {
			doc.set(d);
			doc.setTitle(d.getTitle());
			doc.setBio(d.getBio());
			return doctorRepository.save(doc);
			
		}
		
		return null;
	}
	
	public Doctor addBlogToDoctor(int id, Blog b) {
		Doctor doc = findDoctorbyId(id);
		if (doc != null) {
			if (doc.getBlogs() == null) {
				List<Blog> blogs = new ArrayList<>();
				doc.setBlogs(blogs);
			}
			b.setDoctor(doc);
			doc.getBlogs().add(b);
			
			return doctorRepository.save(doc);
			
		}
		return null;
	}
	
	public Doctor AddPlan(int id, Plan p) {
		Doctor doc = this.findDoctorbyId(id);
		Plan pres = planDao.findPlanById(p.getId());
		if (doc != null && pres != null) {

			List<Doctor> planExistsInDoctor = doctorRepository.checkIfPlanExistsInDoctor(doc.getId(), pres.getId());
			if (planExistsInDoctor.size()==0) {				
				if (doc.getPlansSupported() == null) {
					 doc.setPlansSupported(new ArrayList<Plan> ());
				}
				doc.getPlansSupported().add(pres);
				return doctorRepository.save(doc);
			} 
			
			
		}
		return null;
	}
	
	public Doctor removePlan(int id, Plan p) {
		Doctor doc = this.findDoctorbyId(id);
		Plan pres = planDao.findPlanById(p.getId());
		if (doc != null && pres != null) {

			List<Doctor> planExistsInDoctor = doctorRepository.checkIfPlanExistsInDoctor(doc.getId(), pres.getId());
			if (planExistsInDoctor.size() > 0) {				
				Iterator<Plan> plansI = doc.getPlansSupported().iterator();
				while(plansI.hasNext()) {
					Plan temp = plansI.next();
					if (temp.getId() == pres.getId()) {
						plansI.remove();
						break;
					}
				}
				return doctorRepository.save(doc);
			} 
		}
		return null;
	}
	
	
}
