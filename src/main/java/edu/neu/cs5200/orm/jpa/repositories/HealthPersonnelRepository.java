package edu.neu.cs5200.orm.jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.HealthPersonnel;

public interface HealthPersonnelRepository extends CrudRepository<HealthPersonnel, Integer> {
	@Query("SELECT d FROM HealthPersonnel d WHERE d.fName=:first AND d.lName=:last")
	public HealthPersonnel findHealthPersonnelByName(
			@Param("first") String firstName, 
			@Param("last") String lastName);
}
