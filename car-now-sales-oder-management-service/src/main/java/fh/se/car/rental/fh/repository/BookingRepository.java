package fh.se.car.rental.fh.repository;

import fh.se.car.rental.fh.model.Booking;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends CassandraRepository<Booking, Long> {
    Optional<List<Booking>> findByUserId(Long userId);
}
