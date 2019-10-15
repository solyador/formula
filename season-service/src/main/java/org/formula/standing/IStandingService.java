package org.formula.standing;

import java.util.List;

public interface IStandingService {
    public List<Standing> findAll();
    public Standing save(Standing standing);
    public Standing findById(Long id);
    public void deleteById(Long id);
    public void updateStanding(Standing newStanding, Long id);
    public boolean exists(Standing standing);

    Standing findBySeasonAndDriver(Integer season, Integer driver);
}
