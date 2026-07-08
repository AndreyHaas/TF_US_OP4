package main.java.tag08.aufgaben.ha1;

import main.java.tag08.aufgaben.ha1.model.Auto;
import tools.jackson.databind.ObjectMapper;

import java.io.File;

public class Application {
    static void main() {
        String pfad = "src/main/java/tag08/aufgaben/ha1/auto.json";

        System.out.println("=== 1a: Serialisierung ===");

        Auto auto = new Auto("Rot", 180, "BMW");
        System.out.println("Auto vor Serialisierung: " + auto);

        ObjectMapper mapper = new ObjectMapper();

        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(pfad), auto);

        System.out.println("Auto wurde in '" + pfad + "' serialisiert." + System.lineSeparator());

        System.out.println("=== 1b: De-Serialisierung ===");

        Auto autoDeserialized = mapper.readValue(new File(pfad), Auto.class);

        System.out.println("Auto nach De-Serialisierung: " + autoDeserialized);

        System.out.println(System.lineSeparator() + "=== Vergleich ===");
        System.out.println("Original:  " + auto);
        System.out.println("Deserialisiert: " + autoDeserialized);
        System.out.println("Sind die Objekte gleich? Antwort: " + auto.equals(autoDeserialized));
    }
}