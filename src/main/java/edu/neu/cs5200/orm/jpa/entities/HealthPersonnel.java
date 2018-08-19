package edu.neu.cs5200.orm.jpa.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class HealthPersonnel extends Person{

	@ManyToOne (cascade = CascadeType.ALL)
	HealthProvider hprovider;

	/**
	 * @return the hp
	 */
	public HealthProvider getHprovider() {
		return hprovider;
	}

	/**
	 * @param hp the hp to set
	 */
	public void setHprovider(HealthProvider hp) {
		this.hprovider = hp;
	}

	public void addHealthProviderToHealthPersonnel(HealthProvider hprovider) {
		this.setHprovider(hprovider);
		List<String> hpersons = this.createListOfhpUsersNames(hprovider);
		String thisName = this.getfName()+this.getlName();
		
		
		if (hprovider.getHpUsers() == null) {
			Set<HealthPersonnel> hpSetInProvider = new HashSet<>();
			hprovider.setHpUsers(hpSetInProvider);
		}
		
		if (hpersons != null && hpersons.contains(thisName)) {
			return;
		} else {
			hprovider.getHpUsers().add(this);
		}
		
	}
	
	
	public List<String> createListOfhpUsersNames(HealthProvider hp) {
		if (hp.getHpUsers() == null || hp.getHpUsers().size() == 0) {
			return null;
		}
		return new ArrayList<>(hp.getHpUsers().stream().map((p) -> p.getfName()+p.getlName()).collect(Collectors.toList()));
	}
	
}
