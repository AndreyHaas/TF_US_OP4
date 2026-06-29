package tag01.ha.a1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Loesung1 {
// Geben Sie den Text aus der Datei von Aufgabe output.Aufgabe_1
// (Aufgabe1_output_Lösung.txt aus der Musterlösung) auf der Konsole aus.
  public static void main() {
    Path path = Paths.get("src/main/java/tag01/ha/a1/Aufgabe1_output_loesung.txt");
    String kettenZeichen = null;
    try {
      kettenZeichen = Files.readString(path);
    } catch (NoSuchFileException e) {
      System.out.println("Habe keine '" + path + "' File gefunden! ");
    } catch (IOException e) {
      System.out.println("Allgemeine Fehler: " + e.getMessage());
    }

    if (kettenZeichen != null) {
      System.out.println(kettenZeichen);
    }
  }
}