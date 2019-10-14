package org.formula.race;

import java.util.List;

public interface IRaceService {
    public List<Race> findAll();
    public Race save(Race race);
    public Race findById(Long id);
    public void deleteById(Long id);
    public void updateRace(Race newRace, Long id);
    public boolean exists(Race race);
}
