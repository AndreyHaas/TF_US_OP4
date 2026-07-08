package main.java.tag08.aufgaben.ha2.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = -6587428142259818424L;

    private String street;
    private int number;
    private String city;

    public Address() {}

    public Address(String street, int number, String city) {
        this.street = street;
        this.number = number;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;
        return number == address.number && Objects.equals(street, address.street) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(street);
        result = 31 * result + number;
        result = 31 * result + Objects.hashCode(city);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Address.class.getSimpleName() + "[", "]")
                .add("street='" + street + "'")
                .add("number=" + number)
                .add("city='" + city + "'")
                .toString();
    }
}
