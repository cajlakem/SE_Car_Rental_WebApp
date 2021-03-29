package fh.se.car.rental.fh.currency.ws.client;

import fh.se.car.rental.fh.currency.ws.client.helper.ConvertCurrency;
import fh.se.car.rental.fh.currency.ws.client.helper.ConvertCurrencyResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class CurrencyClient extends WebServiceGatewaySupport {

    public ConvertCurrencyResponse convertCurrency (String toCurrency, Double amount){
        ConvertCurrency request = new ConvertCurrency();
        request.setToCurrency(toCurrency);
        request.setAmount(amount);
        ConvertCurrencyResponse response = (ConvertCurrencyResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        return response;
    }
}
