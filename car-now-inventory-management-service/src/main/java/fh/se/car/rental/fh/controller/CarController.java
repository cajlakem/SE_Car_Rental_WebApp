package fh.se.car.rental.fh.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fh.se.car.rental.fh.currency.ws.client.CurrencyClient;
import fh.se.car.rental.fh.exceptions.CarLabelAlreadyInUse;
import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;
import fh.se.car.rental.fh.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/inventorymanagementbackend/api/v1")
public class CarController {
    @Autowired
    private CarRepository carService;

    @Autowired
    private CurrencyClient currencyClient;

    Logger logger = LoggerFactory.getLogger(CarController.class);

    @GetMapping("/cars/findByState")
    public List<Car> list(
            @RequestParam(required = true) CarState state,
            @RequestParam(required = true) CurrencyCode currency
    ) {
        logger.info("Querying cars with " + state + " " + currency.name());
        List<Car> cars = carService.findAll();
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getStatus() == state) {
                Double price = currencyClient
                        .convertCurrency(currency.name(), car.getPrice())
                        .getConvertResult();
                car.setCurrency(currency);
                car.setPrice(price);
                result.add(car);
            }
        }
        return result;
    }

    @PostMapping("/car")
    public void add(@Validated @RequestBody Car car) {
        logger.info("Adding car " + car.getLabel());
        Optional<Car> dbCar = carService.findById(car.getId());
        if (dbCar != null) {
            String msg = car.getLabel() + " already in use!";
            logger.error(msg);
            throw new CarLabelAlreadyInUse(msg);
        }
        carService.save(car);
    }
}
