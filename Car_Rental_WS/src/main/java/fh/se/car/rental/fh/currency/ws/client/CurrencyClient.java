package fh.se.car.rental.fh.currency.ws.client;

import fh.se.car.rental.fh.currency.ws.client.helper.Convert;
import fh.se.car.rental.fh.currency.ws.client.helper.ConvertResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class CurrencyClient extends WebServiceGatewaySupport {

    public ConvertResponse convertCurrency (String toCurrency, Double amount){
        Convert request = new Convert();
        request.setToCurrency(toCurrency);
        request.setAmount(amount);
        ConvertResponse response = (ConvertResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        return response;
    }
}
