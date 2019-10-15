package org.formula.race;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaceRepository extends JpaRepository<Race, Long> {
    List<Race> findAllBySeasonAndWeek(Integer season, Integer week);
}
