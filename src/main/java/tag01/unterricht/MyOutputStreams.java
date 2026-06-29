package tag01.unterricht;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*
 * Java IO bietet das Konzept von Streams, das im Grunde einen kontinuierlichen
 * Datenfluss darstellt. Streams können viele verschiedene Datentypen wie
 * Bytes, Zeichen, Objekte usw. unterstützen.
 *
 * Generelles: Die Klasse OutputStream ist eine Superklasse
 * Sub-Klassen: AudioOutputStream, ByteArrayOutputStream, FileOutputStream, FilterOutputStream,
 * ObjectOutputStream, PipedOutputStream, SequenceOutputStream, StringBufferOutputStream
 *
 * Fast alle Streams implementieren die Interfaces → Closeable, AutoCloseable
 *
 * Die Schnittstelle Closeable stellt eine Methode namens close()
 * zum Schließen einer Quelle oder eines Ziels von Daten bereit.
 *
 * Die Schnittstelle AutoCloseable bietet auch eine Methode namens close() mit
 * ähnlichem Verhalten wie in Closeable. In diesem Fall wird jedoch beim
 * Verlassen eines try-with-resource-Blocks automatisch die Methode close()
 * aufgerufen.
 */
public class MyOutputStreams {

  public static void main() {

    String lorem = new StringBuilder().append(
            "\uD83D\uDE82Lorem ipsum dolor sit amet, consectetur adipiscing elit. ").append(
            "今では幸福など存在しないと言われている。試合前までは、フュギアとイアキュリスは、ヌルラのように憂鬱な様子で座っていた。\n")
        .append(
            "弓状骨前庭。いや、前にも動いてないのに、笑いの愛門としましょう。みんな面倒な人たちだ。友達がいないと毒に侵されることもある。\uD83D\uDE00\n")
        .toString();

    try {
      MyOutputStreams.writeStringAsBytes("src/main/java/tag01/unterricht/text.txt", lorem);
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      MyOutputStreams.writeString("src/main/java/tag01/unterricht/text2.txt", lorem);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void writeStringAsBytes(String file, String data) throws IOException {

    /*
    Um mit Dateien zu arbeiten, brauchen wir entweder ein Objekt der File-Klasse oder vom Path-Interface.
    Welches wir genau brauchen ist abhängig von anderen Klassen.
    Das Path-Objekt erzeugen wir über Paths.get(String) oder Path.of(String)
    File-Objekte werden über den Konstruktor erzeugt.
    */
    Path path = Paths.get(file);

    /*
    'Files' (mit einem s) ist eine Klasse mit vielen Hilfsmethoden in Bezug auf Dateien.
    Wir können Dateien erstellen, löschen, verschieben, Ordner erstellen oder den Inhalt von Ordnern auflisten.
    Achtung: Gelöschte Dateien und Ordner landen NICHT im Papierkorb!
    Ordner im Dateipfad, die noch nicht existieren, werden erstellt.
    'getParent()' gibt das übergeordnete Verzeichnis zurück.
    'toAbsolutePath()' erzeugt aus relativen Pfaden den absoluten Pfad.
    */
    Files.createDirectories(path.toAbsolutePath().getParent());

    // String in Byte-Array umwandeln:
    byte[] bytes = data.getBytes(StandardCharsets.UTF_8);

    // Angucken des ByteArrays:
    // System.out.println(Arrays.toString(bytes));

    /*
    OutputStream implementiert AutoCloseable, darum können wir Try-With Resources verwenden, damit
    der Stream automatisch wieder geschlossen wird. Der OutputStream wird selbst die Datei erzeugen.
    wenn sie nicht existiert. Wenn die Datei schon existiert, wird der vorhandene Inhalt ersetzt.
    */
    try (OutputStream out = Files.newOutputStream(path)) {
      for (byte elem : bytes) {
        out.write(elem); // Jedes Byte einzeln in die Datei schreiben.
      }

      out.write(bytes); // Alle Bytes in einem Schritt in die Datei schreiben.
      out.write(bytes, 10, 4);
      // Durch Angabe von Offset und Length können wir auch nur bestimmte Bytes in die Datei schreiben.
    }
  }

  private static void writeString(String file, String data) throws IOException {
    Path path = Paths.get(file);
    try (OutputStream os = Files.newOutputStream(path); Writer writer = new OutputStreamWriter(os,
        StandardCharsets.UTF_8)) {
      writer.write(data);
    }

    //Alternative
    try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8, true)) {
      writer.write(data);
      writer.flush(); // ← Hier nicht nötig!
    }

    Files.writeString(path, data, StandardOpenOption.APPEND);
  }
}