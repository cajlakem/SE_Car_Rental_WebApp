package fh.se.car.rental.fh.model;

import fh.se.car.rental.fh.model.enums.CarState;
import fh.se.car.rental.fh.model.enums.CurrencyCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@ToString
@Document("cars")
public class Car {
    @Id
    private String id;


    private String label;


    private String vendor;


    private Double price;

    private String licensePlate;


    private CarState status;

    private CurrencyCode currency;

    public Car(
            String id,
            @NotNull(message = "Username cannot be null") String label,
            @NotNull(message = "Vendor cannot be null") String vendor,
            @NotNull(message = "Price cannot be null") Double price,
            String licensePlate,
            CarState status,
            @NotNull(message = "currency cannot be null") CurrencyCode currency
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

    public String getId() {
        return id;
    }

    public Car setId(String id) {
        this.id = id;
        return this;
    }
}
