package org.formula.driver;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
     Driver findByFirstNameAndLastName(String firstName, String lastName);
    
}
