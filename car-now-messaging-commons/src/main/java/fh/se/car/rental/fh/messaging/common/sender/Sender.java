package fh.se.car.rental.fh.messaging.common.sender;
import fh.se.car.rental.fh.messaging.common.receiver.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Receiver receiver;

    Logger logger = LoggerFactory.getLogger(Sender.class);

    public void sendMessage(String topicExchangeName, String queue, Object message){
        logger.info("Sending message...");
        rabbitTemplate.convertAndSend(topicExchangeName, queue, message);
    }
}
