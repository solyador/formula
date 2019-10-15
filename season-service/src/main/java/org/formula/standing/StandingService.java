package org.formula.standing;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandingService implements IStandingService {
    private final StandingRepository standingRepository;

    public StandingService(StandingRepository standingRepository) {
        this.standingRepository = standingRepository;
    }

    @Override
    public List<Standing> findAll() {
        return standingRepository.findAll();
    }

    @Override
    public Standing save(Standing standing) {
        return standingRepository.save(standing);
    }

    @Override
    public Standing findById(Long id) {
        return standingRepository.findById(id).orElseThrow(() -> new StandingNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {
        standingRepository.deleteById(id);
    }

    @Override
    public void updateStanding(Standing newStanding, Long id) {
        standingRepository.findById(id).map(item -> {
            item.setDriver(newStanding.getDriver());
            item.setSeason(newStanding.getSeason());
            item.setPosition(newStanding.getPosition());
            item.setPoints(newStanding.getPoints());
            standingRepository.save(item);
            return null;
        });
    }

    @Override
    public boolean exists(Standing standing) {
        Standing searchedStanding = standingRepository.findById(standing.getId()).get();
        return searchedStanding != null;
    }

    @Override
    public Standing findBySeasonAndDriver(Integer season, Integer driver) {
        return standingRepository.findBySeasonAndDriver(season, driver);
    }
}
