package fh.se.campus.soap.ws.currency.converter.fh.service;

import fh.se.campus.soap.ws.currency.converter.fh.exceptions.AuthenticationException;
import fh.se.campus.soap.ws.currency.converter.fh.exceptions.ConvertException;
import fh.se.campus.soap.ws.currency.converter.fh.exceptions.ServiceExceptionDetails;
import fh.se.campus.soap.ws.currency.converter.fh.helper.EcbExchangeRateParser;
import fh.se.campus.soap.ws.currency.converter.fh.helper.ExchangeRateDTO;
import fh.se.campus.soap.ws.currency.converter.fh.model.CurrencyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    private static EcbExchangeRateParser ecbExchangeRateParser = new EcbExchangeRateParser();
    @Resource
    WebServiceContext context;

    @Override
    public BigDecimal convert(CurrencyCode to, BigDecimal amount) throws AuthenticationException, Exception, ConvertException {
        this.autheticate();
        if((to == null) || (amount == null)){
            throw new ConvertException("Check your parameters", this.createExceptionDetails("Check your parameters!", "101"));
        }

        ExchangeRateDTO dto = ecbExchangeRateParser.parseActual();
        Map<Date, Map<String, BigDecimal>> dateMapMap = dto.getRates();
        BigDecimal bigDecimal = dateMapMap.values().iterator().next().get(to.toString());

        if(bigDecimal == null){
            throw new ConvertException(to+ " is not supported!", this.createExceptionDetails(to+ " is not supported!", "100"));
        }
        return amount.multiply(bigDecimal);
    }

    @Override
    public String version() throws AuthenticationException{
        this.autheticate();
        return "v1.0";
    }

    private void autheticate() throws AuthenticationException {
        //TODO: authenticate against a DB
        MessageContext messageContext = context.getMessageContext();
        Map httpHeaders = (Map) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);
        List userNameList = (List) httpHeaders.get("username");
        List passwordList = (List) httpHeaders.get("password");

        if(passwordList == null || userNameList == null)
            throw new AuthenticationException("Credentials not set!", this.createExceptionDetails("Credentials not set!", "401"));

        if (userNameList.contains("emir") && passwordList.contains("test"))
            return;
        throw new AuthenticationException("Credentials wrong", this.createExceptionDetails("Credentials wrong", "401"));

    }

    private ServiceExceptionDetails[] createExceptionDetails(String message, String code){
        ServiceExceptionDetails serviceExceptionDetails = new ServiceExceptionDetails();
        serviceExceptionDetails.setFaultMessage(message);
        serviceExceptionDetails.setFaultCode(code);
        ServiceExceptionDetails[] s = new ServiceExceptionDetails[1];
        s[0] = serviceExceptionDetails;
        return s;
    }
}
