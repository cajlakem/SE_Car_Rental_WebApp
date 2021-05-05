package fh.se.car.rental.fh.messaging.common.receiver;

import fh.se.car.rental.fh.messaging.common.config.MessagingConfig;
import fh.se.car.rental.fh.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    Logger logger = LoggerFactory.getLogger(Receiver.class);

    //@Autowired
    //private UserRepository userRepository;

    @RabbitListener(queues = MessagingConfig.SIGNED_USERS)
    public void receiveMessage(final fh.se.car.rental.fh.messaging.common.User message) {
        logger.info("Message received username "+message.getUserName());
        //userRepository.save(new User(message.getId(), message.getUserName(), message.getFirstName(), message.getLastName(), message.getPassword(), message.getActive(), message.getEmail()));
    }
}
