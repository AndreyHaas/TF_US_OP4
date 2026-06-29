package tag01.ha.a2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Loesung2 {
  // Filtern Sie aus dem Ergebnistext aus Aufgabe output.Aufgabe_1 alle Satzzeichen heraus und geben
  // Sie diese (die Satzzeichen) in einer Zeile ohne Leerzeichen auf der Konsole aus.
  public static void main(String[] args) throws IOException {
    Path path = Paths.get("output/Aufgabe1_output_Lösung.txt");
    String content = Files.readString(path);

    // Alle Zeichen, die KEINE Buchstaben/Zahlen/Leerzeichen sind = Satzzeichen
    String punctuation = content.replaceAll("[^\\p{P}\\p{S}]", "");

    System.out.println(punctuation);
  }
}