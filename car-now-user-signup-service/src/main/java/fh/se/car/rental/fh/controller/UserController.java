package fh.se.car.rental.fh.controller;
import fh.se.car.rental.fh.CarRentalRESTWebService;
import fh.se.car.rental.fh.exceptions.CredentialsWrong;
import fh.se.car.rental.fh.exceptions.UsernameAlreadyInUse;
import fh.se.car.rental.fh.messaging.common.CarNowMessage;
import fh.se.car.rental.fh.messaging.common.MessageType;
import fh.se.car.rental.fh.messaging.sender.Sender;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signupmanagementbackend/api/v1")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Sender sender;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/users/register")
    public User createUser(
            @Validated @RequestBody User newUser,
            @RequestHeader(name = "reenteredPassword") String reenteredPassword) {
        sender.sendMessage(CarRentalRESTWebService.topicExchangeName, "foo.bar.baz", new CarNowMessage(MessageType.SIGNUP_MSG, new fh.se.car.rental.fh.messaging.common.User(newUser.getId(), newUser.getUserName(), newUser.getFirstName(), newUser.getLastName(), newUser.getActive(), "", newUser.getEmail(), newUser.getMobile()), fh.se.car.rental.fh.messaging.common.User.class.getName()));

        if (
                reenteredPassword.isEmpty() || !newUser.getPassword().equals(reenteredPassword)
        ) throw new CredentialsWrong(
                "Check the password! " + reenteredPassword + " != " + newUser.getPassword()
        );
        logger.info("Adding user " + newUser.getUserName());
        if (userRepository.findByUserName(newUser.getUserName()) != null) {
            String msg = newUser.getUserName() + " already in use!";
            logger.error(msg);
            throw new UsernameAlreadyInUse(msg);
        }
        newUser.setPassword(reenteredPassword);
        return userRepository.save(newUser);
    }
}
