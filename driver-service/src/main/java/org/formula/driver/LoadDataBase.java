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
        return args -> {
            if (driverRepository.findAll().isEmpty()) {
                log.info("Preloading " + driverRepository.save(new Driver("Lewis", "Hamilton", "Mercedes")));
                log.info("Preloading " + driverRepository.save(new Driver("Valtteri", "Bottas", "Mercedes")));
                log.info("Preloading " + driverRepository.save(new Driver("Charles", "Leclerc", "Ferrari")));
                log.info("Preloading " + driverRepository.save(new Driver("Sebastian", "Vettel", "Ferrari")));
                log.info("Preloading " + driverRepository.save(new Driver("Alexander", "Albon", "Red Bull Racing")));
                log.info("Preloading " + driverRepository.save(new Driver("Max", "Verstappen", "Red Bull Racing")));
                log.info("Preloading " + driverRepository.save(new Driver("Pierre", "Gasly", "Toro rosso")));
                log.info("Preloading " + driverRepository.save(new Driver("Daniil", "Kvyat", "Toro rosso")));
                log.info("Preloading " + driverRepository.save(new Driver("Daniel", "Ricciardo", "Renault")));
                log.info("Preloading " + driverRepository.save(new Driver("Nico", "Hulkenberg", "Renault")));
                log.info("Preloading " + driverRepository.save(new Driver("Sergio", "Perez", "Racing point")));
                log.info("Preloading " + driverRepository.save(new Driver("Lance", "Stroll", "Racing point")));
                log.info("Preloading " + driverRepository.save(new Driver("Lando", "Norris", "Maclaren")));
                log.info("Preloading " + driverRepository.save(new Driver("Carlos", "Sainz", "Maclaren")));
                log.info("Preloading " + driverRepository.save(new Driver("Antonio", "Giovinazzi", "Alfa romeo")));
                log.info("Preloading " + driverRepository.save(new Driver("Kimi", "Raikkonen", "Alfa romeo")));
                log.info("Preloading " + driverRepository.save(new Driver("Romain", "Grosjean", "Haas")));
                log.info("Preloading " + driverRepository.save(new Driver("Kevin", "Magnussen", "Haas")));
                log.info("Preloading " + driverRepository.save(new Driver("Robert", "Kubica", "Williams")));
                log.info("Preloading " + driverRepository.save(new Driver("George", "Russell", "Williams")));
            }
        };
    }
}
