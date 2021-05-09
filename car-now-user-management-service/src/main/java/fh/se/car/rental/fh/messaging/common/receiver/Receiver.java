package fh.se.car.rental.fh.messaging.common.receiver;

import fh.se.car.rental.fh.messaging.common.config.MessagingConfig;
import fh.se.car.rental.fh.messaging.common.enums.ResponseState;
import fh.se.car.rental.fh.messaging.common.events.inventory.CarUpdate;
import fh.se.car.rental.fh.messaging.common.events.salesorder.BookingCarAvailabilityUpdate;
import fh.se.car.rental.fh.messaging.common.events.user.UserUpdate;
import fh.se.car.rental.fh.messaging.common.events.user.UserUpdateResponse;
import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Receiver {

    Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    UserRepository userRepository;

    @RabbitListener(queues = MessagingConfig.SIGN_UP_2_USER_MANAGEMENT)
    public void receiveMessage(final UserUpdate userUpdate) {
        logger.info("Creating new user for CRM server "+userUpdate.getUserName());
        UserUpdateResponse response = new UserUpdateResponse();
        response.setState(ResponseState.OK);
        try{
            User user = new User();
            user.setUserName(userUpdate.getUserName());
            user.setPassword(userUpdate.getPassword());
            user.setFirstName(userUpdate.getFirstName());
            user.setLastName(userUpdate.getLastName());
            user.setMobile(userUpdate.getMobile());
            user.setEmail(userUpdate.getEmail());
            user.setActive(true);
            user.setId(System.currentTimeMillis());
            userRepository.save(user);
        }catch (Exception exception){
            logger.error("Failed to create user! "+exception.getMessage() );
            response.setState(ResponseState.NOK);
        }

   }
}



