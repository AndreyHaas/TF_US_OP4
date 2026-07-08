package main.java.tag08.aufgaben.ha2.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Person implements Serializable {
    private String name;
    private int age;
    private Address address;
    private List<String> hobbies;

    public Person() {}

    public Person(String name, int age, Address address, List<String> hobbies) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.hobbies = hobbies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public void printData() {
        System.out.println("---");
        System.out.println("Name: " + name);
        System.out.println("Alter: " + age);
        System.out.println("Adresse: " + address.getStreet() + " " + address.getNumber() + ", " + address.getCity());
        System.out.println("Hobbys: " + String.join(", ", hobbies));
        System.out.println("---");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name) && Objects.equals(address, person.address) && Objects.equals(hobbies, person.hobbies);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + age;
        result = 31 * result + Objects.hashCode(address);
        result = 31 * result + Objects.hashCode(hobbies);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("age=" + age)
                .add("address=" + address)
                .add("hobbies=" + hobbies)
                .toString();
    }
}
