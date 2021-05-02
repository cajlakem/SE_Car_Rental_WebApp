package fh.se.car.rental.fh.repository;

import fh.se.car.rental.fh.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
