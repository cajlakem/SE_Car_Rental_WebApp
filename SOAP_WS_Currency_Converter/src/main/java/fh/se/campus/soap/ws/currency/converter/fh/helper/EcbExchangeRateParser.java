package fh.se.campus.soap.ws.currency.converter.fh.helper;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Andrej Kuročenko <kurochenko@mail.muni.cz>
 */
@Component
public class EcbExchangeRateParser implements ExchangeRateParser{

    /** Node attribute name which contains currency rate validity date */
    public static final String XML_TIME_ATTR_NAME = "time";

    /** Node attribute name which contains currency code */
    public static final String XML_CURRENCY_ATTR_NAME = "currency";

    /** Node attribute name which contains currency rate */
    public static final String XML_RATE_ATTR_NAME = "rate";

    /** Currency rate validity date format */
    public static final String VALIDITY_DATE_FORMAT = "y-M-d";

    /**
     * URL path to XML file with currency rates
     */
    private static final String RATES_XML_DAILY = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private static final String RATES_XML_ALL = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
    public static final String DATE_XPATH = "/"+EcbNamespaceContext.NS_GMS+":Envelope/"+EcbNamespaceContext.NS_ECB+":Cube/"+EcbNamespaceContext.NS_ECB+":Cube";
    public static final String CURRENCY_XPATH = "./"+EcbNamespaceContext.NS_ECB+":Cube";

    public ExchangeRateDTO parseActual() {
        try {
            return rawParse(RATES_XML_DAILY);
        } catch (XPathExpressionException e) {
        }
        return null;
    }

    public ExchangeRateDTO parseAll() {
        try {
            return rawParse(RATES_XML_ALL);
        } catch (XPathExpressionException e) {
        }
        return null;
    }

    private ExchangeRateDTO rawParse(String xmlUrl) throws XPathExpressionException {

        ExchangeRateDTO result = new ExchangeRateDTO();
        Map<Date, Map<String, BigDecimal>> resultMap = new HashMap<Date, Map<String, BigDecimal>>();

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new EcbNamespaceContext());

        XPathExpression dateXPath =  xpath.compile(DATE_XPATH);
        XPathExpression currencyXPath = xpath.compile(CURRENCY_XPATH);

        NodeList dateNodes = (NodeList) dateXPath.evaluate(getDocument(xmlUrl), XPathConstants.NODESET);

        for (int i = 0; i < dateNodes.getLength(); i++) {
            Map<String, BigDecimal> rates = new HashMap<String, BigDecimal>();
            String time = dateNodes.item(i).getAttributes().getNamedItem(XML_TIME_ATTR_NAME).getNodeValue();
            NodeList currencyNodes = (NodeList) currencyXPath.evaluate(dateNodes.item(i), XPathConstants.NODESET);

            for (int j = 0; j < currencyNodes.getLength(); j++) {
                rates.put(
                        currencyNodes.item(j).getAttributes().getNamedItem(XML_CURRENCY_ATTR_NAME).getNodeValue(),
                        new BigDecimal(currencyNodes.item(j).getAttributes().getNamedItem(XML_RATE_ATTR_NAME).getNodeValue())
                );
            }

            resultMap.put(parseValidityDate(time), rates);
        }

        result.setRates(resultMap);
        return result;
    }

    /**
     * Parses string date to {@code java.util.Date}
     * @param date string date representation
     * @return date or {@code null} when parse error occurs
     */
    private Date parseValidityDate(String date) {

        DateFormat dateFormat = new SimpleDateFormat(VALIDITY_DATE_FORMAT);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
        }

        return null;
    }

    /**
     * Loads XML file with actual currency rates from URL saved in configuration file
     * and parses it into {@code org.w3c.dom.Document}
     * @return parsed document or {@code null} when any error occurs
     */
    private Document getDocument(String xmlUrl) {

        Document document = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(xmlUrl);

            inputStream = url.openStream();

            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);

            DocumentBuilder builder = domFactory.newDocumentBuilder();

            document = builder.parse(inputStream);

        } catch (MalformedURLException e) {

        } catch (IOException e) {

        } catch (SAXException e) {

        } catch (ParserConfigurationException e) {

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
        }

        return document;
    }
}
