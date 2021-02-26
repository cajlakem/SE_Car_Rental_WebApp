package fh.se.car.rental.fh.controller;
import fh.se.car.rental.fh.exceptions.LoiginFail;
import fh.se.car.rental.fh.exceptions.RecordNotFoundException;
import fh.se.car.rental.fh.exceptions.UsernameAlreadyInUse;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;

    Logger logger;

    {
        logger = LoggerFactory.getLogger(UserController.class);
    }

    @GetMapping("/users")
    public List<User> list(){
        return userService.listAll();
    }

    @PostMapping("/user/register")
    public void add(@Validated @RequestBody User user){
        logger.info("Adding user "+user.getUserName());
        User dbUser = userService.findByUserName(user.getUserName());
        if(dbUser != null){
            String msg = user.getUserName()+" already in use!";
            logger.error(msg);
            throw new UsernameAlreadyInUse(msg);
        }
        user.setPassword("test");
        userService.save(user);
    }

    @PostMapping("/user/login")
    @CrossOrigin(origins = "http://localhost:8080")
    public User login(@RequestParam(value = "user", required = true) String username, @RequestParam(value = "password", required = true) String password){
        logger.info("Logging in "+username);
        User dbUser = userService.findByUserName(username);
        if(dbUser == null){
            logger.error("Failed to login "+username);
            throw  new RecordNotFoundException(username+" not found!");
        }
        if(!dbUser.checkPassword(password)){
            logger.error("Failed to login "+username);
            throw  new LoiginFail("Wrong credentials!");
        }
        String token = getJWTToken(username);
        dbUser.setToken(token);
        logger.info("Token created for "+username+" "+token);
        return dbUser;
    }

    private String getJWTToken(String username){
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        return "Bearer " + token;
    }
}
