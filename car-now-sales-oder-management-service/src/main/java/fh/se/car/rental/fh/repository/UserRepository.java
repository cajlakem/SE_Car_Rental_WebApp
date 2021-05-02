package fh.se.car.rental.fh.repository;

import fh.se.car.rental.fh.model.User;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserRepository extends CassandraRepository<User, Long> {
    User findByUserName(String username);
}
