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
@RequestMapping("/salesordermanagementbackend/api/v1")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/users/register")
    public User createUser(
            @Validated @RequestBody User newUser,
            @RequestHeader(name = "reenteredPassword") String reenteredPassword
    ) {
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

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users/login")
    public JwtResponse login(
            @RequestHeader(name = "username", required = true) String username,
            @RequestHeader(name = "password", required = true) String password
    ) {
        logger.info("Logging in " + username);
        User dbUser = userRepository.findByUserName(username);
        if (dbUser == null) {
            logger.error("Failed to login " + username);
            throw new RecordNotFoundException(username + " not found!");
        }
        if (!dbUser.checkPassword(password)) {
            logger.error("Failed to login " + username);
            throw new LoiginFail("Wrong credentials!");
        }
        JwtResponse token = getJWTToken(dbUser);
        logger.info("Token created for " + username + " " + token.getAccessToken());
        return token;
    }

    private JwtResponse getJWTToken(User dbUser) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(
                "SYSTEM_ADMIN"
        );
        String token = Jwts
                .builder()
                .setSubject(dbUser.getUserName())
                .claim(
                        "authorities",
                        grantedAuthorities
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60000000))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes())
                .compact();

        return new JwtResponse(
                token,
                dbUser.getId(),
                dbUser.getUserName(),
                dbUser.getEmail(),
                Arrays.asList("ADMIN", "MODERATOR", "USER")
        );
    }
}
