package org.formula.driver;

import java.util.List;

public interface IDriverService {

    public List<Driver> findAll();
    public Driver save(Driver driver);
    public Driver findById(Long id);
    public void deleteById(Long id);
    public void updateDriver(Driver newDriver, Long id);
	public boolean exists(Driver driver);
}
