package fh.se.car.rental.fh.repository;

import fh.se.car.rental.fh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
