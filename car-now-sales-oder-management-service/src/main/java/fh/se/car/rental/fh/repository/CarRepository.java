package fh.se.car.rental.fh.repository;

import fh.se.car.rental.fh.model.Car;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface CarRepository extends CassandraRepository<Car, Long> {
}
