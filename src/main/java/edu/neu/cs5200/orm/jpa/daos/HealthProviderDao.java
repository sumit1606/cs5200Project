

package edu.neu.cs5200.orm.jpa.daos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.repositories.HealthProviderRepository;

@Component
public class HealthProviderDao {
	@Autowired
	HealthProviderRepository healthProvRepo;
	
	public HealthProvider findProviderByName(String name) {
		Optional<HealthProvider> hp = healthProvRepo.findHealthProviderByName(name);
		if (hp.isPresent()) {
			return hp.get();
		}
		
		return null;
	}
	
	
		public void test() {
			HealthProvider hp = new HealthProvider();
			if (this.findProviderByName("healthP") == null) {
				hp.setName("healthP");
				healthProvRepo.save(hp);
			}

		}
		
		public void deletePlan() {
			HealthProvider hp = this.findProviderByName("healthP");
			healthProvRepo.deleteById(hp.getId());
		}
}
	
