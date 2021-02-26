package fh.se.campus.soap.ws.currency.converter.fh;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({ "classpath:webservice-definition-beans.xml" })
public class SoapWsCurrencyConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoapWsCurrencyConverterApplication.class, args);
    }

}
