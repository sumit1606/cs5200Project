

package edu.neu.cs5200.orm.jpa.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Doctor;

public interface DoctorRepository extends CrudRepository<Doctor, Integer>{

	@Query("SELECT d FROM Doctor d WHERE d.fName=:first AND d.lName=:last")
	public Doctor findDoctorByName(
			@Param("first") String firstName, 
			@Param("last") String lastName);

	@Query("select d from Doctor d join d.plansSupported p where d.id=:docid and p.id=:pid")
	public List<Doctor> checkIfPlanExistsInDoctor(@Param("docid") int docid, @Param("pid") int pid);
}
