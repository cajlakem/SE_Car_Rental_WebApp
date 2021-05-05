package fh.se.car.rental.fh.messaging.common.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    Logger logger = LoggerFactory.getLogger(Receiver.class);

    public void receiveMessage(Object message){
        logger.info("Receiving message: "+message.toString());
    }
}
