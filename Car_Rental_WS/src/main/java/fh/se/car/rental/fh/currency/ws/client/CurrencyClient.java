package fh.se.car.rental.fh.currency.ws.client;

import fh.se.car.rental.fh.currency.ws.client.helper.Convert;
import fh.se.car.rental.fh.currency.ws.client.helper.ConvertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class CurrencyClient extends WebServiceGatewaySupport {

    @Autowired
    private Environment env;


    public ConvertResponse convertCurrency (String toCurrency, Double amount){
        Convert request = new Convert();
        request.setToCurrency(toCurrency);
        request.setAmount(amount);
        request.setApiToken(env.getProperty("carrental.apiToken"));
        ConvertResponse response = (ConvertResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        return response;
    }
}
