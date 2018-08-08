package edu.neu.cs5200.orm.jpa.daos;


import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
				return null;
			Optional<HealthProvider> healthProvider = healthProviderRepository.findById(hp.getId());
			if (healthProvider.isPresent()) {
					p.setHp(hp);
					return planRepo.save(p);
			}
			
			return null;
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
			if (p != null) {
				planRepo.deleteById(p.getId());
			}
		}

		
}
	

