package org.formula.standing;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StandingRepository extends JpaRepository<Standing, Long> {
    List<Standing> findAllBySeason(Integer season);
    Standing findBySeasonAndDriver(Integer season, Integer driver);
}
