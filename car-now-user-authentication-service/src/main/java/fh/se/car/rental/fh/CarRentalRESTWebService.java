package fh.se.car.rental.fh;

import fh.se.car.rental.fh.repository.UserRepository;
import fh.se.car.rental.fh.security.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
                    .antMatchers(HttpMethod.POST, "/userauthorizationtbackend/api/v1/users/login")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
            http.cors();
        }
    }
}
