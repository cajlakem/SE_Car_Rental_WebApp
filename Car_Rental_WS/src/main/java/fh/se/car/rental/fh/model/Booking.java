package fh.se.car.rental.fh.model;

import org.springframework.data.annotation.Reference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "bookingss")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String remark;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    private Float price;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Car car;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public Booking() {

    }

    public Booking(Long id, String remark, Date startTime, Date endTime, Float price, Car car, User user) {
        this.id = id;
        this.remark = remark;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.car = car;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Booking setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Booking setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Float getPrice() {
        return price;
    }

    public Booking setPrice(Float price) {
        this.price = price;
        return this;
    }

    public Car getCar() {
        return car;
    }

    public Booking setCar(Car car) {
        this.car = car;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Booking setUser(User user) {
        this.user = user;
        return this;
    }
}
