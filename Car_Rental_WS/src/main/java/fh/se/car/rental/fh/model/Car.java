package fh.se.car.rental.fh.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotNull(message = "Username cannot be null")
    private String label;
    @NotNull(message = "Vendor cannot be null")
    private String vendor;
    @NotNull(message = "Price cannot be null")
    private Float price;
    private String licensePlate;

    public Car(Long id, String label, String vendor, Float price, String licensePlate) {
        this.id = id;
        this.label = label;
        this.vendor = vendor;
        this.price = price;
        this.licensePlate = licensePlate;
    }

    public Car() {

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

    public Float getPrice() {
        return price;
    }

    public Car setPrice(Float price) {
        this.price = price;
        return this;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Car setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }
}