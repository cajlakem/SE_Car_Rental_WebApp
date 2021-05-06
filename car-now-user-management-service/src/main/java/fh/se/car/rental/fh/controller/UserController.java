package fh.se.car.rental.fh.controller;

import fh.se.car.rental.fh.controller.helper.JwtResponse;
import fh.se.car.rental.fh.exceptions.CredentialsWrong;
import fh.se.car.rental.fh.exceptions.LoiginFail;
import fh.se.car.rental.fh.exceptions.RecordNotFoundException;
import fh.se.car.rental.fh.exceptions.UsernameAlreadyInUse;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usermanagementbackend/api/v1")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public List<User> queryAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id.toString()));
    }

    @PutMapping("/users/{id}")
    public User modifyUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository
                .findById(id)
                .map(
                        user -> {
                            user.setEmail(newUser.getEmail());
                            user.setMobile(newUser.getMobile());
                            return userRepository.save(user);
                        }
                )
                .orElseGet(
                        () -> {
                            newUser.setId(id);
                            return userRepository.save(newUser);
                        }
                );
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
