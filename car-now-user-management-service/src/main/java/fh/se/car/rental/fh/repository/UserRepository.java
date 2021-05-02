package fh.se.car.rental.fh.repository;

import fh.se.car.rental.fh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);
}
