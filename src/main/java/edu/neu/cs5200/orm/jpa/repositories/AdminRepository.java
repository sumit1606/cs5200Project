package edu.neu.cs5200.orm.jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Admin;
import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.Patient;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
	
	@Query("SELECT d FROM Admin d WHERE d.dtype=:role")
	public Admin findByIfAdminExists(@Param("role") String role);
	
	@Query("select admin from Admin admin where admin.fName=:fn AND admin.lName=:ln")
	public Admin findAdminByName(@Param("fn") String firstName,
											@Param("ln") String lastName);
}


