package edu.neu.cs5200.orm.jpa.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class HealthProvider {
	@Id
	@GeneratedValue
	(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@OneToMany(mappedBy="hp", orphanRemoval=true, cascade=CascadeType.ALL)
	private Set<Plan> plans;
	
	@OneToMany (mappedBy="hprovider")
	@JsonIgnore
	private Set<HealthPersonnel> hpUsers;
	
	public HealthProvider() {
		
	}
	
	public HealthProvider(String providerName) {
		// TODO Auto-generated constructor stub
		this.name = providerName;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the plans
	 */
	public Set<Plan> getPlans() {
		return plans;
	}
	/**
	 * @param plans the plans to set
	 */
	public void setPlans(Set<Plan> plans) {
		this.plans = plans;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the hpUsers
	 */
	public Set<HealthPersonnel> getHpUsers() {
		return hpUsers;
	}
	/**
	 * @param hpUsers the hpUsers to set
	 */
	public void setHpUsers(Set<HealthPersonnel> hpUsers) {
		this.hpUsers = hpUsers;
	}
	public void addPlan(Plan p) {
		if (this.plans == null) {
			this.plans = new HashSet<Plan>();
		}
		
		List<String> planNamesExisting = createListOfPlanNames(this);
		if (planNamesExisting==null || !planNamesExisting.contains(p.getName())) {
			this.plans.add(p);
			p.setHp(this);
		}

	}
	
	public void setPlan(HealthProvider hp) {
		this.name = hp.getName() != null? hp.getName() : this.name;
		if (hp.getPlans() != null && hp.getPlans().size() > 0) {
			List<String>planNamesInNewObject = createListOfPlanNames(hp);
			Set <Plan> plansToBeRemoved = new HashSet<>();
			
			for(Plan p : this.getPlans()) {
				if (!planNamesInNewObject.contains(p.getName())) {
					plansToBeRemoved.add(p);
				}
			}
			
			for (Plan p: plansToBeRemoved) {
				this.getPlans().remove(p);
			}
			
		
			for(Plan p : hp.getPlans()) {
				this.addPlan(p);
			}
		}else {
			if (this.getPlans() != null)
				this.getPlans().removeAll(this.getPlans());
		
		}
		
	
	}
	
//	public void setHealthPersonnel(HealthProvider hp) {
//		this.name = hp.getName() != null? hp.getName() : this.name;
//		if (hp.getHpUsers() != null && hp.getHpUsers().size() > 0) {
//			List<String>hpUsersInNewObject = createListOfhpUsersNames(hp);
//			Set <HealthPersonnel> hpUsersToBeRemoved = new HashSet<>();
//			
//			for(HealthPersonnel p : this.getHpUsers()) {
//				if (!hpUsersInNewObject.contains(p.getfName()+p.getlName())) {
//					hpUsersToBeRemoved.add(p);
//				}
//			}
//			
//			for (HealthPersonnel p: hpUsersToBeRemoved) {
//				this.getHpUsers().remove(p);
//			}
//			
//		
//			for(HealthPersonnel p : hp.getHpUsers()) {
//				
//				this.addHpUsers(p);
//			}
//		}else {
//			if (this.getHpUsers() != null)
//				this.getHpUsers().removeAll(this.getHpUsers());
//		
//		}
//	}
//	
	public void addHpUsers(HealthPersonnel p) {
		if (this.hpUsers == null) {
			this.hpUsers = new HashSet<HealthPersonnel>();
		}
		
		List<String> hpUsersNamesExisting = createListOfhpUsersNames(this);
		if (hpUsersNamesExisting == null || !hpUsersNamesExisting.contains(p.getfName()+p.getlName())) {
		
			this.hpUsers.add(p);
			p.setHprovider(this);
		}
		
	}
	
	public List<String> createListOfhpUsersNames(HealthProvider hp) {
		if (hp.getHpUsers() == null || hp.getHpUsers().size() == 0) {
			return null;
		}
		return new ArrayList<>(hp.getHpUsers().stream().map((p) -> p.getfName()+p.getlName()).collect(Collectors.toList()));
	}
	public List<String> createListOfPlanNames(HealthProvider hp) {
		if (hp.getPlans() == null || hp.getPlans().size() == 0) {
			return null;
		}
		return new ArrayList<>(hp.getPlans().stream().map((p) -> p.getName()).collect(Collectors.toList()));
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HealthProvider [id=" + id + ", name=" + name + ", plans=" + plans + "]";
	}
	
	
}
