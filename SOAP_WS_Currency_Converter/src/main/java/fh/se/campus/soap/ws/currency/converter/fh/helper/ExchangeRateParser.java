package fh.se.campus.soap.ws.currency.converter.fh.helper;

/**
 * @author Andrej Kuročenko <kurochenko@mail.muni.cz>
 */
public interface ExchangeRateParser {
    public ExchangeRateDTO parseAll();
    public ExchangeRateDTO parseActual();
}
