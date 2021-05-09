package fh.se.car.rental.fh;

import fh.se.car.rental.fh.messaging.common.config.MessagingConfig;
import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;
import fh.se.car.rental.fh.repository.CarRepository;
import fh.se.car.rental.fh.security.JWTAuthorizationFilter;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
                    "W-56757554",
                    CarState.FREE,
                    CurrencyCode.USD
            );
            carRepository.save(car);
            car =
                    new Car(3L, "Golf8", "VM", 555D, "W-65756747", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(4L, "3er", "BMW", 888D, "W-3534345447", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(
                            5L,
                            "CLA",
                            "Mercedes-Benz",
                            846D,
                            "W-1343445",
                            CarState.FREE,
                            CurrencyCode.USD
                    );
            carRepository.save(car);
            car = new Car(6L, "407", "Pug", 454D, "W-sdfg438", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(7L, "C1", "Citroen", 324D, "W-fdg3436", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(
                            8L,
                            "911",
                            "Porsche",
                            1387D,
                            "W-21214554",
                            CarState.FREE,
                            CurrencyCode.USD
                    );
            carRepository.save(car);
            car = new Car(9L, "A4", "Audi", 898D, "W-54545444", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(10L, "A5", "Audi", 874D, "W-1656554163", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(11L, "Passat", "VW", 895D, "W-6519882", CarState.FREE, CurrencyCode.USD);
            carRepository.save(car);
            car =
                    new Car(12L, "Käfer", "VW", 356D, "W-6262651", CarState.FREE, CurrencyCode.USD);
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
