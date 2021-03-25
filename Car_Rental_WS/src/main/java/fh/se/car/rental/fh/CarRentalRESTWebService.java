package fh.se.car.rental.fh;

import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;
import fh.se.car.rental.fh.repository.CarRepository;
import fh.se.car.rental.fh.repository.UserRepository;
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
    UserRepository userRepository;
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
            http.csrf().disable().addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/v1/users/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/users/register").permitAll()
                    .anyRequest().authenticated();
        }
    }

    @Bean
    InitializingBean initDatabase(){
        return () -> {
            User user = new User(1L, "testuser", "Dummy", "Dummy", "test", true, "emir@cajlakovic");
            userRepository.save(user);
            Car car = new Car(1L, "C4","Covette", 100F,"W-1235454", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
        };
    };
}
