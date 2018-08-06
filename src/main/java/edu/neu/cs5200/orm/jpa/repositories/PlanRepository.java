package edu.neu.cs5200.orm.jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Plan;

public interface PlanRepository extends CrudRepository<Plan, Integer> {
	@Query("select plan from Plan plan where plan.name=:name")
	public Iterable<Plan> findPlanByName(@Param("name") String name);
}
