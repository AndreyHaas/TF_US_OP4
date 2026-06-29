package tag01.ha.a5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogAnalyzerApp {

  private static final String LOG_DATEI = "src/main/java/tag01/ha/a5/server.log";
  private static final String ERGEBNIS_DATEI = "src/main/java/tag01/ha/a5/error_summary.txt";

  public static void main() {
    LocalDateTime startZeit = LocalDateTime.now();

    try {
      List<String> lines = Files.readAllLines(Paths.get(LOG_DATEI));

      List<String> fehlerZeilen = lines.stream()
          .filter(line -> line.contains("ERROR"))
          .toList();

      long fehlerCount = fehlerZeilen.size();

      Map<String, Long> fehlerTypen = fehlerZeilen.stream()
          .map(line -> line.replaceFirst("ERROR - ", ""))
          .collect(Collectors.groupingBy(
              typ -> typ,
              Collectors.counting()
          ));

      StringBuilder ergebnis = new StringBuilder();
      ergebnis.append("Anzahl der Fehler: ").append(fehlerCount).append(System.lineSeparator());
      ergebnis.append("Analyse durchgeführt am: ")
          .append(startZeit.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
          .append(System.lineSeparator());
      ergebnis.append("Fehler im Detail:").append(System.lineSeparator());
      fehlerTypen.forEach((typ, count) ->
          ergebnis.append("  - ")
              .append(typ)
              .append(": ")
              .append(count)
              .append(System.lineSeparator())
      );

      Files.writeString(Paths.get(ERGEBNIS_DATEI), ergebnis.toString(), StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING
      );

      System.out.println("Analyse abgeschlossen!");
      System.out.println("Fehler gefunden: " + fehlerCount);
      System.out.println("Ergebnis gespeichert in: " + ERGEBNIS_DATEI);

    } catch (IOException e) {
      System.err.println("Fehler: " + e.getMessage());
    }
  }
}