package fh.se.campus.soap.ws.currency.converter.fh.exceptions;

import java.io.Serializable;

public class ConvertException extends Exception implements Serializable {

    private ServiceExceptionDetails faultDetails[];

    public ConvertException(ServiceExceptionDetails faultDetails[]) {
        this.faultDetails = faultDetails;
    }

    public ConvertException(String message, ServiceExceptionDetails faultDetails[]) {
        super(message);
        this.faultDetails = faultDetails;
    }

    public ServiceExceptionDetails[] getFaultDetails() {
        return faultDetails;
    }

}
