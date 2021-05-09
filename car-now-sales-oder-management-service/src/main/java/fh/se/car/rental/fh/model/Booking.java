package fh.se.car.rental.fh.model;

import fh.se.car.rental.fh.model.enums.BookingState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;
import java.util.Date;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@Document("bookings")
public class Booking {
    @Id
    private Long id;

    private String remark;

    private String label;

    private Date startTime;

    private Date endTime;

    private Double price;

    private Car car;

    private User user;


    private BookingState status;

    private CurrencyCode currency;

    public Booking() {
    }

    public Booking(
            Long id,
            String remark,
            String label,
            Date startTime,
            Date endTime,
            Double price,
            Car car,
            User user,
            BookingState status,
            CurrencyCode currency
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
