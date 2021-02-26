package fh.se.campus.soap.ws.currency.converter.fh.helper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class ExchangeRateDTO {

    Map<Date, Map<String, BigDecimal>> rates;


    public Map<Date, Map<String, BigDecimal>> getRates() {
        return rates;
    }

    public void setRates(Map<Date, Map<String, BigDecimal>> rates) {
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExchangeRateDTO that = (ExchangeRateDTO) o;

        if (rates != null ? !rates.equals(that.rates) : that.rates != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return rates != null ? rates.hashCode() : 0;
    }
}
