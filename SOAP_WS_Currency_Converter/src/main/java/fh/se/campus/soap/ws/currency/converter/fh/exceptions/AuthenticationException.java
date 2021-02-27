package fh.se.campus.soap.ws.currency.converter.fh.exceptions;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

public class AuthenticationException extends Exception implements Serializable {

    private ServiceExceptionDetails faultDetails[];

    public AuthenticationException(ServiceExceptionDetails faultDetails[]) {
        this.faultDetails = faultDetails;
    }

    public AuthenticationException(String message, ServiceExceptionDetails faultDetails[]) {
        super(message);
        this.faultDetails = faultDetails;
    }

    public ServiceExceptionDetails[] getFaultDetails() {
        return faultDetails;
    }

}
