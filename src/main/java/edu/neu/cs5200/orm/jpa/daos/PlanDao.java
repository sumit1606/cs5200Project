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
		
		public Plan createPlan(Plan p) {
			List<Plan> plans = (List<Plan>)planRepo.findPlanByName(p.getName());
			Optional<HealthProvider> healthProvider = healthProviderRepository.findById(4);
			
			
			if (plans != null && plans.size() == 0 && healthProvider.isPresent()) {
					planRepo.save(p);
			}
			
			return null;
		}
		
		public void test() {
			Plan p = new Plan();
			HealthProvider hp = healthProviderDao.findProviderByName("healthP");
			if (hp != null) {
				p.setName("plan1");
				p.setHp(hp);
				this.createPlan(p);
			}
			
		}
	

		
}
	

