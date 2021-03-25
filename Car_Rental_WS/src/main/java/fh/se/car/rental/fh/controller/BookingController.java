package fh.se.car.rental.fh.controller;

import fh.se.car.rental.fh.exceptions.CarLabelAlreadyInUse;
import fh.se.car.rental.fh.model.Booking;
import fh.se.car.rental.fh.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/v1")
public class BookingController {
    @Autowired
    private BookingRepository bookingService;

    Logger logger = LoggerFactory.getLogger(BookingController.class);

    @GetMapping("/bookings")
    public List<Booking> list(){
        return bookingService.findAll();
    }

    @PostMapping("/booking")
    public void add(@Validated @RequestBody Booking booking){
        logger.info("Adding booking "+booking.getId());
        Optional<Booking> dbBooking = bookingService.findById(booking.getId());
        if(dbBooking != null){
            String msg = booking.getId()+" already in use!";
            logger.error(msg);
            throw new CarLabelAlreadyInUse(msg);
        }
        bookingService.save(booking);
    }

}
