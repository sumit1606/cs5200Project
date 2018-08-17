package edu.neu.cs5200.orm.jpa.daos;


import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.neu.cs5200.orm.jpa.entities.HealthPersonnel;
import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.repositories.HealthPersonnelRepository;
import edu.neu.cs5200.orm.jpa.repositories.HealthProviderRepository;

@Component
public class HealthPersonnelDao {
	@Autowired
	HealthPersonnelRepository hpRepo;
	
	@Autowired
	HealthProviderDao hpd;
	
	@Autowired
	HealthProviderRepository healthProviderRepo;
	
	@Autowired
	PersonDao personDao;
	
	public HealthPersonnel findPersonnelByName(HealthPersonnel hp) {
		return hpRepo.findHealthPersonnelByName(hp.getfName(), hp.getlName());
	
	}
	
	public HealthPersonnel findHealthPersonnelById(int hpId) {
		 Optional<HealthPersonnel> hpRes = hpRepo.findById(hpId);
		 if (hpRes.isPresent())
			 return hpRes.get();
		 return null;
	}
	
	public List<HealthPersonnel> findAllHealthPersonnel() {

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
			 hp.setPassword(p.getPassword());
			 return hpRepo.save(hp);
		}
		return hp;
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
		
		HealthPersonnel d = this.findHealthPersonnelById(id);
		if (d != null)
		{
			personDao.removeFollowingMappingIfPersonDeleted(d.getId());
			deleteHealthPersonnelFromProvider(d);
			hpRepo.deleteById(id);
		}

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

	
	public void deleteAll() {
		List<HealthPersonnel> pts = this.findAllHealthPersonnel();
		for(HealthPersonnel pt: pts) {
			personDao.removeFollowingMappingIfPersonDeleted(pt.getId());
			
		}
		hpRepo.deleteAll();
	}



}
