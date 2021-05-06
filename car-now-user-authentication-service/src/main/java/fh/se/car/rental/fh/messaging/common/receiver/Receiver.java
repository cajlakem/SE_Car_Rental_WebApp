package fh.se.car.rental.fh.messaging.common.receiver;

import fh.se.car.rental.fh.messaging.common.config.MessagingConfig;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    UserRepository userRepository;

    @RabbitListener(queues = MessagingConfig.SIGN_UP_2_AUTHENTICATION)
    public void receiveMessage(final fh.se.car.rental.fh.messaging.common.User message) {
        logger.info("Adding a new user with username: "+message.getUserName());
        userRepository.save(new User(message.getUserName(), message.getUserName(), message.getFirstName(), message.getLastName(), message.getPassword(), true, message.getEmail()));
    }
}
