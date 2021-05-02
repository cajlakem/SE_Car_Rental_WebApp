package fh.se.car.rental.fh;

import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;
import fh.se.car.rental.fh.repository.CarRepository;
import fh.se.car.rental.fh.security.JWTAuthorizationFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class CarRentalRESTWebService {

    @Autowired
    CarRepository carRepository;

    public static void main(String[] args) {
        SpringApplication.run(CarRentalRESTWebService.class, args);
    }

    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf()
                    .disable()
                    .addFilterAfter(
                            new JWTAuthorizationFilter(),
                            UsernamePasswordAuthenticationFilter.class
                    )
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/v1/users/login")
                    .permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/users/register")
                    .permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/currencyCodes")
                    .permitAll()
                    .antMatchers(HttpMethod.GET, "/swagger/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
            http.cors();
        }
    }

    @Bean
    InitializingBean initDatabase() {
        return () -> {
            Car car = new Car(
                    2L,
                    "C4",
                    "Covette",
                    1157D,
                    "W-5675755",
                    CarState.FREE,
                    CurrencyCode.USD
            );
            carRepository.save(car);
            car =
                    new Car(3L, "Golf8", "VM", 555D, "W-6575674", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(4L, "3er", "BMW", 888D, "W-35343454", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(
                            5L,
                            "CLA",
                            "Mercedes-Benz",
                            846D,
                            "W-134344",
                            CarState.FREE,
                            CurrencyCode.USD
                    );
            carRepository.save(car);
            car = new Car(6L, "407", "Pug", 454D, "W-sdfg43", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(7L, "C1", "Citroen", 324D, "W-fdg343", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(
                            8L,
                            "911",
                            "Porsche",
                            1387D,
                            "W-2121454",
                            CarState.FREE,
                            CurrencyCode.USD
                    );
            carRepository.save(car);
            car = new Car(9L, "A4", "Audi", 898D, "W-5454544", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(10L, "A5", "Audi", 874D, "W-165655416", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(11L, "Passat", "VW", 895D, "W-651988", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(12L, "Käfer", "VW", 356D, "W-626265", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            //currencyClient.convertCurrency("EUR", 100D);
        };
    }
}
