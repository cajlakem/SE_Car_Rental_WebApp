package fh.se.campus.soap.ws.currency.converter.fh.service;

import fh.se.campus.soap.ws.currency.converter.fh.model.CurrencyCode;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.math.BigDecimal;

@WebService(serviceName = "CurrencyConverterService")
public interface CurrencyConverterService {



    @WebMethod()
    @WebResult(name = "Amount")
    public BigDecimal convert(@WebParam(name = "apiToken") String apiToken, @WebParam(name = "to") CurrencyCode to, @WebParam(name = "amount") BigDecimal amount) throws Exception;

    @WebMethod()
    @WebResult(name = "Version")
    public String version() throws Exception;

    @WebMethod()
    @WebResult(name = "Token")
    public String login(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws Exception;

}
