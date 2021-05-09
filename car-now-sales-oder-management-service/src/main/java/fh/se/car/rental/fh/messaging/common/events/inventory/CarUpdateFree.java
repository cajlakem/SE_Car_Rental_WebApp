package fh.se.car.rental.fh.messaging.common.events.inventory;

import fh.se.car.rental.fh.messaging.common.events.common.CarNowMessage;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class CarUpdateFree extends CarNowMessage implements Serializable {
    public String getCarId() {
        return carId;
    }

    public CarUpdateFree setCarId(String carId) {
        this.carId = carId;
        return this;
    }

    private String carId;
    private Long salesOrderId;

    public CarUpdateFree() {
    }

    public CarUpdateFree(String carId, Long salesOrderId) {
        this.carId = carId;
        this.salesOrderId = salesOrderId;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public CarUpdateFree setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
        return this;
    }
}
