package org.formula.standing;

public class StandingNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    StandingNotFoundException(Long id) {
        super("Could not find standing " + id);
    }
}
