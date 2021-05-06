package fh.se.car.rental.fh.controller;


import fh.se.car.rental.fh.controller.helper.JwtResponse;
import fh.se.car.rental.fh.exceptions.LoiginFail;
import fh.se.car.rental.fh.exceptions.RecordNotFoundException;
import fh.se.car.rental.fh.messaging.common.MySeverity;
import fh.se.car.rental.fh.messaging.common.sender.Sender;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/userauthorizationtbackend/api/v1")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Sender sender;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Cacheable(value = "users")
    @PostMapping("/users/login")
    public JwtResponse login(
            @RequestHeader(name = "username", required = true) String username,
            @RequestHeader(name = "password", required = true) String password
    ) {
        String msg = "Logging in " + username;
        logger.info(msg);
        sender.sendLogMessage(msg, MySeverity.INFO);
        Optional<User> dbUser = userRepository.findById(username);

        if (dbUser.isEmpty()) {
            msg ="Failed to login " + username;
            logger.error(msg);
            sender.sendLogMessage(msg, MySeverity.ERROR);
            throw new RecordNotFoundException(username + " not found!");
        }

        if (!dbUser.get().checkPassword(password)) {
            msg ="Failed to login " + username;
            logger.error(msg);
            sender.sendLogMessage(msg, MySeverity.ERROR);
            throw new LoiginFail("Wrong credentials!");
        }
        JwtResponse token = getJWTToken(dbUser.get());
        msg = "Token created for " + username + " " + token.getAccessToken();
        logger.info(msg);
        sender.sendLogMessage(msg, MySeverity.INFO);
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
