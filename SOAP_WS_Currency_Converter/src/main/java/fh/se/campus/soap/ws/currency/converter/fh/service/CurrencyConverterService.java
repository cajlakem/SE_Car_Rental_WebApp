package fh.se.campus.soap.ws.currency.converter.fh.service;

import fh.se.campus.soap.ws.currency.converter.fh.exceptions.AuthenticationException;
import fh.se.campus.soap.ws.currency.converter.fh.exceptions.ConvertException;
import fh.se.campus.soap.ws.currency.converter.fh.model.CurrencyCode;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.math.BigDecimal;

@WebService(serviceName = "CurrencyConverterService")
public interface CurrencyConverterService {



    @WebMethod()
    @WebResult(name = "Amount")
    public BigDecimal convert(@WebParam(name = "to") CurrencyCode to, @WebParam(name = "amount") BigDecimal amount) throws AuthenticationException, Exception, ConvertException;
    //throws AuthenticationException, Exception, ConvertException";

    @WebMethod()
    @WebResult(name = "Version")
    public String version() throws AuthenticationException;

}
