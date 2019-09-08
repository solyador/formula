package org.formula.pilot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDataBase {

    @Bean
    CommandLineRunner intiDataBase(PilotRepository pilotRepository) {
        return args -> {
            log.info("Preloading " + pilotRepository.save(new Pilot("Louis", "Hamilton")));
            log.info("Preloading " + pilotRepository.save(new Pilot("Valteri", "Botas")));
        };
    }
}
