package fh.se.car.rental.fh.repository;

import fh.se.car.rental.fh.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<Car, Long> {
}
