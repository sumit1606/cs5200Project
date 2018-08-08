

package edu.neu.cs5200.orm.jpa.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Doctor;
import edu.neu.cs5200.orm.jpa.entities.Person;

public interface DoctorRepository extends CrudRepository<Doctor, Integer>{

}
