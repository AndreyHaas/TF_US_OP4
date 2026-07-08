package main.java.tag08.aufgaben.ha2;

import main.java.tag08.aufgaben.ha2.model.Person;
import tools.jackson.databind.ObjectMapper;

import java.io.File;

public class Application {
    static void main() {
        String pfad = "src/main/java/tag08/aufgaben/ha2/person.json";

        ObjectMapper mapper = new ObjectMapper();

        Person person = mapper.readValue(new File(pfad), Person.class);

        person.printData();

        System.out.println(System.lineSeparator() + "Person-Objekt: " + person);
    }
}
