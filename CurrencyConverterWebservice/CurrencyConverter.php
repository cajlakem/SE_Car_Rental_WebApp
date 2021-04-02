<?php
    ini_set("soap.wsdl_cache_enabled", "0");

    function loadAllCurrenciesFromXML() {
		$currencies = array();
		$xmlurl = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
		
		$xml = simplexml_load_file($xmlurl) or die("feed not loading");
		
		foreach ($xml->Cube->Cube->Cube as $currency) {
			$currencies[(string)$currency['currency']] = (string)$currency['rate'];
		}
		return $currencies;
	}
	
	function convertToCurrency($toCurrency,$amount){
		$convertedAmount = 0;

		if ($amount <= 0) {
			return 0;
		}
		
		if (strtoupper($toCurrency) == "USD") {
			return $amount;
		}
		
		$currencies = loadAllCurrenciesFromXML();
		#if (!availableCurrencies.Contains(toCurrency.ToUpper()))
        #        throw new SoapException("The currency " + toCurrency + " is not listed in the xml file", SoapException.ClientFaultCode);
	
	    if (strtoupper($toCurrency) == "EUR") {
			$convertedAmount = convertEuro($amount,$currencies);
		}
		else {
			$convertedAmount = convertNonEuro(strtoupper($toCurrency), $amount, $currencies);
		}
		
		return round($convertedAmount,2);
	}
	
	function convertNonEuro($toCurrency, $amount, $currencies)
	{
		$convertedAmount = 0;
		$toCurrencyValue = $currencies[$toCurrency];
		$convertedEuroValue = convertEuro($amount, $currencies);
		$convertedAmount = $convertedEuroValue * $toCurrencyValue;
		return $convertedAmount;
	}

	function convertEuro($amount, $currencies)
	{
		$convertedAmount = 0;
		$toCurrencyValue = $currencies["USD"];
		$convertedAmount = $amount / $toCurrencyValue;
		return $convertedAmount;
	}
	
	#$server=newSoapServer("http://localhost/CurrencyConverterWebservice/currencyConverter.wsdl");
	$server = new SoapServer( 
							 "converter.wsdl", 
							 array( 
							  'uri'=>"http://localhost/test-uri",
							  'encoding'=>'UTF-8', 
							  'soap_version'=>SOAP_1_2
							 ) 
							);
	$server->addFunction("convertToCurrency");
	$server->handle();
?>