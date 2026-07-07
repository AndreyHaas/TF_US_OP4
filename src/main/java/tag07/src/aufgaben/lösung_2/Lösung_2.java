package main.java.tag07.src.aufgaben.lösung_2;


import java.io.File;
import java.util.Arrays;
import tools.jackson.databind.ObjectMapper;


public class Lösung_2 {

  public static void main(String[] args) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      Person person = mapper.readValue(new File("resources/person.json"), Person.class);
      printData(person);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void printData(Person person) {
    System.out.println("---");
    System.out.println(person);
    System.out.println("---");
  }

}

class Address {

  private String street;
  private int number;
  private String city;

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

  public Address() {
  }

  @Override
  public String toString() {
    return street + " " + number + ", " + city;
  }
}

class Person {

  private String name;
  private int age;
  private Address address;
  private String[] hobbies;

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public Address getAddress() {
    return address;
  }

  public String[] getHobbies() {
    return hobbies;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public void setHobbies(String[] hobbies) {
    this.hobbies = hobbies;
  }

  public Person() {
  }

  @Override
  public String toString() {
    return "Name: " + name + "\nAlter: " + age + "\nAdresse: " + address + "\nHobbies: "
        + Arrays.toString(hobbies).replaceAll("[\\[\\]]", "");
  }
}
