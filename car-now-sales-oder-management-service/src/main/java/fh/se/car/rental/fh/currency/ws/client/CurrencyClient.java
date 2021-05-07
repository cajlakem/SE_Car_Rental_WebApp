package fh.se.car.rental.fh.currency.ws.client;

import fh.se.car.rental.fh.currency.ws.client.helper.Convert;
import fh.se.car.rental.fh.currency.ws.client.helper.ConvertResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class CurrencyClient extends WebServiceGatewaySupport {
    @Autowired
    private Environment env;

    Logger logger = LoggerFactory.getLogger(CurrencyClient.class);

    public ConvertResponse convertCurrency(String toCurrency, Double amount) {
        Convert request = new Convert();
        request.setToCurrency(toCurrency);
        request.setAmount(amount);
        request.setApiToken(env.getProperty("carrental.apiToken"));
        logger.info(
                "Calling convertCurrency (toString: " +
                        toCurrency +
                        " amount: " +
                        amount.toString() +
                        " token:" +
                        request.getApiToken()
        );
        ConvertResponse response = (ConvertResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request);
        logger.info("Result" + String.valueOf(response.getConvertResult()));
        return response;
    }
}
