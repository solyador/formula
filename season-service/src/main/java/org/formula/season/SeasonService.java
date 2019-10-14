package org.formula.season;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService implements ISeasonService {
    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Override
    public List<Season> findAll() {
        return seasonRepository.findAll();
    }

    @Override
    public Season save(Season season) {
        return seasonRepository.save(season);
    }

    @Override
    public Season findById(Long id) {
        return seasonRepository.findById(id).orElseThrow(() -> new SeasonNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {
        seasonRepository.deleteById(id);
    }

    @Override
    public void updateSeason(Season newSeason, Long id) {
        seasonRepository.findById(id).map(item -> {
            item.setRace(newSeason.getRace());
            seasonRepository.save(item);
            return null;
        });
    }

    @Override
    public boolean exists(Season season) {
        Season searchedSeason = seasonRepository.findById(season.getId()).get();
        return searchedSeason != null;
    }
}
