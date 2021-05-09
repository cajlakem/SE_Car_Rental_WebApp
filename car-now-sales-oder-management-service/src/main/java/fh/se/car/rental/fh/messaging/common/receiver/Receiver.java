package fh.se.car.rental.fh.messaging.common.receiver;

import fh.se.car.rental.fh.messaging.common.config.MessagingConfig;
import fh.se.car.rental.fh.messaging.common.events.salesorder.BookingCarAvailabilityUpdate;
import fh.se.car.rental.fh.model.Booking;
import fh.se.car.rental.fh.model.enums.BookingState;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.repository.BookingRepository;
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
    BookingRepository bookingRepository;

    @RabbitListener(queues = MessagingConfig.SALES_ORDER_UPDATE)
    public void receiveMessage(final BookingCarAvailabilityUpdate update) {
        logger.info("Sales order update "+update.toString());
        Optional<Booking> booking = bookingRepository.findById(update.getId());
        if(booking.isPresent()){
            booking.get().setStatus(BookingState.BOOKED);
            booking.get().getCar().setStatus(CarState.INUSE);
            bookingRepository.save(booking.get());
            logger.info("Setting sales order "+booking.get().getId()+" to BOOKED");
        }else{
            logger.info("Failed to book sales order "+booking.get().getId()+", Car not available");
        }
   }
}
