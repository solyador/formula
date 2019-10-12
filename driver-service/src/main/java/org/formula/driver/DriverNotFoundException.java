package org.formula.driver;

public class DriverNotFoundException extends RuntimeException {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    DriverNotFoundException(Long id) {
        super("Could not find driver " + id);
    }
}
