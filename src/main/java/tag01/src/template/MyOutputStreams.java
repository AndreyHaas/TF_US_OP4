package template;

import java.io.*;
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
 * Fast alle Streams implementieren die Interfaces => Closeable, AutoCloseable
 *
 * Die Schnittstelle Closeable stellt eine Methode namens close()
 * zum Schließen einer Quelle oder eines Ziels von Daten bereit.
 *
 * Die Schnittstelle AutoCloseable bietet auch eine Methode namens close() mit
 * ähnlichem Verhalten wie in Closeable. In diesem Fall wird jedoch beim
 * Verlassen eines try-with-resource-Blocks automatisch die Methode close()
 * aufgerufen.
 */
public class MyOutputStreams
{
    public static void main(String[] args)
    {
        // Text ist Lorem Ipsum auf Japanisch zum Test von Multibyte Zeichen
        // https://de.lipsum.com/feed/html
        String lorem = "\uD83D\uDE82Lorem ipsum dolor sit amet, consectetur adipiscing elit. 今では幸福など存在しないと言われている。試合前までは、フュギアとイアキュリスは、ヌルラのように憂鬱な様子で座っていた。\n弓状骨前庭。いや、前にも動いてないのに、笑いの愛門としましょう。みんな面倒な人たちだ。友達がいないと毒に侵されることもある。\uD83D\uDE00\n";

        // Hinweis: Unter Run -> Edit Configurations -> Edit Configuration Templates -> Application -> Working Directory -> $MODULE_WORKING_DIR$ einfügen.
		try
		{
			writeStringAsBytes("resources/Beispiel/Text.txt", lorem);

            writeString("resources/Beispiel/Text2.txt", lorem);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void writeStringAsBytes(String file, String data) throws IOException
    {
        // Um mit Dateien zu arbeiten, brauchen wir entweder ein Objekt der File-Klasse oder vom Path-Interface.
        // Welches wir genau brauchen ist abhängig von anderen Klassen.
        Path path = Paths.get(file);
        // Das Path-Objekt erzeugen wir über Paths.get(String) oder Path.of(String)
        // File-Objekte werden über den Konstruktor erzeugt.

        // 'Files' (mit einem s) ist eine Klasse mit vielen Methoden in Bezug auf Dateien. Wir können Dateien erstellen, löschen, verschieben, Ordner erstellen oder den Inhalt von Ordnern auflisten. Achtung: Gelöschte Dateien und Ordner landen NICHT im Papierkorb!
        Files.createDirectories(path.toAbsolutePath().getParent()); // Ordner im Dateipfad, die noch nicht existieren, werden erstellt.
        // 'getParent()' gibt das übergeordnete Verzeichnis zurück.
        // 'toAbsolutePath()' erzeugt aus relativen Pfaden den absoluten Pfad.

        // https://www.utf8-chartable.de/unicode-utf8-table.pl
        // Alternativ: https://www.charset.org/utf-8
        // https://stackoverflow.com/questions/5078314/isnt-the-size-of-character-in-java-2-bytes

        // String in Byte-Array umwandeln:
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);

		//System.out.println(Arrays.toString(bytes));

		// OutputStream implementiert AutoCloseable, darum können wir Try-With-Resources verwenden, damit der Stream automatisch geschlossen wird.
        // Der OutputStream wird selbst die Datei erzeugen, wenn sie nicht existiert. Wenn sie schon existiert, wird der vorhandene Inhalt ersetzt.
		try (OutputStream out = Files.newOutputStream(path))
		{
			for (byte elem : bytes)
				out.write(elem); // Jedes Byte einzeln in die Datei schreiben.

            out.write(bytes); // Alle Bytes in einem Schritt in die Datei schreiben.

            out.write(bytes, 10, 4); // Durch Angabe von Offset und Length können wir auch nur bestimmte Bytes in die Datei schreiben.

            //out.flush(); // Je nach Implementierung des OutputStreams werden zu schreibende Daten nur zwischengespeichert und erst beim Schließen des Streams tatsächlich in die Datei geschrieben. Mit flush() können wir das Schreiben in die Datei erzwingen.
        }
    }

    private static void writeString(String file, String data) throws IOException
    {
        Path path = Paths.get(file);
        //Path path = Path.of(file); // Ab Java 11

        // Der OutputStreamWriter umschließt einen OutputStream und kann Zeichen direkt an das gewünschte Ziel schreiben.
        // Optional können wir dem OutputStreamWriter auch einen Zeichensatz zur Kodierung mitgeben.
        // Es wird der Schritt zur Konvertierung in ein byte[] gespart, bzw. der Writer macht das für uns.
        try (OutputStream out = Files.newOutputStream(path);
             Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8))
        {
            writer.write(data);
        }

        // Alternative: FileWriter. Hier können wir auch angeben, ob der Text an den vorhandenen angehangen werden soll.
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8, true))
        {
            writer.write(data);
            writer.flush(); // Der FileWriter puffert den Text, bevor er in die Datei geschrieben wird. Das tatsächliche Schreiben in die Datei findet erst statt, wenn der FileWriter geschlossen wird. Mit flush() können wir das Schreiben in die Datei aber schon vorher erzwingen.
        }

        // Viel angenehmer, ab Java 11!
        Files.writeString(path, data, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }
}
