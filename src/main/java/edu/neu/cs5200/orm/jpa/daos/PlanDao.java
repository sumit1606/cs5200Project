package edu.neu.cs5200.orm.jpa.daos;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.entities.Plan;
import edu.neu.cs5200.orm.jpa.repositories.HealthProviderRepository;
import edu.neu.cs5200.orm.jpa.repositories.PlanRepository;

@Component
public class PlanDao {

		@Autowired
		HealthProviderRepository healthProviderRepository;
		

		@Autowired
		HealthProviderDao healthProviderDao;
	
		@Autowired 
		PlanRepository planRepo;
		
		@Autowired
		DoctorDao doctorDao;
		
		public Plan findPlanById(int id) {
			Optional<Plan> planRes = planRepo.findById(id);
			if(planRes.isPresent())
				return planRes.get();
			
			return null;
		}
		
		public Plan findPlanByName(String name) {
			List<Plan> planRes = (List<Plan>)planRepo.findPlanByName(name);
			if(planRes != null && planRes.size() > 0)
				return planRes.get(0);
			
			return null;
		}
		
		public List<Plan> findAllPlan() {
			 return (List<Plan>)planRepo.findAll();
			
		}
		
		
		public Plan createPlan(HealthProvider hp, Plan p) {
			List<Plan> plans = (List<Plan>)planRepo.findPlanByName(p.getName());
			if (plans != null && plans.size() > 0)
				return plans.get(0);
			Optional<HealthProvider> healthProvider = healthProviderRepository.findById(hp.getId());
			if (healthProvider.isPresent()) {
					p.setHp(hp);
					return planRepo.save(p);
			}
			
			return plans.get(0);
		}
		
		public Plan updatePlan(int id, Plan p) {
			Plan planRes = findPlanById(id);
			if (planRes != null) {
				planRes.set(p);
				planRepo.save(planRes);
			}
			return planRes;
		}
		
		public void deletePlanByName(String name) {
			Plan p = findPlanByName(name);
			this.deletePlanById(p.getId());
		}
		
		public Plan addDoctorToThePlan(int id, Doctor d) {
			Plan plan = this.findPlanById(id);
			Doctor doc = doctorDao.findDoctorbyId(d.getId());
			if (plan != null && doc != null) {
					doctorDao.AddPlan(doc.getId(), plan);
					return this.findPlanById(id);
			}
			
			return plan;

		}
		
		public Plan removeDoctorFromPlan(int id, Doctor d) {
			Plan plan = this.findPlanById(id);
			Doctor doc = doctorDao.findDoctorbyId(d.getId());
			if (plan != null && doc != null) {
				doctorDao.removePlan(doc.getId(), plan);
				return this.findPlanById(id);
			}
			return null; 
		}

		public void deletePlanById(int id) {
			Plan p = this.findPlanById(id);
			if (p != null) {
				for (Doctor d: p.getDoctorsEnrolled()) {
					this.removeDoctorFromPlan(p.getId(), d);
				}
				planRepo.deleteById(p.getId());
			}
		}
		
		public void deleteAll() {
			List<Plan> plans = (List<Plan>) planRepo.findAll();
			for(Plan p : plans) {
				deletePlanById(p.getId());
			}
			planRepo.deleteAll();
		}
		
}
	

