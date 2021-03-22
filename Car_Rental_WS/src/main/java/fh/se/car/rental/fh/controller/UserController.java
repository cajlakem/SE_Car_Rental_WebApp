package fh.se.car.rental.fh.controller;
import fh.se.car.rental.fh.exceptions.LoiginFail;
import fh.se.car.rental.fh.exceptions.RecordNotFoundException;
import fh.se.car.rental.fh.exceptions.UsernameAlreadyInUse;
import fh.se.car.rental.fh.model.User;
import fh.se.car.rental.fh.repository.UserRepository;
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
public class UserController {
    @Autowired
    private UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/users/register")
    public User createUser(@Validated @RequestBody User newUser){
        logger.info("Adding user "+newUser.getUserName());
        if(userRepository.findByUserName(newUser.getUserName()) != null){
            String msg = newUser.getUserName()+" already in use!";
            logger.error(msg);
            throw new UsernameAlreadyInUse(msg);
        }
        //TODO: I know you can do it better!
        newUser.setPassword("test");
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    public List<User> queryAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id.toString()));
    }

    @PutMapping("/users/{id}")
    User modifyUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(newUser.getEmail());
                    user.setMobile(newUser.getMobile());
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }


    @PostMapping("/users/login")
    public String login(@RequestParam(value = "user", required = true) String username, @RequestParam(value = "password", required = true) String password){
        logger.info("Logging in "+username);
        User dbUser = userRepository.findByUserName(username);
        if(dbUser == null){
            logger.error("Failed to login "+username);
            throw  new RecordNotFoundException(username+" not found!");
        }
        if(!dbUser.checkPassword(password)){
            logger.error("Failed to login "+username);
            throw  new LoiginFail("Wrong credentials!");
        }
        String token = getJWTToken(username);
        logger.info("Token created for "+username+" "+token);
        return token;
    }

    private String getJWTToken(String username){
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("SYSTEM_ADMIN");
        String token = Jwts
                .builder()
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
