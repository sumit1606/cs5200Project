package edu.neu.cs5200.orm.jpa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.HealthProvider;

public interface HealthProviderRepository extends CrudRepository<HealthProvider, Integer> {
	@Query("select provider from HealthProvider provider where provider.name=:name")
	public Optional<HealthProvider> findHealthProviderByName(@Param("name") String name);
}
