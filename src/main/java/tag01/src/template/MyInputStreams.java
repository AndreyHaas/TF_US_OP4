package template;
// Achtung: Zuerst MyOutputStreams bearbeiten!

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 * Generelles: Die Klasse InputStream ist eine Superklasse
 * Sub-Klassen: AudioInputStream, ByteArrayInputStream, FileInputStream, FilterInputStream,
 * ObjectInputStream, PipedInputStream, SequenceInputStream, StringBufferInputStream
 *
 * Streams implementieren die Interfaces => Closeable, AutoCloseable
 */
public class MyInputStreams
{
    public static void main(String[] args)
    {
        try
        {
            Path p = Paths.get("resources/Beispiel/Text.txt");

            // Verwendet den InputStream
            String text = inputStreamToString(p);
            System.out.println(text);
            System.out.println();

            // Verwendet den BufferedReader
            text = bufferedReaderToString(p);
            System.out.println(text);
            System.out.println();

            // Verwendet einen Scanner
            text = scannerToString(p);
            System.out.println(text);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

	private static String inputStreamToString(Path file) throws IOException
	{
		// Mit Files.exists(Path) können wir prüfen, ob eine Datei oder ein Ordner existiert.
		if (Files.exists(file))
		{
			// Erzeugt einen InputStream, indem eine Verbindung zu einer Datei hergestellt wird.
			try (InputStream in = Files.newInputStream(file))
			{
				// Ausgabe der auf dem Stream verfügbaren Anzahl an Bytes:
				System.out.println("Available: " + in.available());

                // Files.size() gibt die Größe der Datei in Bytes zurück.
                // Die Klasse 'Files' enthält einige Methoden, um mit Dateien zu arbeiten.
                byte[] daten = new byte[(int) Files.size(file)]; // Buffer
                // Größe in Bytes ausgeben:
                System.out.println("Daten-Anzahl: " + daten.length);

                int anzahl = in.read(daten);
                System.out.println("Anzahl gelesen: " + anzahl);

                // Alle Bytes einlesen: // Ab Java 9
                // Achtung: Bei sehr großen Dateien könnte dies zu einem OutOfMemoryError führen, wenn nicht genug zusammenhängender Arbeitsspeicher verfügbar ist.
				//daten = in.readAllBytes();

                // Weitere Alternative:
                //daten = Files.readAllBytes(file);

                // Daten als String zurückgeben. Wir können einen Zeichensatz angeben, sonst wird der Standardzeichensatz verwendet.
                return new String(daten, StandardCharsets.UTF_8);
            }
        }
        else
            return null;
    }

    private static String bufferedReaderToString(Path file) throws IOException
    {
        StringBuilder stringBuilder = new StringBuilder();

        // Ein InputStreamReader ist eine Brücke von Byteströmen zu Zeichenströmen.
        // Er liest bytes und dekodiert sie in Zeichen unter Verwendung des angegebenen Zeichensatzes.
        // Ein BufferedReader liest Text aus einem Zeicheneingabestrom und puffert die Zeichen, um ein effizientes Lesen von Zeichen zu ermöglichen.
        // Es wird empfohlen, den BufferedReader an Stelle des puren InputStreamReaders oder FileReaders zu verwenden.
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)))
        {
            // Der BufferedReader lässt sich auch direkt über die Files-Klasse erzeugen:
			//Files.newBufferedReader(file, StandardCharsets.UTF_8)

            String line = null;
            // readLine() liest jede Zeile aus dem Stream und gibt am Ende des Streams (am Ende der Datei) null zurück.
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line).append("\n");
            }

			// Alternative:
			//List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);

            // Ab Java 11
            //String text = Files.readString(file, StandardCharsets.UTF_8);

            return stringBuilder.toString();
        }
    }

    // abc... => Letters
    // 123... => Digits
    // \d => Any Digit
    // \D => Any Non-digit character
    // . => Any Character
    // \. => Period
    // [abc] => Only a, b, or c
    // [^abc] => Not a, b, nor c
    // [a-z] => Characters a to z
    // [0-9] => Numbers 0 to 9
    // \w => Any Alphanumeric character
    // \W => Any Non-alphanumeric character
    // {m} => m Repetitions
    // {m,n} => m to n Repetitions
    // * => Zero or more repetitions
    // + => One or more repetitions
    // ? => Optional character
    // \s => Any Whitespace
    // \S => Any Non-whitespace character
    // ^...$ => Starts and ends
    // (...) => Capture Group
    // (a(bc)) => Capture Sub-group
    // (.*) => Capture all
    // (ab|cd) => Matches ab or cd
    /*
     * Pattern =>
     * https://www.vogella.com/tutorials/JavaRegularExpressions/article.html
     * https://www.regular-expressions.info/tutorial.html
     */
    private static String scannerToString(Path file) throws IOException
    {
        StringBuilder stringBuilder = new StringBuilder();

        try (InputStream in = Files.newInputStream(file);
             Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) // .name() weil der Scanner erst ab Java 10 direkt Charsets annehmen kann.
        {
            // Zeilenweise über den Scanner einlesen:
            /*while (scanner.hasNextLine())
            {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }*/

            // Das Pattern prüft auf Punkt und Komma.
            Pattern pattern = Pattern.compile("([.,。、])");
            // Das Pattern als Delimiter bewirkt, dass Zeichen, die dem Pattern entsprechen, als Trennzeichen verarbeitet werden.
            scanner.useDelimiter(pattern);

            while (scanner.hasNext())
            {
                stringBuilder.append(scanner.next().trim()).append("\n");
            }

            return stringBuilder.toString();
        }
    }
}
