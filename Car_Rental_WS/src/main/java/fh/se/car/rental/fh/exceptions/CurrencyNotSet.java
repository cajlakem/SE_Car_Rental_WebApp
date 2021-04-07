package fh.se.car.rental.fh.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CurrencyNotSet extends RuntimeException {

  public CurrencyNotSet(String exception) {
    super(exception);
  }
}
