package tag01.ha.a3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Loesung3 {

  public static void main() throws IOException {

    Path inputPath = Paths.get("src/main/java/tag01/ha/a3/log.txt");
    Path shortCsvPath = Paths.get("src/main/java/tag01/ha/a3/short.csv");
    Path longCsvPath = Paths.get("src/main/java/tag01/ha/a3/long.csv");

    List<String> strings = Files.readAllLines(inputPath);

    List<Eintrag> eintraege = strings.stream()
        .filter(line -> !line.isBlank())
        .map(Eintrag::parseFromLine).toList();

    List<String> shortLines = eintraege.stream()
        .map(Eintrag::toShortCsv)
        .toList();
    Files.write(shortCsvPath, shortLines);

    List<String> longLines = eintraege.stream()
        .map(Eintrag::toLongCsv)
        .toList();
    Files.write(longCsvPath, longLines);

    System.out.println("short.csv und long.csv wurden erstellt.");
  }
}
