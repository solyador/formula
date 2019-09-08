package org.formula.pilot;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotRepository extends JpaRepository<Pilot, Long> {
    public Pilot findByFirsNameAndLasstName(String firstName, String lastName);
    
}
