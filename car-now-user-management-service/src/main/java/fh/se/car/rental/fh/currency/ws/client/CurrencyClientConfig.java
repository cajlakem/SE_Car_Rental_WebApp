package fh.se.car.rental.fh.currency.ws.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CurrencyClientConfig {
    @Autowired
    private Environment env;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("fh.se.car.rental.fh.currency.ws.client.helper");
        return marshaller;
    }

    @Bean
    public CurrencyClient countryClient(Jaxb2Marshaller marshaller) {
        CurrencyClient client = new CurrencyClient();
        client.setDefaultUri(env.getProperty("carrental.currency.client.url"));
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
