package org.formula;

import lombok.extern.slf4j.Slf4j;
import org.formula.reference.RaceReference;
import org.formula.reference.RaceReferenceRepository;
import org.formula.season.Season;
import org.formula.season.SeasonRepository;
import org.formula.standing.Standing;
import org.formula.standing.StandingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDataBase {

    @Bean
    CommandLineRunner intiDataBase(RaceReferenceRepository raceReferenceRepository,
                                   SeasonRepository seasonRepository,
                                   StandingRepository standingRepository) {
        return args -> {
            if (raceReferenceRepository.findAll().isEmpty()) {
                initRaceReference(raceReferenceRepository);
            }

            if (seasonRepository.findAll().isEmpty()) {
                initSeason(seasonRepository);
            }

            if (standingRepository.findAll().isEmpty()) {
                initStanding(standingRepository);
            }
        };
    }

    private void initStanding(StandingRepository standingRepository) {
        standingRepository.save(new Standing(1, 1, 0));
        standingRepository.save(new Standing(1, 2, 0));
        standingRepository.save(new Standing(1, 3, 0));
        standingRepository.save(new Standing(1, 4, 0));
        standingRepository.save(new Standing(1, 5, 0));
        standingRepository.save(new Standing(1, 6, 0));
        standingRepository.save(new Standing(1, 7, 0));
        standingRepository.save(new Standing(1, 8, 0));
        standingRepository.save(new Standing(1, 9, 0));
        standingRepository.save(new Standing(1, 10, 0));
        standingRepository.save(new Standing(1, 11, 0));
        standingRepository.save(new Standing(1, 12, 0));
        standingRepository.save(new Standing(1, 13, 0));
        standingRepository.save(new Standing(1, 14, 0));
        standingRepository.save(new Standing(1, 15, 0));
        standingRepository.save(new Standing(1, 16, 0));
        standingRepository.save(new Standing(1, 17, 0));
        standingRepository.save(new Standing(1, 18, 0));
        standingRepository.save(new Standing(1, 19, 0));
        standingRepository.save(new Standing(1, 20, 0));
    }

    private void initSeason(SeasonRepository seasonRepository) {
        seasonRepository.save(new Season(0));
    }

    private void initRaceReference(RaceReferenceRepository raceReferenceRepository) {
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("AUSTRALIA")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("BAHRAIN")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("CHINA")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("AZERBAIJAN")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("SPAIN")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("MONACO")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("CANADA")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("FRANCE")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("AUSTRIA")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("GREAT BRITAIN")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("GERMANY")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("HUNGARY")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("BELGIUM")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("ITALY")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("SINGAPORE")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("RUSSIA")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("JAPAN")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("MEXICO")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("UNITED STATES")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("BRAZIL")));
        log.info("Preloading " + raceReferenceRepository.save(new RaceReference("UNITED ARAB EMIRATES")));
    }
}
