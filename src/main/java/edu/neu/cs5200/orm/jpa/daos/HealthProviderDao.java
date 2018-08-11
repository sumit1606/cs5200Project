

package edu.neu.cs5200.orm.jpa.daos;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.HealthPersonnel;
import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.entities.Plan;
import edu.neu.cs5200.orm.jpa.repositories.HealthPersonnelRepository;
import edu.neu.cs5200.orm.jpa.repositories.HealthProviderRepository;
import edu.neu.cs5200.orm.jpa.repositories.PlanRepository;

@Component
public class HealthProviderDao {
	@Autowired
	HealthProviderRepository healthProviderRepo;
	
	@Autowired 
	PlanRepository planRepo;
	
	@Autowired
	HealthPersonnelRepository hpRepo;
	
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
		
		if(hp.getHpUsers() != null && hp.getHpUsers().size() > 0) {
			for(HealthPersonnel h: hp.getHpUsers()) {
				if (hpRepo.findHealthPersonnelByName(h.getfName(), h.getlName()) != null)
					hpRes.addHpUsers(h);
			}
		}
		
		return healthProviderRepo.save(hpRes);
	}
	

	
	public HealthProvider updateHealthProvider(int hpId, HealthProvider hp) {
		HealthProvider hpRes = this.findHealthProviderById(hpId);
		if (hpRes != null ) {
			hpRes.setPlan(hp);
			healthProviderRepo.save(hpRes);
			
//			Discuss if this should be provided or not. 
			
//			if(hp.getHpUsers() != null && hp.getHpUsers().size() > 0) {
//				Set<HealthPersonnel> hpUsersValid = new HashSet<>();
//				for (HealthPersonnel p : hp.getHpUsers()) {
//					if (hpRepo.findHealthPersonnelByName(p.getfName(), p.getlName()) != null) {
//						hpUsersValid.add(p);
//					}
//				}
//				
//				if (hpUsersValid.size() > 0) {
//					hp.setHpUsers(hpUsersValid);
//					hpRes.setHealthPersonnel(hp);
//					healthProviderRepo.save(hpRes);
//				}
//			}
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
	
	
//	public HealthProvider addHealthPersonnelToHealthProvider(HealthProvider hp, HealthPersonnel p) {
//		HealthProvider hpRes = this.findHealthProviderById(hp.getId());
//		if (hpRes != null) {
//			if (hpRepo.findHealthPersonnelByName(p.getfName(), p.getlName()) != null) {
//				hpRes.addHpUsers(p);
//				return healthProviderRepo.save(hpRes);
//			}
//		
//		}
//		
//		return null;
//
//	}
	
	public void deleteHealthProviderByName(String hpName) {
		HealthProvider hpRes = this.findProviderByName(hpName);
		if (hpRes != null) {
			for (HealthPersonnel h: hpRes.getHpUsers()) {
				h.setHprovider(null);
				hpRepo.save(h);
			}
			
			healthProviderRepo.deleteById(hpRes.getId());
		}
			
	}
	

	

		

}
	
