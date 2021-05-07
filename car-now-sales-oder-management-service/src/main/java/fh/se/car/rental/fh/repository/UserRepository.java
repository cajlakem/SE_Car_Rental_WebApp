package fh.se.car.rental.fh.repository;

import fh.se.car.rental.fh.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
    User findByUserName(String username);
}
