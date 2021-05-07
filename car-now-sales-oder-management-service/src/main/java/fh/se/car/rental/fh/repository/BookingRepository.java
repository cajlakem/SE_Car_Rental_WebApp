package fh.se.car.rental.fh.repository;

import fh.se.car.rental.fh.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking, Long> {
    Optional<List<Booking>> findByUserId(Long userId);
}
