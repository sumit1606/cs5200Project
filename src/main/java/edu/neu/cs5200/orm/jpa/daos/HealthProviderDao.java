

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
	
	public HealthProvider findHealthProviderById(int hpId) {
		 Optional<HealthProvider> hpRes = healthProviderRepo.findById(hpId);
		 if (hpRes.isPresent())
			 return hpRes.get();
		 return null;
	}
	
	public List<HealthProvider> findAllHealthProvider() {

		return (List<HealthProvider>)healthProviderRepo.findAll();
		 
	
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
	
	public void deleteHealthProviderByName(String hpName) {
		HealthProvider hpRes = this.findProviderByName(hpName);
		if (hpRes != null)
			healthProviderRepo.deleteById(hpRes.getId());
	}
	

		

}
	
