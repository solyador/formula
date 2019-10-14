package org.formula.driver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDataBase {

    @Bean
    CommandLineRunner intiDataBase(DriverRepository driverRepository) {
        Double d = 1.213;
        return args -> {
            log.info("Preloading " + driverRepository.save(new Driver("Louis", "Hamilton", "Mercedes")));
            log.info("Preloading " + driverRepository.save(new Driver("Valteri", "Bottas", "Mercedes")));
            log.info("Preloading " + driverRepository.save(new Driver("Charles", "Leclerc", "Ferrari")));
            log.info("Preloading " + driverRepository.save(new Driver("Sebastien", "Vettel", "Ferrari")));

        };
    }
}
