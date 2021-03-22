package fh.se.car.rental.fh.controller;
import fh.se.car.rental.fh.exceptions.CarLabelAlreadyInUse;
import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarController {
    @Autowired
    private CarRepository carService;

    Logger logger;

    {
        logger = LoggerFactory.getLogger(CarController.class);
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
