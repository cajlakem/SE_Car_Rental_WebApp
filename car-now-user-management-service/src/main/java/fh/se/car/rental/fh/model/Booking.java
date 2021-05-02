package fh.se.car.rental.fh.model;

import fh.se.car.rental.fh.model.enums.BookingState;
import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Reference;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String remark;

    @NotNull(message = "Label cannot be null")
    private String label;

    @NotNull(message = "StartTime cannot be null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @NotNull(message = "Price cannot be null")
    private Double price;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    @NotNull(message = "Car cannot be null")
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    @NotNull(message = "User cannot be null")
    private User user;

    @NotNull(message = "state cannot be null")
    @Enumerated(EnumType.ORDINAL)
    private BookingState status;

    @NotNull(message = "currency cannot be null")
    @Enumerated(EnumType.ORDINAL)
    private CurrencyCode currency;

    public Booking() {
    }

    public Booking(
            Long id,
            String remark,
            @NotNull(message = "Label cannot be null") String label,
            @NotNull(message = "StartTime cannot be null") Date startTime,
            Date endTime,
            @NotNull(message = "Price cannot be null") Double price,
            @NotNull(message = "Car cannot be null") Car car,
            @NotNull(message = "User cannot be null") User user,
            @NotNull(message = "state cannot be null") BookingState status,
            @NotNull(message = "currency cannot be null") CurrencyCode currency
    ) {
        this.id = id;
        this.remark = remark;
        this.label = label;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.car = car;
        this.user = user;
        this.status = status;
        this.currency = currency;
    }

    public BookingState getStatus() {
        return status;
    }

    public Booking setStatus(BookingState status) {
        this.status = status;
        return this;
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

    public Double getPrice() {
        return price;
    }

    public Booking setPrice(Double price) {
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

    public String getLabel() {
        return label;
    }

    public Booking setLabel(String label) {
        this.label = label;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Booking setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Booking setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public Booking setCurrency(CurrencyCode currency) {
        this.currency = currency;
        return this;
    }
}
