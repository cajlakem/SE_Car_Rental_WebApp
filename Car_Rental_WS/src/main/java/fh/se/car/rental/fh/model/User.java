package fh.se.car.rental.fh.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotNull(message = "Username cannot be null")
    private String userName;
    @Column(nullable = false)
    @NotNull(message = "Firstname cannot be null")
    private String firstName;
    @Column(nullable = false)
    @NotNull(message = "Lastname cannot be null")
    private String lastName;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    private List<Booking> bookings;
    private Boolean active;
    private String token;
    @Column(unique = true)
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email invalid")
    private String email;
    @Column(unique = true)
    private String mobile;

    public Boolean getActive() {
        return active;
    }

    public User setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public User setBookings(List<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    private String password;



    public User(Long id, String userName, String firstName, String lastName, String password, boolean active, String email) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.active = active;
        this.email = email;
    }

    public User() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    public boolean checkPassword(String password){
        return password.equals(this.password);
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public User setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }
}
