package edu.neu.cs5200.orm.jpa.daos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.HealthPersonnel;
import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.entities.Patient;
import edu.neu.cs5200.orm.jpa.entities.Plan;
import edu.neu.cs5200.orm.jpa.repositories.HealthPersonnelRepository;
import edu.neu.cs5200.orm.jpa.repositories.HealthProviderRepository;
import edu.neu.cs5200.utitlities.Utility;


@Component
public class HealthPersonnelDao {
	@Autowired
	HealthPersonnelRepository hpRepo;
	
	@Autowired
	HealthProviderDao hpd;
	
	@Autowired
	HealthProviderRepository healthProviderRepo;
	
	public HealthPersonnel findPersonnelByName(HealthPersonnel hp) {
		return hpRepo.findHealthPersonnelByName(hp.getfName(), hp.getlName());
	
	}
	
	public HealthPersonnel findHealthPersonnelById(int hpId) {
		 Optional<HealthPersonnel> hpRes = hpRepo.findById(hpId);
		 if (hpRes.isPresent())
			 return hpRes.get();
		 return null;
	}
	
	public List<HealthPersonnel> findAllHealthProvider() {

		return (List<HealthPersonnel>)hpRepo.findAll();
	}
		
	
	public HealthPersonnel createHealthPersonnel(HealthPersonnel p) {
		HealthPersonnel hp = this.findPersonnelByName(p);
		
		if ( hp == null) {
			 hp = new HealthPersonnel();
			 hp.setfName(p.getfName());
			 hp.setlName(p.getlName());
			 hp.setAddress(p.getAddress());
			 hp.setEmail(p.getEmail());
		
//			 Discuss how to handle this
//			 hp.setDob(p.getDob());
			 
			 hp.setDtype("healthPersonnel");
			 return hpRepo.save(hp);
		}
		return null;
	}
	
	
	
	public HealthPersonnel updateHealthPersonnel(int id, HealthPersonnel p) {
		HealthPersonnel hp = this.findHealthPersonnelById(id);
		
		if ( hp != null) {
				hp.set(p);
				hpRepo.save(hp);
				updateHealthProvider(hp.getId(), p.getHprovider());
				return hp;
		}
		
		return null;
	}
	
	
	public HealthPersonnel updateHealthProvider(int id, HealthProvider p) { 
		HealthPersonnel hp = this.findHealthPersonnelById(id);
		HealthProvider hprovider = hpd.findProviderByName(p.getName());
		
		if (hp == null)
			return null;
		
		
		if (hp.getHprovider() != null) {
			if (hprovider != null && hp.getHprovider().getName() == hprovider.getName()) {
				return hp;
			} else {
				this.deleteHealthPersonnelFromProvider(hp);
			}
		}
		
		
		if (hprovider != null) {
			
			hp.addHealthProviderToHealthPersonnel(hprovider);
			return hpRepo.save(hp);
		} else {
			hprovider = hpd.createHealthProvider(p);
			hprovider = hpd.findHealthProviderById(hprovider.getId());
			hp.addHealthProviderToHealthPersonnel(hprovider);
			return hpRepo.save(hp);
			
		}
		
	
	}
	
	public void deleteHealthPersonnel(int id) {
		deleteHealthPersonnelFromProvider(this.findHealthPersonnelById(id));
		hpRepo.deleteById(id);
	}
	
	
	
	// Note: argument "HealthPersonnel hp" should be fetched from DB.
	public void deleteHealthPersonnelFromProvider(HealthPersonnel hp) {
		HealthProvider oldHealthProvider = hpd.findHealthProviderById(hp.getHprovider().getId());
		Iterator <HealthPersonnel> it = oldHealthProvider.getHpUsers().iterator();
		while(it.hasNext()) {
			HealthPersonnel temp = it.next();
			if (temp.getfName() == hp.getfName() && temp.getlName() == hp.getlName()) {
				it.remove();
				break;
			}
		}
		healthProviderRepo.save(oldHealthProvider);
		hp.setHprovider(null);
		hpRepo.save(hp);
	}
	


	
	public void test() {
		HealthProvider hp = new HealthProvider();
		hp.setName("healthP");
		Set<Plan> planSet = new HashSet<>();
		Plan p1 = new Plan();
		p1.setName("p1");
		Plan p2 = new Plan();
		p2.setName("p2");
		planSet.addAll(Arrays.asList(p1, p2));
		hp.setPlans(planSet);
//		hpd.createHealthProvider(hp);
		
		HealthPersonnel p = new HealthPersonnel();
		p.setAddress("address");
		p.setDob(new Utility().modifySQLDate(31,2,1991));

		p.setEmail("a@a.com");
		p.setfName("aashish");
		p.setlName("singh");
		p.setDtype("HealthPersonnel");
		p.setHprovider(hp);
		this.createHealthPersonnel(p);
		
		p.setHprovider(hp);
	
		this.updateHealthPersonnel(1, p);
		
		hp = new HealthProvider();
		hp.setName("healthP1");
		planSet = new HashSet<>();
		p1 = new Plan();
		p1.setName("p4");
		p2 = new Plan();
		p2.setName("p3");
		planSet.addAll(Arrays.asList(p1, p2));
		hp.setPlans(planSet);
		p.setHprovider(hp);
		this.updateHealthPersonnel(1, p);
		this.createHealthPersonnel(p);
		
		hp.setName("healthP");
		p.setHprovider(hp);
		this.updateHealthPersonnel(1, p);
		
//		this.deleteHealthPersonnel(1);
		p = new HealthPersonnel();
		p.setAddress("address");
		p.setDob(new Utility().modifySQLDate(31,2,1991));

		p.setEmail("a@a.com");
		p.setfName("sumit");
		p.setlName("Bhanwala");
		p.setDtype("HealthPersonnel");
		
		hp.setName("healthP1");
		
		p.setHprovider(hp);
		this.createHealthPersonnel(p);
		this.updateHealthPersonnel(2, p);
		
//		hpd.deleteHealthProviderByName("healthP");
		this.deleteHealthPersonnel(1);
		
		
		
	}
	
}
