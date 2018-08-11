package edu.neu.cs5200;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.neu.cs5200.orm.jpa.daos.BlogDao;
import edu.neu.cs5200.orm.jpa.daos.DoctorDao;
import edu.neu.cs5200.orm.jpa.daos.HealthPersonnelDao;
import edu.neu.cs5200.orm.jpa.daos.HealthProviderDao;
import edu.neu.cs5200.orm.jpa.daos.PatientDao;
import edu.neu.cs5200.orm.jpa.daos.PlanDao;
import edu.neu.cs5200.orm.jpa.entities.Blog;
import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.HealthPersonnel;
import edu.neu.cs5200.orm.jpa.entities.HealthProvider;
import edu.neu.cs5200.orm.jpa.entities.Patient;
import edu.neu.cs5200.orm.jpa.entities.Plan;
import edu.neu.cs5200.utitlities.Utility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Cs5200DoctorManagementApplicationTests {

	@Autowired 
	HealthProviderDao hpd;
	
	@Autowired
	PlanDao pld;
	
	@Autowired 
	PatientDao patd;
	
	@Autowired
	PatientDao patientDao;
	
	@Autowired
	HealthPersonnelDao hperd;
	
	@Autowired
	BlogDao bdao;
	
	@Autowired
	DoctorDao ddao;
	
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

//	@Test
//	public void testDelete() {
//		pld.deletePlanByName("p1");
//	}
	@Test
	public void testCreatePatientWithPlan() {
		Patient p = new Patient();
		p.setAddress("address");
		p.setDob(new Utility().modifySQLDate(31,2,1991));
		p.setDtype("Patient");
		p.setEmail("a@a.com");
		p.setfName("aashish");
		p.setlName("singh");
		p.setDtype("patient");
		Plan pl = pld.findPlanById(8);
		p.setHealthInsurancePlan(pl);
		patientDao.createPatient(p);

	}
	
	
	
	@Test
	
	public void testHealthPersonnel() {

			HealthProvider hp = new HealthProvider();
			hp.setName("healthP");
			Set<Plan> planSet = new HashSet<>();
			Plan p1 = new Plan();
			p1.setName("p1");
			Plan p2 = new Plan();
			p2.setName("p2");
			planSet.addAll(Arrays.asList(p1, p2));
			hp.setPlans(planSet);
//			hpd.createHealthProvider(hp);
			
			HealthPersonnel p = new HealthPersonnel();
			p.setAddress("address");
			p.setDob(new Utility().modifySQLDate(31,2,1991));

			p.setEmail("a@a.com");
			p.setfName("aashish");
			p.setlName("singh");
			p.setDtype("HealthPersonnel");
			p.setHprovider(hp);
			hperd.createHealthPersonnel(p);
			
			p.setHprovider(hp);
		
			hperd.updateHealthPersonnel(1, p);
			
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
			hperd.updateHealthPersonnel(1, p);
			hperd.createHealthPersonnel(p);
			
			hp.setName("healthP");
			p.setHprovider(hp);
			hperd.updateHealthPersonnel(1, p);
			
//			this.deleteHealthPersonnel(1);
			p = new HealthPersonnel();
			p.setAddress("address");
			p.setDob(new Utility().modifySQLDate(31,2,1991));

			p.setEmail("a@a.com");
			p.setfName("sumit");
			p.setlName("Bhanwala");
			p.setDtype("HealthPersonnel");
			
			hp.setName("healthP1");
			
			p.setHprovider(hp);
			hperd.createHealthPersonnel(p);
			hperd.updateHealthPersonnel(2, p);
			
//			hpd.deleteHealthProviderByName("healthP");
			hperd.deleteHealthPersonnel(1);
			
			
			
			Patient pat = new Patient();
			pat.setAddress("address");
			pat.setDob(new Utility().modifySQLDate(31,2,1991));
			pat.setEmail("a@a.com");
			pat.setfName("asd");
			pat.setlName("singh");
			pat.setDtype("patient");
			pat.setHealthInsurancePlan(p1);
			patientDao.createPatient(pat);
//			pld.deletePlanByName(p1.getName());
			pat.setHealthInsurancePlan(p2);
			patientDao.updatePatient(pat);
			patientDao.deletePatientById(patientDao.findPatientByName(pat).getId());
			
			
	}
	
	@Test
	public void testBlog() {
		Doctor d = new Doctor();
		d.setfName("avs");
		d.setlName("sb");
		d.setDob(new Utility().modifySQLDate(31,2,1991));
		d.setAddress("gsdhvbsv");
		d.setDtype("doctor");
		d.setEmail("a@abc.com");
		d.setTitle("bye");
		d.setBio("hello");
		d = ddao.createDoctor(d);
		
		Blog b = new Blog();
		b.setContent("hgsvhsdvcbdvcbfdjvcfjvbjfbv");
		b = bdao.createBlog(d.getId(), b);
//		docDao.deleteDoctorById(d.getId());
//		bdao.deleteBlog(b.getId());
		b.setContent("This content is changed");
		bdao.updateBlog(b.getId(), b);
		System.out.println(bdao.findAllBlogs());
		
		
		
	}
	
	@Test
	public void testDoctor() {
		Doctor d = new Doctor();
		d.setfName("avs");
		d.setlName("sb");
		d.setDob(new Utility().modifySQLDate(31,2,1991));
		d.setAddress("gsdhvbsv");
		d.setDtype("doctor");
		d.setEmail("a@abc.com");
		d.setTitle("bye");
		d.setBio("hello");
		d = ddao.createDoctor(d);
		
		Blog b = new Blog();
		b.setContent("hgsvhsdvcbdvcbfdjvcfjvbjfbv");
		ddao.addBlogToDoctor(d.getId(), b);
		System.out.println(ddao.findAllBlogsForThisDoctor(d.getId()));
		b.setContent("zibberish");
		ddao.addBlogToDoctor(d.getId(), b);
		System.out.println(ddao.findAllBlogsForThisDoctor(d.getId()));
	
	}
	

}
