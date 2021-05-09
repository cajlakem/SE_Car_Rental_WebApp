package fh.se.car.rental.fh.messaging.common.events.salesorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import fh.se.car.rental.fh.messaging.common.enums.ResponseState;
import fh.se.car.rental.fh.messaging.common.events.common.CarNowMessage;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class BookingCarAvailabilityUpdate extends CarNowMessage implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("state")
    private ResponseState state;

    public BookingCarAvailabilityUpdate(){

    }


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
