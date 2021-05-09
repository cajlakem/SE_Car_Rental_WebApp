package fh.se.car.rental.fh.repository;

import fh.se.car.rental.fh.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CarRepository extends MongoRepository<Car, String> {
    Optional<Car> findByLicensePlate(String licensePlate);
}
