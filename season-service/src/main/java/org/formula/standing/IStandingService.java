package org.formula.standing;

import java.util.List;

public interface IStandingService {
    List<Standing> findAll();
    Standing save(Standing standing);
    Standing findById(Long id);
    void deleteById(Long id);
    void updateStanding(Standing newStanding, Long id);

    Standing findBySeasonAndDriver(Integer season, Integer driver);
}
