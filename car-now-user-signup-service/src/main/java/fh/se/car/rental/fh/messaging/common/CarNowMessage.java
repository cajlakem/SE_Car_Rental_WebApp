package fh.se.car.rental.fh.messaging.common;

import java.io.Serializable;

public class CarNowMessage implements Serializable {
    private MessageType type;
    private Object payload;
    private String payloadClass;

    public CarNowMessage(MessageType type, Object payload, String payloadClass) {
        this.type = type;
        this.payload = payload;
        this.payloadClass = payloadClass;
    }


    public MessageType getType() {
        return type;
    }

    public CarNowMessage setType(MessageType type) {
        this.type = type;
        return this;
    }

    public Object getPayload() {
        return payload;
    }

    public CarNowMessage setPayload(Object payload) {
        this.payload = payload;
        return this;
    }

    public String getPayloadClass() {
        return payloadClass;
    }

    public CarNowMessage setPayloadClass(String payloadClass) {
        this.payloadClass = payloadClass;
        return this;
    }
}
