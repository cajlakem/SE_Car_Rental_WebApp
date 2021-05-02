package fh.se.car.rental.fh.services;

public enum DocumentType {
    car(10),
    booking(20),
    user(30);

    int prefix;

    DocumentType(int prefix) {
        this.prefix = prefix;
    }

    public int getPrefix() {
        return this.prefix;
    }
}
