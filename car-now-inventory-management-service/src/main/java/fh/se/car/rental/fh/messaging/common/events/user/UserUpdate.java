package fh.se.car.rental.fh.messaging.common.events.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import fh.se.car.rental.fh.messaging.common.events.common.CarNowMessage;

import java.io.Serializable;

public class UserUpdate extends CarNowMessage implements Serializable {
    @JsonProperty("id")
    private String id;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("token")
    private String token;
    @JsonProperty("email")
    private String email;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("password")
    private String password;

    public UserUpdate(){

    }

    public UserUpdate(String id, String userName, String firstName, String lastName, Boolean active, String token, String email, String mobile) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.token = token;
        this.email = email;
        this.mobile = mobile;
    }


    public String getId() {
        return id;
    }

    public UserUpdate setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserUpdate setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserUpdate setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserUpdate setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public UserUpdate setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public String getToken() {
        return token;
    }

    public UserUpdate setToken(String token) {
        this.token = token;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserUpdate setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public UserUpdate setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserUpdate setPassword(String password) {
        this.password = password;
        return this;
    }
}
