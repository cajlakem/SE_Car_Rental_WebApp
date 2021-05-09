package fh.se.car.rental.fh.messaging.common.events.inventory;

import fh.se.car.rental.fh.messaging.common.events.common.CarNowMessage;

import java.io.Serializable;

public class CarUpdate extends CarNowMessage implements Serializable {
    private Long carId;
    private Long salesOrderId;


    public CarUpdate(Long carId, Long salesOrderId) {
        this.carId = carId;
        this.salesOrderId = salesOrderId;
    }

    public Long getCarId() {
        return carId;
    }

    public CarUpdate setCarId(Long carId) {
        this.carId = carId;
        return this;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public CarUpdate setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
        return this;
    }
}
