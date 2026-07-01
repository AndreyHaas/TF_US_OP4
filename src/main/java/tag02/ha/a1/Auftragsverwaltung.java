package main.java.tag02.ha.a1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Auftragsverwaltung {

  private static final String ORDNER = "src/main/java/tag02/unterricht/auftraege/";
  private static final Scanner scanner = new Scanner(System.in);

  public static void main() {
    try {
      Files.createDirectories(Paths.get(ORDNER));
    } catch (IOException e) {
      System.err.println("Fehler beim Erstellen des Ordners: " + e.getMessage());
      return;
    }

    System.out.println("Auftragsverwaltung gestartet." + System.lineSeparator());

    while (true) {
      menueAnzeigen();
      String auswahl = scanner.nextLine().trim().toUpperCase();

      switch (auswahl) {
        case "N", "NEW", "NEU" -> neuerEintrag();
        case "L", "LADEN" -> eintragLaden();
        case "A", "ANZEIGEN" -> alleEintraegeAnzeigen();
        case "X", "Q", "EXIT", "QUIT" -> {
          System.out.println("Programm beendet.");
          scanner.close();
          return;
        }
        default -> System.out.println("Ungültige Eingabe! Bitte N, L, A oder X wählen.");
      }
      System.out.println();
    }
  }

  private static void menueAnzeigen() {
    System.out.println("=== MENÜ ===");
    System.out.println("  [N] Neuer Eintrag");
    System.out.println("  [L] Eintrag laden");
    System.out.println("  [A] Alle Einträge anzeigen");
    System.out.println("  [X] Beenden");
    System.out.print("Ihre Wahl: ");
  }

  private static void neuerEintrag() {
    System.out.println(System.lineSeparator() + "--- Neuer Eintrag ---");

    System.out.print("Auftragsnummer: ");
    String nummer = scanner.nextLine().trim();

    if (nummer.isEmpty()) {
      System.err.println("Auftragsnummer darf nicht leer sein!");
      return;
    }

    Path dateiPfad = Paths.get(ORDNER + nummer + ".txt");
    if (Files.exists(dateiPfad)) {
      System.err.println("Auftrag mit dieser Nummer existiert bereits!");
      return;
    }

    System.out.print("Bezeichnung: ");
    String bezeichnung = scanner.nextLine().trim();

    System.out.print("Datum (TT.MM.JJJJ): ");
    String datum = scanner.nextLine().trim();

    System.out.print("Preis: ");
    String preis = scanner.nextLine().trim();

    String inhalt = "Auftragsnummer: " + nummer + System.lineSeparator() +
        "Bezeichnung: " + bezeichnung + System.lineSeparator() +
        "Datum: " + datum + System.lineSeparator() +
        "Preis: " + preis + " € " + System.lineSeparator();

    try {
      Files.writeString(dateiPfad, inhalt, StandardOpenOption.CREATE);
      System.out.println("Auftrag erfolgreich gespeichert: " + dateiPfad.toAbsolutePath());
    } catch (IOException e) {
      System.err.println("Fehler beim Speichern: " + e.getMessage());
    }
  }

  private static void eintragLaden() {
    System.out.println(System.lineSeparator() + "--- Eintrag laden ---");

    alleEintraegeAnzeigen();

    System.out.print("Auftragsnummer eingeben: ");
    String nummer = scanner.nextLine().trim();

    if (nummer.isEmpty()) {
      System.err.println("Auftragsnummer darf nicht leer sein!");
      return;
    }

    Path dateiPfad = Paths.get(ORDNER + nummer + ".txt");

    if (!Files.exists(dateiPfad)) {
      System.err.println("Auftrag mit Nummer " + nummer + " nicht gefunden!");
      return;
    }

    try {
      String inhalt = Files.readString(dateiPfad);
      System.out.println(System.lineSeparator() + "Inhalt von Auftrag " + nummer + ":");
      System.out.println("----------------------------------------");
      System.out.println(inhalt);
      System.out.println("----------------------------------------");
    } catch (IOException e) {
      System.err.println("Fehler beim Lesen: " + e.getMessage());
    }
  }

  private static void alleEintraegeAnzeigen() {
    System.out.println(System.lineSeparator() + "--- Alle Einträge ---");

    try (Stream<Path> stream = Files.list(Paths.get(ORDNER))) {
      List<String> dateien = stream
          .filter(Files::isRegularFile)
          .map(path -> path.getFileName().toString())
          .sorted()
          .toList();

      if (dateien.isEmpty()) {
        System.out.println("Keine Aufträge vorhanden.");
        return;
      }

      System.out.println("Vorhandene Aufträge:");
      for (String datei : dateien) {
        String nummer = datei.replace(".txt", "");
        System.out.println(" - " + nummer);
      }

    } catch (IOException e) {
      System.err.println("Fehler beim Auflisten: " + e.getMessage());
    }
  }
}