package fh.se.car.rental.fh.controller;

import fh.se.car.rental.fh.currency.ws.client.CurrencyClient;
import fh.se.car.rental.fh.exceptions.CarLabelAlreadyInUse;
import fh.se.car.rental.fh.exceptions.CurrencyNotSet;
import fh.se.car.rental.fh.exceptions.RecordNotFoundException;
import fh.se.car.rental.fh.messaging.common.config.MessagingConfig;
import fh.se.car.rental.fh.messaging.common.enums.MySeverity;
import fh.se.car.rental.fh.messaging.common.events.inventory.CarUpdate;
import fh.se.car.rental.fh.messaging.common.events.inventory.CarUpdateFree;
import fh.se.car.rental.fh.messaging.common.sender.Sender;
import fh.se.car.rental.fh.model.Booking;
import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.model.enums.BookingState;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;
import fh.se.car.rental.fh.repository.BookingRepository;
import fh.se.car.rental.fh.repository.CarRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@Validated
@RequestMapping("/salesordermanagementbackend/api/v1")
public class BookingController {
    @Autowired
    private BookingRepository bookingService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CurrencyClient currencyClient;

    @Autowired
    private Sender sender;

    Logger logger = LoggerFactory.getLogger(BookingController.class);

    @GetMapping("/bookings")
    public List<Booking> list(@RequestParam(required = true) CurrencyCode currency) {
        List<Booking> bookings = bookingService.findAll();
        for (Booking booking : bookings) {
            booking.setPrice(
                    currencyClient
                            .convertCurrency(currency.name(), booking.getPrice())
                            .getConvertResult()
            );
        }
        return bookings;
    }

    @GetMapping("/currencyCodes")
    public Enum<CurrencyCode>[] getCurrencyCodes() {
        return CurrencyCode.values();
    }

    @GetMapping("/bookings/findByState")
    public List<Booking> list(
            @RequestParam(required = true) BookingState state,
            @RequestParam(required = true) CurrencyCode currency
    ) {
        logger.info("Querying bookings with " + state + " " + currency);
        List<Booking> bookings = bookingService.findAll();
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getStatus() == state) {
                result.add(booking);
                booking.setStatus(state);
                Double price = currencyClient
                        .convertCurrency(currency.name(), booking.getPrice())
                        .getConvertResult();
                booking.setPrice(price);
            }
        }
        return result;
    }

    @PostMapping("/booking")
    public void add(@RequestBody Booking booking, Errors errors) {
            logger.info("Adding booking " + booking.getId() + " " + booking.getCar().getPrice());
            Optional<Car> car = carRepository.findByLicensePlate(booking.getCar().getLicensePlate());

            if (booking.getCurrency() == null) {
                throw new CurrencyNotSet("Currency not set!");
            }
            if (car.isPresent()) {
                if (!car.get().getStatus().name().equals(CarState.FREE.name())) {
                    String msg = booking.getId() + " already in use!";
                    logger.error(msg);
                    throw new CarLabelAlreadyInUse(msg);
                }
            }

            booking.setPrice(
                    currencyClient
                            .convertCurrency(booking.getCurrency().name(), booking.getCar().getPrice())
                            .getConvertResult()
            );


            booking.setLabel("");
            booking.setRemark("");
            booking.setEndTime(null);
            booking.getCar().setStatus(CarState.INUSE);
            long leftLimit = 1L;
            long rightLimit = 10000000L;
            long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
            booking.setId(generatedLong);
            booking.setStatus(BookingState.IN_PROGRESS);

            carRepository.save(booking.getCar());
            Booking dbBooking = bookingService.save(booking);
            logger.info(dbBooking.toString());
            CarUpdate carUpdate = new CarUpdate(booking.getCar().getLicensePlate(), dbBooking.getId());
            sender.sendMessage(MessagingConfig.EXCHANGE_NAME, MessagingConfig.CAR_UPDATE_KEY, carUpdate);
            sender.sendLogMessage("A new car update ("+booking.getCar().getLicensePlate()+"!", MySeverity.INFO);
    }

    @PostMapping("/booking/return")
    public void returnCar(@Validated @RequestBody Booking booking) {
        logger.info("Return car " + booking.getId());
        Optional<Booking> repositoryBooking = bookingService.findById(booking.getId());

        if (booking.getEndTime() == null) {
            throw new CurrencyNotSet("End Time not set!");
        }
        if (repositoryBooking.isEmpty()) {
            throw new RecordNotFoundException("Booking entry not found!");
        }

        Booking carBooking = repositoryBooking.get();
        carBooking.setEndTime(booking.getEndTime());
        carBooking.setStatus(BookingState.CLOSED);
        carBooking.getCar().setStatus(CarState.FREE);

        carRepository.save(carBooking.getCar());
        bookingService.save(carBooking);
        CarUpdateFree carUpdateFree = new CarUpdateFree(booking.getCar().getLicensePlate(), carBooking.getId());
        logger.info("Returning car "+carUpdateFree.toString());
        sender.sendMessage(MessagingConfig.EXCHANGE_NAME, MessagingConfig.CAR_UPDATE_FREE_KEY, carUpdateFree);
    }

    @GetMapping("/bookings/user/{id}")
    public List<Booking> getUser(@PathVariable String id) {
        logger.info("Getting bookings from " + id);
        return bookingService.findByUserId(id).orElseThrow(() -> new RecordNotFoundException(id.toString()));
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
