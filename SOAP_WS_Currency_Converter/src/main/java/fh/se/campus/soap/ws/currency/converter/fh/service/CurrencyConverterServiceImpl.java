package fh.se.campus.soap.ws.currency.converter.fh.service;

import fh.se.campus.soap.ws.currency.converter.fh.model.CurrencyCode;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    @Override
    public double convert(String apiToken, CurrencyCode from, CurrencyCode to, Float amount) {
        return (100/2);
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
