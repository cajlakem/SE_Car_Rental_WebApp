package fh.se.car.rental.fh.controller;
import fh.se.car.rental.fh.exceptions.CredentialsWrong;
import fh.se.car.rental.fh.exceptions.UsernameAlreadyInUse;
import fh.se.car.rental.fh.messaging.common.MySeverity;
import fh.se.car.rental.fh.messaging.common.config.MessagingConfig;
import fh.se.car.rental.fh.messaging.common.sender.Sender;
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
        if (
                reenteredPassword.isEmpty() || !newUser.getPassword().equals(reenteredPassword)
        ) throw new CredentialsWrong(
                "Check the password! " + reenteredPassword + " != " + newUser.getPassword()
        );
        String msg = "Adding user " + newUser.getUserName();
        logger.info(msg);
        sender.sendLogMessage(msg, MySeverity.INFO);
        if (userRepository.findByUserName(newUser.getUserName()) != null) {
            msg = newUser.getUserName() + " already in use!";
            logger.error(msg);
            sender.sendLogMessage(msg, MySeverity.ERROR);
            throw new UsernameAlreadyInUse(msg);
        }
        newUser.setPassword(reenteredPassword);
        fh.se.car.rental.fh.messaging.common.User userMessage = new fh.se.car.rental.fh.messaging.common.User();
        //userMessage.setType(MessageType.SIGNUP_MSG);
        //userMessage.setCreationDate(new Date());
        userMessage.setUserName(newUser.getUserName());
        userMessage.setId(newUser.getId());
        userMessage.setFirstName(newUser.getFirstName());
        userMessage.setLastName(newUser.getLastName());
        userMessage.setActive(newUser.getActive());
        userMessage.setEmail(newUser.getEmail());
        userMessage.setMobile(newUser.getMobile());
        userMessage.setPassword(reenteredPassword);
        sender.sendMessage(MessagingConfig.EXCHANGE_NAME, MessagingConfig.USERS, userMessage);
        sender.sendLogMessage("A new User ("+newUser.getUserName()+") signed up!", MySeverity.INFO);
        return userRepository.save(newUser);
    }
}
