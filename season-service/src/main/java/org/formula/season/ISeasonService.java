package org.formula.season;

import java.util.List;

public interface ISeasonService {
    List<Season> findAll();
    Season save(Season season);
    Season findById(Long id);
    void deleteById(Long id);
    void updateSeason(Season newSeason, Long id);
}
