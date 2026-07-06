package main.java.tag02.unterricht;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import main.java.tag02.unterricht.models.Artikel;


public class MyStreams {

  public static void main() {
    try {
      Map<Integer, Artikel> artikelMap =
          MyStreams.createMapFromFile("src/src/main/resources/tag02/unterricht/artikel.csv");
      artikelMap.values().forEach(System.out::println);

      artikelMap = MyStreams.filterByHersteller(artikelMap, "GameGear");
      artikelMap.values().forEach(System.out::println);

      MyStreams.orderByPreis(artikelMap).forEach(System.out::println);

    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
    }
  }

  private static Map<Integer, Artikel> createMapFromFile(String path) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
      return reader.lines()
          .skip(1)
          .map(zeile -> {
            String[] werte = zeile.split(",");
            return new Artikel(Integer.parseInt(werte[0]), werte[1], Double.parseDouble(werte[2]),
                werte[3]
            );
          })
          .collect(Collectors.toMap(Artikel::getNummer, Function.identity()));
    }
  }

  private static Map<Integer, Artikel> filterByHersteller(Map<Integer, Artikel> artikelMap,
      String hersteller) {
    return artikelMap.values().stream()
        .filter(artikel -> artikel.getHersteller().equalsIgnoreCase(hersteller))
        .collect(Collectors.toMap(Artikel::getNummer, Function.identity()));
  }

  private static List<Artikel> orderByPreis(Map<Integer, Artikel> artikelMap) {
    return artikelMap.values().stream()
        .sorted(Comparator.comparingDouble(Artikel::getPreis))
        .toList();
  }
}