package fh.se.car.rental.fh;

import fh.se.car.rental.fh.currency.ws.client.CurrencyClient;
import fh.se.car.rental.fh.messaging.common.config.MessagingConfig;
import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;
import fh.se.car.rental.fh.repository.CarRepository;
import fh.se.car.rental.fh.repository.UserRepository;
import fh.se.car.rental.fh.security.JWTAuthorizationFilter;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@SpringBootApplication
public class CarRentalRESTWebService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CurrencyClient currencyClient;

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
                    .antMatchers(HttpMethod.GET, "/salesordermanagementbackend/api/v1/currencyCodes")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
            http.cors();
        }
    }

    @Bean
    InitializingBean initDatabase() {
        return () -> {
            User user = new User(
                    "testuser",
                    "testuser",
                    "Dummy",
                    "Dummy",
                    "test",
                    true,
                    "emir@cajlakovic"
            );
            userRepository.save(user);
            carRepository.deleteAll();
            Car car = new Car("W-sdfg43", "407", "Pug", 454D, "W-sdfg43", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car("W-651988", "Passat", "VW", 895D, "W-651988", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car("W-626265", "Z4", "BMW", 356D, "W-626265", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            //currencyClient.convertCurrency("EUR", 100D);
        };
    }

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(MessagingConfig.EXCHANGE_NAME);
    }

    @Bean
    public Queue appQueueGeneric() {
        return new Queue(MessagingConfig.LOGGING);
    }

    @Bean
    public Queue appQueueUserMgt() {
        return new Queue(MessagingConfig.SIGN_UP_2_USER_MANAGEMENT);
    }

    @Bean
    public Queue appQueueAuth() {
        return new Queue(MessagingConfig.SIGN_UP_2_AUTHENTICATION);
    }

    @Bean
    public Queue appQueueSOMgt() {
        return new Queue(MessagingConfig.SALES_ORDER_UPDATE);
    }

    @Bean
    public Queue appQueueCar() {
        return new Queue(MessagingConfig.CAR_UPDATE);
    }

    @Bean
    public Queue appQueueCarFree() {
        return new Queue(MessagingConfig.CAR_UPDATE_FREE);
    }

    @Bean
    public Binding declareBindingCarFree() {
        return BindingBuilder.bind(appQueueCarFree()).to(appExchange()).with(MessagingConfig.CAR_UPDATE_FREE_KEY);
    }

    @Bean
    public Binding declareBindingCar() {
        return BindingBuilder.bind(appQueueCar()).to(appExchange()).with(MessagingConfig.CAR_UPDATE_KEY);
    }

    @Bean
    public Binding declareBindingSO() {
        return BindingBuilder.bind(appQueueSOMgt()).to(appExchange()).with(MessagingConfig.SO_UPDATE);
    }

    @Bean
    public Binding declareBindingGeneric() {
        return BindingBuilder.bind(appQueueGeneric()).to(appExchange()).with(MessagingConfig.LOGGING_KEY);
    }

    @Bean
    public Binding declareBindingAuth() {
        return BindingBuilder.bind(appQueueAuth()).to(appExchange()).with(MessagingConfig.SIGN_UP);
    }

    @Bean
    public Binding declareBindingUSerMgt() {
        return BindingBuilder.bind(appQueueUserMgt()).to(appExchange()).with(MessagingConfig.SIGN_UP);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
