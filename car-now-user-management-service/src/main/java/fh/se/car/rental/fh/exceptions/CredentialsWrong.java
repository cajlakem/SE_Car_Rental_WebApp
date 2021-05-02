package fh.se.car.rental.fh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CredentialsWrong extends RuntimeException {

    public CredentialsWrong(String exception) {
        super(exception);
    }
}
