package fh.se.car.rental.fh.messaging.common.receiver;

import fh.se.car.rental.fh.messaging.common.config.MessagingConfig;
import fh.se.car.rental.fh.messaging.common.enums.ResponseState;
import fh.se.car.rental.fh.messaging.common.events.inventory.CarUpdate;
import fh.se.car.rental.fh.messaging.common.events.salesorder.BookingCarAvailabilityUpdate;
import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Receiver {

    Logger logger = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    CarRepository carRepository;

    @RabbitListener(queues = MessagingConfig.SALES_ORDER_UPDATE)
    public void receiveMessage(final CarUpdate carUpdate) {
        logger.info("Checking car availability "+carUpdate.getCarId());
        BookingCarAvailabilityUpdate availabilityUpdate = new BookingCarAvailabilityUpdate();
        availabilityUpdate.setId(carUpdate.getSalesOrderId());
        Optional<Car> car = carRepository.findById(carUpdate.getCarId());
        if(car.isPresent()){
            car.get().setStatus(CarState.INUSE);
            carRepository.save(car.get());
            availabilityUpdate.setState(ResponseState.OK);
        }else
            availabilityUpdate.setState(ResponseState.NOK);
   }
}
