package fh.se.car.rental.fh.controller;

import fh.se.car.rental.fh.currency.ws.client.CurrencyClient;
import fh.se.car.rental.fh.exceptions.CarLabelAlreadyInUse;
import fh.se.car.rental.fh.exceptions.CurrencyNotSet;
import fh.se.car.rental.fh.exceptions.RecordNotFoundException;
import fh.se.car.rental.fh.model.Booking;
import fh.se.car.rental.fh.model.enums.BookingState;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;
import fh.se.car.rental.fh.repository.BookingRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fh.se.car.rental.fh.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1")
public class BookingController {
  @Autowired
  private BookingRepository bookingService;

  @Autowired
  private CarRepository carRepository;

  @Autowired
  private CurrencyClient currencyClient;

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
  public void add(@Validated @RequestBody Booking booking) {
    logger.info("Adding booking " + booking.getId()+ " "+booking.getCar().getPrice());
    Optional<Booking> dbBooking = bookingService.findById(booking.getId());
    if (booking.getCurrency() == null) {
      throw new CurrencyNotSet("Currency not set!");
    }
    if (dbBooking.isPresent()) {
      String msg = booking.getId() + " already in use!";
      logger.error(msg);
      throw new CarLabelAlreadyInUse(msg);
    }

    booking.setPrice(
      currencyClient
        .convertCurrency(booking.getCurrency().name(), booking.getCar().getPrice())
        .getConvertResult()
    );
    booking.getCar().setStatus(CarState.INUSE);
    carRepository.save(booking.getCar());
    bookingService.save(booking);
  }

  @GetMapping("/bookings/user/{id}")
  public List<Booking> getUser(@PathVariable Long id) {
    System.out.println(bookingService.findByUserId(id).get());
    return bookingService
      .findByUserId(id)
      .orElseThrow(() -> new RecordNotFoundException(id.toString()));
  }
}
