package tag01.unterricht;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

public class MyInputStreams {

  public static void main() {
    try {
      String text = MyInputStreams.inputStreamToString(
          Paths.get("src/main/java/tag01/unterricht/text.txt"));
      System.out.println(text);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static @Nullable String inputStreamToString(Path file) throws IOException {
    // Mit Files.exists(Path) können wir prüfen, ob eine Datei oder ein Ordner existiert.
    if (Files.exists(file)) {
      // Erzeugt einen InputStream, indem eine Verbindung zu einer Datei hergestellt wird.
      try (InputStream in = Files.newInputStream(file)) {
        // Ausgabe der auf dem Stream verfuegbaren Anzahl an Bytes:
        System.out.println("Available Bytes: " + in.available());

        // Files.size() gibt die Größe der Datei in Bytes zurück.
        // Die Klasse 'Files' enthält einige Methoden, um mit Dateien zu arbeiten.
        byte[] daten = new byte[Math.toIntExact(Files.size(file))];
        System.out.println("Daten-Anzahl: " + daten.length);

        int anzahl = in.read(daten);
        System.out.println("Anzahl gelesener Bytes: " + anzahl);

        // Alle Bytes einlesen: // Ab Java 9
        // Achtung: Bei sehr großen Dateien könnte dies zu einem OutOfMemoryError führen, wenn nicht genug zusammenhängender Arbeitsspeicher verfügbar ist.
        //daten = in.readAllBytes();

        // Weitere Alternative:
        //daten = Files.readAllBytes(file);

        // Daten als String zurückgeben. Wir können einen Zeichensatz angeben, sonst wird der Standardzeichensatz verwendet.

        return new String(daten, StandardCharsets.UTF_8);
      }
    } else {
      return null;
    }
  }

  private static String bufferedReaderToString(Path path) throws IOException {
    StringBuilder builder = new StringBuilder();

    try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line).append(System.lineSeparator());
      }
    }

    return builder.toString();
  }

  private static String scannerToString(Path path) {
    StringBuilder builder = new StringBuilder();
    try (
        InputStream is = Files.newInputStream(path);
        Scanner scanner = new Scanner(is, StandardCharsets.UTF_8)) {
      Pattern pattern = Pattern.compile("([.,。、])");
      scanner.useDelimiter(pattern);

      while (scanner.hasNext()) {
        builder.append(scanner.next().trim()).append(System.lineSeparator());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return builder.toString();
  }
}