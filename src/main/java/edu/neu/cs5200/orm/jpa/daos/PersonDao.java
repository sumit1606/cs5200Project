package edu.neu.cs5200.orm.jpa.daos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.repositories.DoctorRepository;
import edu.neu.cs5200.orm.jpa.repositories.PersonRepository;

@Component
public class PersonDao {
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	DoctorDao docDao;
	
	@Autowired
	DoctorRepository drepo;
	

	public Person findPersonbyId(int id) {
		if(personRepository.findById(id) != null)
			return personRepository.findById(id).get();
		else
			return null;
	}
	
	public void followDoctor(int docid, int pid) {
		Doctor doc = (Doctor) this.findPersonbyId(docid);
		if (!doc.getDtype().equals("doctor"))
			return;
		Person per = this.findPersonbyId(pid);
		if (doc == null || per == null || docid == pid) 
			return;
		
		List<Person> res = personRepository.checkIfPersonFollowThisDoctor(docid, pid);
		if(res == null || res.size() == 0) {
			if (doc.getFollowedBy() == null) {
				doc.setFollowedBy(new ArrayList<Person>());
			}

			doc.getFollowedBy().add(per);
			personRepository.save(doc);
			
		}
		return;
		
		
	}
	
	public void unfollowDoctor(int docid, int pid) {
		Doctor doc = (Doctor) this.findPersonbyId(docid);
		if (!doc.getDtype().equals("doctor"))
			return;
		Person per = this.findPersonbyId(pid);
		if (doc == null || per == null || docid == pid) 
			return;
		
		List<Person> res = personRepository.checkIfPersonFollowThisDoctor(docid, pid);
		if(res == null || res.size() == 0) {
				return;
		}
		
		Iterator<Person> followers = doc.getFollowedBy().iterator();
		while(followers.hasNext()) {
			Person p = followers.next();
			if (p.getId() == per.getId()) {
				followers.remove();
				break;
			}
		}
		
		
		personRepository.save(doc);
		return;
		
		
	}
	
	public void removeFollowingMappingIfPersonDeleted(int pid) {
		Person p = this.findPersonbyId(pid);
		if (p != null && p.getFollows() != null) {
			List<Person> followingPpl = p.getFollows();
			for(Person ppl: followingPpl) {
				Iterator<Person> perIt = ppl.getFollowedBy().iterator();
				while(perIt.hasNext()) {
					Person temp = perIt.next();
					if(temp.getId() == p.getId()) {
						perIt.remove();
						personRepository.save(ppl);
						break;
					}
				}
			}
			
		}
	}
	
	
	

	
}
