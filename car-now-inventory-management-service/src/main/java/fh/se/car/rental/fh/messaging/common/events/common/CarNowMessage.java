package fh.se.car.rental.fh.messaging.common.events.common;

import fh.se.car.rental.fh.messaging.common.enums.MessageType;

import java.io.Serializable;
import java.util.Date;

public class CarNowMessage implements Serializable {
    private MessageType type;
    private Date creationDate;



    public MessageType getType() {
        return type;
    }

    public CarNowMessage setType(MessageType type) {
        this.type = type;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public CarNowMessage setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }
}
