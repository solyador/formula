package org.formula.season;

import java.util.List;

public interface ISeasonService {
    public List<Season> findAll();
    public Season save(Season season);
    public Season findById(Long id);
    public void deleteById(Long id);
    public void updateSeason(Season newSeason, Long id);
    public boolean exists(Season season);
}
