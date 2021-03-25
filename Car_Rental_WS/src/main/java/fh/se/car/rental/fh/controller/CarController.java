package fh.se.car.rental.fh.controller;
import fh.se.car.rental.fh.exceptions.CarLabelAlreadyInUse;
import fh.se.car.rental.fh.model.Booking;
import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;
import fh.se.car.rental.fh.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/v1")
public class CarController {
    @Autowired
    private CarRepository carService;

    Logger logger = LoggerFactory.getLogger(CarController.class);

    @GetMapping("/cars/findByState")
    public List<Car> list(@RequestParam(required = true) CarState state, @RequestParam(required = true) CurrencyCode currency){
        logger.info("Querying cars with "+state+ " "+currency.name());
        List<Car>  cars = carService.findAll();
        List<Car> result = new ArrayList<>();
        for (Car car:cars) {
            logger.info(car.getStatus().toString());
            if(car.getStatus() == state){
                car.setCurrency(currency);
                result.add(car);
            }
        }
        return result;
    }


    @GetMapping("/cars")
    public List<Car> list(){
        return carService.findAll();
    }

    @PostMapping("/car")
    public void add(@Validated @RequestBody Car car){
        logger.info("Adding car "+car.getLabel());
        Optional<Car> dbCar = carService.findById(car.getId());
        if(dbCar != null){
            String msg = car.getLabel()+" already in use!";
            logger.error(msg);
            throw new CarLabelAlreadyInUse(msg);
        }
        carService.save(car);
    }
}
