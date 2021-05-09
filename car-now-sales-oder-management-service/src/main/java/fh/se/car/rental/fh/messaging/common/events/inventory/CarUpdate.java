package fh.se.car.rental.fh.messaging.common.events.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import fh.se.car.rental.fh.messaging.common.events.common.CarNowMessage;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class CarUpdate extends CarNowMessage implements Serializable {
    public String getCarId() {
        return carId;
    }

    public CarUpdate setCarId(String carId) {
        this.carId = carId;
        return this;
    }

    @JsonProperty("carId")
    private String carId;
    @JsonProperty("salesOrderId")
    private Long salesOrderId;

    public CarUpdate() {
    }

    public CarUpdate(String carId, Long salesOrderId) {
        this.carId = carId;
        this.salesOrderId = salesOrderId;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public CarUpdate setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
        return this;
    }
}
