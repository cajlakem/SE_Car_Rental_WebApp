package fh.se.car.rental.fh.messaging.common.events.salesorder;

import fh.se.car.rental.fh.messaging.common.enums.ResponseState;
import fh.se.car.rental.fh.messaging.common.events.common.CarNowMessage;

import java.io.Serializable;

public class BookingCarAvailabilityUpdate extends CarNowMessage implements Serializable {
    private Long id;
    private ResponseState state;


    public Long getId() {
        return id;
    }

    public BookingCarAvailabilityUpdate setId(Long id) {
        this.id = id;
        return this;
    }

    public ResponseState getState() {
        return state;
    }

    public BookingCarAvailabilityUpdate setState(ResponseState state) {
        this.state = state;
        return this;
    }
}
