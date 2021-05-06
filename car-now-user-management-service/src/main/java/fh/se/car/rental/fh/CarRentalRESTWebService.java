package fh.se.car.rental.fh;

import fh.se.car.rental.fh.messaging.common.config.MessagingConfig;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.repository.UserRepository;
import fh.se.car.rental.fh.security.JWTAuthorizationFilter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                    .anyRequest()
                    .authenticated();
            http.cors();
        }
    }

    @Bean
    InitializingBean initDatabase() {
        return () -> {
            User user = new User(
                    System.currentTimeMillis(),
                    "testuser",
                    "Dummy",
                    "Dummy",
                    "test",
                    true,
                    "emir@cajlakovic"
            );
            if(userRepository.count() == 0)
                userRepository.save(user);
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
