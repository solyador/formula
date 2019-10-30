package org.formula.driver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final Logger LOG = LoggerFactory.getLogger(DriverController.class);
    private final IDriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverService.findAll();
        if (drivers == null || drivers.isEmpty()) {
            LOG.info("no drivers found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDriver(@PathVariable Long id) {
        LOG.info("getting driver with id {}", id);
        Driver driver = driverService.findById(id);
        if (driver == null) {
            LOG.info("driver with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addDriver(@RequestBody Driver driver) {
        LOG.info("adding new driver with id {}", driver);
        if (driverService.exists(driver)) {
            LOG.info("a driver with name " + driver.getFirstName() + " " + driver.getLastName() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(driverService.save(driver), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDriver(@PathVariable Long id, @RequestBody Driver newDriver) {
        LOG.info("updating driver with id {}", newDriver);
        Driver currentDriver = driverService.findById(id);
        if (currentDriver == null) {
            LOG.info("Driver with id {} not found", id);
            return new ResponseEntity<Driver>(HttpStatus.NOT_FOUND);
        }
        driverService.updateDriver(newDriver, id);
        return new ResponseEntity<>(newDriver, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDriver(@PathVariable Long id) {
        LOG.info("deleting driver with id {}", id);
        Driver driver = driverService.findById(id);
        if (driver == null) {
            LOG.info("Driver with id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        driverService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
