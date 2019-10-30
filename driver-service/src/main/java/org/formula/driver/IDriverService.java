package org.formula.driver;

import java.util.List;

public interface IDriverService {

    List<Driver> findAll();
    Driver save(Driver driver);
    Driver findById(Long id);
    void deleteById(Long id);
    void updateDriver(Driver newDriver, Long id);
    boolean exists(Driver driver);
}
