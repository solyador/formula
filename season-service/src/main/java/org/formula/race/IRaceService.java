package org.formula.race;

import java.util.List;

public interface IRaceService {
    List<Race> findAll();
    Race save(Race race);
    Race findById(Long id);
    void deleteById(Long id);
    void updateRace(Race newRace, Long id);
    boolean exists(Race race);

    List<Race> simulateRace(Integer season, Integer week);

    void updateStandings(Integer season, List<Race> race);
}
