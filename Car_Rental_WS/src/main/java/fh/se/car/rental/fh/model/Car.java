package fh.se.car.rental.fh.model;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String label;
    private String vendor;
    private Float price;

    public Car(Long id, String label, String vendor, Float price) {
        this.id = id;
        this.label = label;
        this.vendor = vendor;
        this.price = price;
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
}
