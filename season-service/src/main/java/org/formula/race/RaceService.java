package org.formula.race;

import org.formula.standing.IStandingService;
import org.formula.standing.Standing;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Service
public class RaceService implements IRaceService {

    private final RaceRepository raceRepository;
    private final Random randomGenerator = new Random();
    private final IStandingService standingService;

    public RaceService(RaceRepository raceRepository, IStandingService standingService) {
        this.raceRepository = raceRepository;
        this.standingService = standingService;
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
            item.setSeason(newRace.getSeason());
            item.setWeek(newRace.getWeek());
            item.setDriver(newRace.getDriver());
            item.setScore(newRace.getScore());
            raceRepository.save(item);
            return null;
        });
    }

    @Override
    public List<Race> simulateRace(Integer season, Integer week) {
        List<Race> race = raceRepository.findAllBySeasonAndWeek(season, week);
        race.forEach(item -> {
            item.setScore(getScore());
            raceRepository.save(item);
        });
        race.sort(Comparator.comparing(Race::getScore));
        return race;
    }

    @Override
    public void updateStandings(Integer season, List<Race> race) {
            List<Integer> points = Arrays.asList(25, 18, 15, 12, 10, 8, 6, 4, 2, 1);
            List<Race> scoringDrivers = race.subList(0, 9);
            for (int i = 0; i < 10; i++) {
                Integer driver = scoringDrivers.get(i).getDriver();
                Standing standing = this.standingService.findBySeasonAndDriver(season, driver);
                standing.setPoints(standing.getPoints() + points.get(i));
                this.standingService.save(standing);
            }
    }

    private Integer getScore() {
        return randomGenerator.ints(4000, 7258).findFirst().getAsInt();
    }
}
