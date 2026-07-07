package main.java.tag07.src.aufgaben.lösung_2v2;


import java.io.File;
import java.io.IOException;
import tools.jackson.databind.ObjectMapper;

public class Lösung_2 {

  private static final ObjectMapper mapper = new ObjectMapper();

  public static void main(String[] args) {
    try {
      Person p = readPerson("resources/person.json");
      printData(p);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Person readPerson(String file) throws IOException {
    return mapper.readValue(new File(file), Person.class);
  }

  /*
  ---
  Name: Jenny Azad
  Alter: 30
  Adresse: Musterstraße 123, Musterstadt
  Hobbys: lesen, schwimmen, reisen
  ---
   */
  private static void printData(Person person) {
    System.out.println("---");
    System.out.println("Name: " + person.getName());
    System.out.println("Alter: " + person.getAge());
    String address = person.getAddress().getStreet() + " " + person.getAddress().getNumber() + ", "
        + person.getAddress().getCity();
    System.out.println("Adresse: " + address);
    System.out.print("Hobbys: ");
    for (int i = 0; i < person.getHobbies().length; i++) {
      System.out.print(person.getHobbies()[i]);
			if (i < person.getHobbies().length - 1) {
				System.out.print(", ");
			}
    }
    System.out.println("\n---");
  }
}
