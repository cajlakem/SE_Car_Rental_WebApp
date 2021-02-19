package fh.se.car.rental.fh.service;

import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listAll(){
        return userRepository.findAll();
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User get(Long id){
        return userRepository.findById(id).get();
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

}
