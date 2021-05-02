package fh.se.car.rental.fh.repository;

import fh.se.car.rental.fh.model.Booking;
import fh.se.car.rental.fh.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<List<Booking>> findByUserId(Long userId);
}
