package fh.se.car.rental.fh.repository;

import fh.se.car.rental.fh.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByUserName(String username);
}
