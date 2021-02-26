package fh.se.campus.soap.ws.currency.converter.fh.service;

import fh.se.campus.soap.ws.currency.converter.fh.helper.EcbExchangeRateParser;
import fh.se.campus.soap.ws.currency.converter.fh.helper.ExchangeRateDTO;
import fh.se.campus.soap.ws.currency.converter.fh.model.CurrencyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    @Autowired
    EcbExchangeRateParser ecbExchangeRateParser;

    @Override
    public BigDecimal convert(String apiToken, CurrencyCode to, BigDecimal amount) throws Exception {
        ExchangeRateDTO dto = ecbExchangeRateParser.parseActual();
        BigDecimal targetRate = new BigDecimal(0);
        Map<Date, Map<String, BigDecimal>> dateMapMap = dto.getRates();
        BigDecimal bigDecimal = dateMapMap.values().iterator().next().get(to.toString());
        if(bigDecimal == null){
            throw new Exception(to+ " is not supported!");
        }
        return amount.multiply(bigDecimal);
    }

    @Override
    public String version() {
        return null;
    }

    @Override
    public String login(String username, String password) {
        return null;
    }
}
