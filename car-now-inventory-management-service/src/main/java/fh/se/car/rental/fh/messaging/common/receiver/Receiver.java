package fh.se.car.rental.fh.messaging.common.receiver;

import fh.se.car.rental.fh.messaging.common.config.MessagingConfig;
import fh.se.car.rental.fh.messaging.common.enums.ResponseState;
import fh.se.car.rental.fh.messaging.common.events.inventory.CarUpdate;
import fh.se.car.rental.fh.messaging.common.events.inventory.CarUpdateFree;
import fh.se.car.rental.fh.messaging.common.events.salesorder.BookingCarAvailabilityUpdate;
import fh.se.car.rental.fh.messaging.common.sender.Sender;
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

    @Autowired
    Sender sender;

    @RabbitListener(queues = MessagingConfig.CAR_UPDATE)
    public void receiveMessage(final CarUpdate carUpdate) {
        logger.info("Checking car availability "+carUpdate.toString());
        try{

            BookingCarAvailabilityUpdate availabilityUpdate = new BookingCarAvailabilityUpdate();
            availabilityUpdate.setId(carUpdate.getSalesOrderId());
            Optional<Car> car = carRepository.findByLicensePlate(carUpdate.getCarId());
            logger.info("DB Car"+car.get().getLicensePlate());
            if(car.isPresent()){
                car.get().setStatus(CarState.INUSE);
                carRepository.save(car.get());
                availabilityUpdate.setState(ResponseState.OK);
            }else{
                availabilityUpdate.setState(ResponseState.NOK);
            }
            logger.info("Car state "+car.get().getLicensePlate() +" "+car.get().getStatus());
            sender.sendMessage(MessagingConfig.EXCHANGE_NAME, MessagingConfig.SO_UPDATE, availabilityUpdate);
        }catch (Exception exception){
            logger.error(exception.getMessage());
        }
    }

    @RabbitListener(queues = MessagingConfig.CAR_UPDATE_FREE)
    public void receiveMessage(final CarUpdateFree carUpdate) {
        logger.info("Setting car free  "+carUpdate.toString());
        try{
            Optional<Car> car = carRepository.findByLicensePlate(carUpdate.getCarId());
            logger.info("DB Car"+car.get().getLicensePlate());
            if(car.isPresent()){
                car.get().setStatus(CarState.FREE);
                carRepository.save(car.get());
            }else{
                logger.info("Not ablke to free car ");
            }
            logger.info("Car state "+car.get().getLicensePlate() +" "+car.get().getStatus());
        }catch (Exception exception){
            logger.error(exception.getMessage());
        }
    }
}
