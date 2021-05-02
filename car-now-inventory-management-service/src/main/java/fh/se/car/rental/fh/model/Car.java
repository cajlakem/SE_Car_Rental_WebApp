package fh.se.car.rental.fh.model;

import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;

import javax.persistence.*;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String label;

    private String vendor;

    private Double price;

    private String licensePlate;

    private CarState status;

    private CurrencyCode currency;

    public Car(
            Long id,
            String label,
            String vendor,
            Double price,
            String licensePlate,
            CarState status,
            CurrencyCode currency
    ) {
        this.id = id;
        this.label = label;
        this.vendor = vendor;
        this.price = price;
        this.licensePlate = licensePlate;
        this.status = status;
        this.currency = currency;
    }

    public Car() {
    }

    public CarState getStatus() {
        return status;
    }

    public Car setStatus(CarState status) {
        this.status = status;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Car setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Car setId(Long id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public Car setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getVendor() {
        return vendor;
    }

    public Car setVendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Car setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public Car setCurrency(CurrencyCode currency) {
        this.currency = currency;
        return this;
    }
}
