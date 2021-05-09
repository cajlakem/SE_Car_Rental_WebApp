package fh.se.car.rental.fh.messaging.common.events.user;

import fh.se.car.rental.fh.messaging.common.events.common.CarNowMessage;
import fh.se.car.rental.fh.messaging.common.enums.ResponseState;

import java.io.Serializable;

public class UserUpdateResponse extends CarNowMessage implements Serializable {
    private ResponseState state;
    private String id;

    public ResponseState getState() {
        return state;
    }

    public UserUpdateResponse setState(ResponseState state) {
        this.state = state;
        return this;
    }

    public String getId() {
        return id;
    }

    public UserUpdateResponse setId(String id) {
        this.id = id;
        return this;
    }
}
