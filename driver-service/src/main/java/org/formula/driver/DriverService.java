package org.formula.driver;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DriverService implements IDriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }
    
    @Override
    public List<Driver> findAll() {
		return driverRepository.findAll();
	}

    @Override
	public Driver save(Driver driver) {
		return driverRepository.save(driver);
	}

    @Override
	public Driver findById(Long id) {
        return driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException(id));
	}

    @Override
	public void deleteById(Long id) {
        driverRepository.deleteById(id);
    }
    
    @Override
	public void updateDriver(Driver newDriver, Long id) {
        driverRepository.findById(id).map(item -> {
                                   item.setFirstName(newDriver.getFirstName());
                                   item.setLastName(newDriver.getLastName());
                                   driverRepository.save(item);
            return null;
        });
	}

    @Override
    public boolean exists(Driver driver) {
        Driver searchedDriver = driverRepository.findByFirstNameAndLastName(driver.getFirstName(), driver.getLastName());
        return searchedDriver != null;
    }
}
