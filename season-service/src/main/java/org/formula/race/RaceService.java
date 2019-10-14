package org.formula.race;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceService implements IRaceService {
    private final RaceRepository raceRepository;

    public RaceService(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @Override
    public List<Race> findAll() {
        return raceRepository.findAll();
    }

    @Override
    public Race save(Race race) {
        return raceRepository.save(race);
    }

    @Override
    public Race findById(Long id) {
        return raceRepository.findById(id).orElseThrow(() -> new RaceNotFoundException(id));
    }

    @Override
    public void deleteById(Long id) {
        raceRepository.deleteById(id);
    }

    @Override
    public void updateRace(Race newRace, Long id) {
        raceRepository.findById(id).map(item -> {
            item.setDriver(newRace.getDriver());
            item.setPosition(newRace.getPosition());
            item.setSeason(newRace.getSeason());
            item.setWeek(newRace.getWeek());
            item.setScore(newRace.getScore());
            raceRepository.save(item);
            return null;
        });
    }

    @Override
    public boolean exists(Race race) {
        Race searchedRace = raceRepository.findById(race.getId()).get();
        return searchedRace != null;
    }
}
