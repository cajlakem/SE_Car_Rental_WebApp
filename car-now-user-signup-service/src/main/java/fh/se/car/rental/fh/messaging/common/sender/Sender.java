package fh.se.car.rental.fh.messaging.common.sender;
import fh.se.car.rental.fh.messaging.common.CarNowMessage;
import fh.se.car.rental.fh.messaging.common.LogEntry;
import fh.se.car.rental.fh.messaging.common.MessageType;
import fh.se.car.rental.fh.messaging.common.MySeverity;
import fh.se.car.rental.fh.messaging.common.config.MessagingConfig;
import fh.se.car.rental.fh.messaging.common.receiver.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${application.name}")
    private String serviceName;

    Logger logger = LoggerFactory.getLogger(Sender.class);

    public void sendMessage(String topicExchangeName, String queue, Object message){
        logger.info("Sending message...");
        rabbitTemplate.convertAndSend(topicExchangeName, queue, message);
    }

    public void sendLogMessage(String logMessage, MySeverity severity){
        //logger.info("Sending log message "+logMessage);
        LogEntry logEntry = new LogEntry();
        logEntry.setType(MessageType.LOG_MSG);
        logEntry.setCreationDate(new Date());
        logEntry.setSeverity(severity);
        logEntry.setMicroserviceName(serviceName);
        logEntry.setMessage(logMessage);
        //rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE_NAME, MessagingConfig.LOGGING_QUEUE, logEntry);
    }
}
