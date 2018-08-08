

package edu.neu.cs5200.orm.jpa.daos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.entities.Plan;
import edu.neu.cs5200.orm.jpa.repositories.HealthProviderRepository;
import edu.neu.cs5200.orm.jpa.repositories.PlanRepository;

@Component
public class HealthProviderDao {
	@Autowired
	HealthProviderRepository healthProviderRepo;
	
	@Autowired 
	PlanRepository planRepo;
	
	public HealthProvider findProviderByName(String name) {
		Optional<HealthProvider> hp = healthProviderRepo.findHealthProviderByName(name);
		if (hp.isPresent()) {
			return hp.get();
		}
		
		return null;
	}
	
	public HealthProvider createHealthProvider(HealthProvider hp) {
		HealthProvider hpRes = findProviderByName(hp.getName());
		if(hpRes != null)
			return hpRes;
		
		hpRes = new HealthProvider();
		hpRes.setName(hp.getName());
		if (hp.getPlans() != null && hp.getPlans().size() > 0) {
			for(Plan p : hp.getPlans()) {
					hpRes.addPlan(p);
			}
		}
		
		return healthProviderRepo.save(hpRes);
	}
	
	public void deleteHealthProviderByName(String hpName) {
		HealthProvider hpRes = this.findProviderByName(hpName);
		if (hpRes != null)
			healthProviderRepo.deleteById(hpRes.getId());
	}
	
	public HealthProvider findHealthProviderById(int hpId) {
		 Optional<HealthProvider> hpRes = healthProviderRepo.findById(hpId);
		 if (hpRes.isPresent())
			 return hpRes.get();
		 return null;
	}
	
	public List<HealthProvider> findAllHealthProvider() {

		 List<HealthProvider> hpRes = (List<HealthProvider>)healthProviderRepo.findAll();
		 return hpRes;
	
	}
	
	public HealthProvider updateHealthProvider(int hpId, HealthProvider hp) {
		HealthProvider hpRes = this.findHealthProviderById(hpId);
		if (hpRes != null ) {
			hpRes.set(hp);
			return healthProviderRepo.save(hpRes);
		}
		return null;
	}
	
	public HealthProvider addPlanToHealthProvider(HealthProvider hp, Plan p) {
		HealthProvider hpRes = this.findHealthProviderById(hp.getId());
		if (hpRes != null) {
			hpRes.addPlan(p);
			return healthProviderRepo.save(hpRes);
		}
		
		return null;

	}
	

	
	
	public void test() {

		testCreateHp();
		testUpadateHealthProviderWithSamePlans();
		testUpadateHealthProviderWithDiffAndSamePlansAndDiffName();
		testUpadateHealthProviderWithDiffPlans();
		testUpadateHealthProviderWithNoPlans();
		testCreateHp();
		testCreateHp();
		
	}
	
	public void testCreateHp() {
		HealthProvider hp = new HealthProvider();
		
		hp.setName("healthP");
		Set<Plan> planSet = new HashSet<>();
		Plan p1 = new Plan();
		p1.setName("p1");
		Plan p2 = new Plan();
		p2.setName("p2");
		planSet.addAll(Arrays.asList(p1, p2));
		hp.setPlans(planSet);
		this.createHealthProvider(hp);
	}
	
	public void testUpadateHealthProviderWithSamePlans() {
		HealthProvider hp = new HealthProvider();
		hp.setId(1);
		hp.setName("healthP");
		Set<Plan> planSet = new HashSet<>();
		Plan p1 = new Plan();
		p1.setName("p1");
		Plan p2 = new Plan();
		p2.setName("p2");
		planSet.addAll(Arrays.asList(p1, p2));
		hp.setPlans(planSet);
		this.updateHealthProvider(hp.getId(), hp);
		addPlanToHealthProvider(hp, p1);
	}
	
	public void testUpadateHealthProviderWithDiffAndSamePlansAndDiffName() {
		HealthProvider hp = new HealthProvider();
		hp.setId(1);
		hp.setName("healthP1");
		Set<Plan> planSet = new HashSet<>();
		Plan p3 = new Plan();
		p3.setName("p3");
		Plan p2 = new Plan();
		p2.setName("p2");
		planSet.addAll(Arrays.asList(p3, p2));
		hp.setPlans(planSet);
		this.updateHealthProvider(hp.getId(), hp);
	}
	
	public void testUpadateHealthProviderWithDiffPlans() {
		HealthProvider hp = new HealthProvider();
		hp.setId(1);
		hp.setName("healthP1");
		Set<Plan> planSet = new HashSet<>();
		Plan p4 = new Plan();
		p4.setName("p4");
		Plan p5 = new Plan();
		p5.setName("p5");
		planSet.addAll(Arrays.asList(p4, p5));
		hp.setPlans(planSet);
		this.updateHealthProvider(hp.getId(), hp);
		
	}
	
	public void testUpadateHealthProviderWithNoPlans() {
		HealthProvider hp = new HealthProvider();
		hp.setId(1);
		hp.setName("healthP2");

		this.updateHealthProvider(hp.getId(), hp);
	}
		
		

}
	
