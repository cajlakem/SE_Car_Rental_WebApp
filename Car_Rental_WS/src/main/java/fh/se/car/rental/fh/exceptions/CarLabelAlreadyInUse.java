package fh.se.car.rental.fh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CarLabelAlreadyInUse extends RuntimeException {
    public CarLabelAlreadyInUse(String exception) {
        super(exception);
    }
}
