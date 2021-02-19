package fh.se.car.rental.fh.controller;

import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list(){
        return userService.listAll();
    }

    @PostMapping("/user")
    public void add(@RequestBody User user){
        userService.save(user);
    }
}
