using CurrencyConverterWebservice.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Xml;

namespace CurrencyConverterWebservice
{
    public class ConverterService : IConverterService
    {
        private const String XML_FILE_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
        private const String AMOUNT_CURRENCY = "USD";
        private const String CURRENCY_ATTRIBUTE_VALUE = "currency";
        private const String RATE_ATTRIBUTE_VALUE = "rate";
        private XmlDocument currencyDoc;
        private Dictionary<String, Double> currencyAndRatesMap = new Dictionary<String, double>();
        private List<String> availableCurrencies = new List<String> {"EUR", "USD", "JPY", "BGN", "CZK", "DKK", "GBP", "HUF", "PLN",
                                                                     "RON", "SEK", "CHF", "ISK", "NOK", "HRK", "RUB", "TRY", "AUD",
                                                                     "BRL", "CAD", "CNY", "HKD", "IDR", "ILS", "INR", "KRW", "MXN",
                                                                     "MYR", "NZD", "PHP", "SGD", "THB", "ZAR" };

        public double convert(string apiToken,string toCurrency, double amount)
        {
            double convertedAmount = 0;

            if (amount <= 0 || !apiToken.Equals("ichHasseDasWortValide") || !availableCurrencies.Contains(toCurrency.ToUpper()))
                return 0;

            readXML();
            //if (!availableCurrencies.Contains(toCurrency.ToUpper()))
            //    throw new SoapException("The currency " + toCurrency + " is not listed in the xml file", SoapException.ClientFaultCode);

            if (toCurrency.Equals(AMOUNT_CURRENCY))
                return amount;
            else if (toCurrency.Equals("EUR"))
                convertedAmount = Math.Round(convertEuro(amount), 2);
            else
                convertedAmount = Math.Round(convertNonEuro(toCurrency, amount), 2);

            return convertedAmount;
        }

        private void readXML()
        {
            if (currencyAndRatesMap.Count > 0)
                return;

            currencyDoc = new XmlDocument();
            currencyDoc.Load(XML_FILE_URL);
            foreach (XmlNode xmlNode in currencyDoc.DocumentElement.ChildNodes[2].ChildNodes[0].ChildNodes)
            {
                double rate = 0;
                try
                {
                    rate = Convert.ToDouble(xmlNode.Attributes[RATE_ATTRIBUTE_VALUE].Value);
                }
                catch(FormatException fe)
                {
                    rate = Convert.ToDouble(xmlNode.Attributes[RATE_ATTRIBUTE_VALUE].Value.Replace(".", ","));
                }
                
                currencyAndRatesMap.Add(xmlNode.Attributes[CURRENCY_ATTRIBUTE_VALUE].Value,rate);
            }
        }

        private double convertNonEuro(String toCurrency, double amount)
        {
            double convertedAmount = 0;
            double toCurrencyValue = currencyAndRatesMap[toCurrency.ToUpper()];
            double convertedEuroValue = convertEuro(amount);
            convertedAmount = convertedEuroValue * toCurrencyValue;
            return convertedAmount;
        }

        private double convertEuro(double amount)
        {
            double convertedAmount = 0;
            double toCurrencyValue = currencyAndRatesMap[AMOUNT_CURRENCY];
            convertedAmount = amount / toCurrencyValue;
            return convertedAmount;
        }
    }
}
