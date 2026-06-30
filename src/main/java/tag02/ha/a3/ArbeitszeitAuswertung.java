package tag02.ha.a3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import tag02.ha.a3.model.ArbeitszeitEintrag;

public class ArbeitszeitAuswertung {

  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public static void main() {
    List<ArbeitszeitEintrag> eintraege = ladeArbeitszeiten("src/main/java/tag02/ha/a3/resources/arbeitszeiten.csv");

    if (eintraege.isEmpty()) {
      System.out.println("Keine Daten gefunden.");
      return;
    }

    System.out.println(eintraege.size() + " Einträge geladen." + System.lineSeparator());

    Map<String, Double> stundenProMitarbeiter = berechneGesamtstunden(eintraege);

    System.out.println("=== Arbeitszeitauswertung ===" + System.lineSeparator());

    stundenProMitarbeiter.entrySet().stream()
        .sorted(Map.Entry.comparingByKey())
        .forEach(entry -> {
          String[] parts = entry.getKey().split(";");
          System.out.printf("  %s (ID: %s): %.1f Stunden%n",
              parts[1], parts[0], entry.getValue());
        });

    System.out.println(System.lineSeparator() + "=== Zusätzliche Auswertungen ===");
    System.out.printf("  Durchschnittliche tägliche Arbeitszeit: %.1f Stunden%n",
        berechneDurchschnitt(eintraege));
    System.out.printf("  Gesamtarbeitszeit aller Mitarbeiter: %.1f Stunden%n",
        berechneGesamtSumme(eintraege));
    System.out.println("  Arbeitstage: " + berechneAnzahlTage(eintraege));

    System.out.println(System.lineSeparator() + "=== Auswertung für 2026-05-10 ===");
    List<ArbeitszeitEintrag> eintraegeAm10Mai = filterNachDatum(eintraege, LocalDate.of(2026, 5, 10));
    eintraegeAm10Mai.forEach(System.out::println);
  }

  public static List<ArbeitszeitEintrag> ladeArbeitszeiten(String dateiname) {
    List<ArbeitszeitEintrag> eintraege = new ArrayList<>();
    Path path = Paths.get(dateiname);

    try {
      List<String> lines = Files.readAllLines(path);

      IntStream.range(1, lines.size()).mapToObj(lines::get).filter(line -> !line.isBlank())
          .forEachOrdered(line -> {
            String[] parts = line.split(";");
            if (parts.length != 4) {
              System.err.println("Ungültige Zeile übersprungen: " + line);
              return;
            }
            try {
              int mitarbeiterId = Integer.parseInt(parts[0]);
              String name = parts[1];
              LocalDate datum = LocalDate.parse(parts[2], DATE_FORMAT);
              double stunden = Double.parseDouble(parts[3]);

              eintraege.add(new ArbeitszeitEintrag(mitarbeiterId, name, datum, stunden));

            } catch (NumberFormatException e) {
              System.err.println("Fehler beim Parsen: " + line);
            }
          });

    } catch (IOException e) {
      System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
    }

    return eintraege;
  }

  public static Map<String, Double> berechneGesamtstunden(List<ArbeitszeitEintrag> eintraege) {
    Map<String, Double> resultMap = new LinkedHashMap<>();

    for (ArbeitszeitEintrag e : eintraege) {
      String key = e.getMitarbeiterId() + ";" + e.getName();
      resultMap.put(key, resultMap.getOrDefault(key, 0.0) + e.getArbeitsstunden());
    }

    return resultMap;
  }

  public static double berechneDurchschnitt(List<ArbeitszeitEintrag> eintraege) {
    if (eintraege.isEmpty()) {
      return 0.0;
    }

    double summe = 0.0;
    for (ArbeitszeitEintrag e : eintraege) {
      summe += e.getArbeitsstunden();
    }

    return summe / eintraege.size();
  }

  public static double berechneGesamtSumme(List<ArbeitszeitEintrag> eintraege) {
    double summe = 0.0;
    for (ArbeitszeitEintrag e : eintraege) {
      summe += e.getArbeitsstunden();
    }
    return summe;
  }

  public static long berechneAnzahlTage(List<ArbeitszeitEintrag> eintraege) {
    return eintraege.stream()
        .map(ArbeitszeitEintrag::getDatum)
        .distinct()
        .count();
  }

  public static List<ArbeitszeitEintrag> filterNachDatum(List<ArbeitszeitEintrag> eintraege, LocalDate datum) {
    return eintraege.stream()
        .filter(eintrag -> eintrag.getDatum().equals(datum))
        .collect(Collectors.toList());
  }
}