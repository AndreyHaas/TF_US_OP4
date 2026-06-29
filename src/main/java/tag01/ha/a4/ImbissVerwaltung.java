package tag01.ha.a4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImbissVerwaltung {

  private final List<Ware> waren = new ArrayList<>();

  private static final String SPEISEN_CSV = "src/main/java/tag01/ha/a4/speisen.csv";
  private static final String GETRAENKE_CSV = "src/main/java/tag01/ha/a4/getraenke.csv";

  public ImbissVerwaltung() {
    laden();
  }

  public void laden() {
    waren.clear();
    ladenSpeisen();
    ladenGetraenke();
    System.out.println(waren.size() + " Stk  Waren geladen.");
  }

  private void ladenSpeisen() {
    try {
      List<String> lines = Files.readAllLines(Paths.get(SPEISEN_CSV));

      for (String line : lines) {
        if (line.isBlank()) {
          continue;
        }

        String[] parts = line.split(",");
        String bezeichnung = parts[0];
        double preis = Double.parseDouble(parts[1]);
        List<String> zutaten = new ArrayList<>(Arrays.asList(parts).subList(2, parts.length));

        waren.add(new Speise(bezeichnung, preis, zutaten));
      }
    } catch (IOException e) {
      System.err.println("Speisen.csv nicht gefunden – wird neu erstellt.");
    }
  }

  private void ladenGetraenke() {
    try {
      List<String> lines = Files.readAllLines(Paths.get(GETRAENKE_CSV));

      for (String line : lines) {
        if (line.isBlank()) {
          continue;
        }

        String[] parts = line.split(",");
        String bezeichnung = parts[0];
        double preis = Double.parseDouble(parts[1]);
        int fuellmenge = Integer.parseInt(parts[2]);
        waren.add(new Getraenk(bezeichnung, preis, fuellmenge));
      }
    } catch (IOException e) {
      System.err.println("Getraenke.csv nicht gefunden – wird neu erstellt.");
    }
  }

  public void speichern() {
    speichernSpeisen();
    speichernGetraenke();
    System.out.println("Alle Waren gespeichert.");
  }

  private void speichernSpeisen() {
    List<String> lines = waren.stream()
        .filter(Speise.class::isInstance)
        .map(Ware::toCsvString)
        .collect(Collectors.toList());

    try {
      Files.write(Paths.get(SPEISEN_CSV), lines, StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      System.err.println("Fehler beim Speichern der Speisen: " + e.getMessage());
    }
  }

  private void speichernGetraenke() {
    List<String> lines = waren.stream()
        .filter(Getraenk.class::isInstance)
        .map(Ware::toCsvString)
        .collect(Collectors.toList());

    try {
      Files.write(Paths.get(GETRAENKE_CSV), lines, StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      System.err.println("Fehler beim Speichern der Getränke: " + e.getMessage());
    }
  }

  public void addSpeise(String bezeichnung, double preis, List<String> zutaten) {
    waren.add(new Speise(bezeichnung, preis, zutaten));
    speichern();
  }

  public void addGetraenk(String bezeichnung, double preis, int fuellmenge) {
    waren.add(new Getraenk(bezeichnung, preis, fuellmenge));
    speichern();
  }

  public void alleAnzeigen() {
    if (waren.isEmpty()) {
      System.out.println("Keine Waren vorhanden.");
      return;
    }

    System.out.println(System.lineSeparator() + "Alle Waren:");
    for (int i = 0; i < waren.size(); i++) {
      System.out.println((i + 1) + ". " + waren.get(i));
    }
  }

  public static void main() {
    ImbissVerwaltung verwaltung = new ImbissVerwaltung();
    verwaltung.alleAnzeigen();
    verwaltung.addSpeise("Döner", 6.99,
        List.of("Fleisch", "Salat", "Zwiebeln", "Tzatziki", "Brot"));
    verwaltung.addGetraenk("Apfelschorle", 2.49, 400);
    verwaltung.alleAnzeigen();
    verwaltung.speichern();
  }
}