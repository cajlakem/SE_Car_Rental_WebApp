package fh.se.car.rental.fh.controller;

import fh.se.car.rental.fh.exceptions.CarLabelAlreadyInUse;
import fh.se.car.rental.fh.model.Booking;
import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.service.BookingService;
import fh.se.car.rental.fh.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
public class BookingController {
    @Autowired
    private BookingService bookingService;

    Logger logger;

    {
        logger = LoggerFactory.getLogger(BookingController.class);
    }

    @GetMapping("/bookings")
    public List<Booking> list(){
        return bookingService.listAll();
    }

    @PostMapping("/booking")
    public void add(@Validated @RequestBody Booking booking){
        logger.info("Adding booking "+booking.getId());
        Booking dbBooking = bookingService.get(booking.getId());
        if(dbBooking != null){
            String msg = booking.getId()+" already in use!";
            logger.error(msg);
            throw new CarLabelAlreadyInUse(msg);
        }
        bookingService.save(booking);
    }

}
