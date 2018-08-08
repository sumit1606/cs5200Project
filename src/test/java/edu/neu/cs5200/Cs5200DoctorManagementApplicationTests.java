package edu.neu.cs5200;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.neu.cs5200.orm.jpa.daos.HealthProviderDao;
import edu.neu.cs5200.orm.jpa.daos.PlanDao;
import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.entities.Plan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Cs5200DoctorManagementApplicationTests {

	@Autowired 
	HealthProviderDao hpd;
	
	@Autowired
	PlanDao pld;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
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
		hpd.createHealthProvider(hp);
	}
	
	@Test
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
		hpd.updateHealthProvider(hp.getId(), hp);
		hpd.addPlanToHealthProvider(hp, p1);
	}
	
	@Test
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
		hpd.updateHealthProvider(hp.getId(), hp);
	}
	
	@Test
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
		hpd.updateHealthProvider(hp.getId(), hp);
		
	}
	
	@Test
	public void testUpadateHealthProviderWithNoPlans() {
		HealthProvider hp = new HealthProvider();
		hp.setId(1);
		hp.setName("healthP2");

		hpd.updateHealthProvider(hp.getId(), hp);
	}
	
	@Test
	public void testPlanCreate() {
		Plan p = new Plan();
		HealthProvider hp = hpd.findProviderByName("healthP2");
		if (hp != null) {
			p.setName("plan1");
			pld.createPlan(hp, p);
		}
	}
	
	@Test
	public void testUpdate() {
		Plan p = pld.findPlanByName("plan1");
		if (p == null)
			return;
		p.setName("planChanged");
		HealthProvider hp = hpd.findProviderByName("healthP");
		if (hp != null ) {
			p.setHp(hp);
		}
		pld.updatePlan(p.getId(), p);
	}

	@Test
	public void testDelete() {
		pld.deletePlanByName("p1");
	}

}
