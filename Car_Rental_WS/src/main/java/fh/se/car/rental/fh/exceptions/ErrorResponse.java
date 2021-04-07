package fh.se.car.rental.fh.exceptions;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class ErrorResponse {

  public ErrorResponse(String message, List<String> details) {
    super();
    this.message = message;
    this.details = details;
  }

  private String message;
  private List<String> details;

  public String getMessage() {
    return message;
  }

  public ErrorResponse setMessage(String message) {
    this.message = message;
    return this;
  }

  public List<String> getDetails() {
    return details;
  }

  public ErrorResponse setDetails(List<String> details) {
    this.details = details;
    return this;
  }
}
