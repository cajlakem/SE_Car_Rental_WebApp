package fh.se.car.rental.fh.service;

import fh.se.car.rental.fh.model.Car;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.repository.CarRepository;
import fh.se.car.rental.fh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> listAll(){
        return carRepository.findAll();
    }

    public void save(Car car){
        carRepository.save(car);
    }

    public Car get(Long id){
        return carRepository.findById(id).get();
    }

    public void delete(Long id){
        carRepository.deleteById(id);
    }


}
