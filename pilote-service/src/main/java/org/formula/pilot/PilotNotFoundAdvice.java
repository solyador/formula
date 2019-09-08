package org.formula.pilot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PilotNotFoundAdvice {
    
    @ResponseBody
    @ExceptionHandler(PilotNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String pilotNotFoundHandler(PilotNotFoundException exception) {
        return exception.getMessage();
    } 
}
