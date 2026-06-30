package tag02.src.template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Der Begriff "Stream" beschreibt einen Datenstrom, zum Beispiel Input- und Output-Streams in und aus Dateien.
// Demnächst werden wir auch den Datenstrom zwischen zwei Programmen über das Netzwerk mit Sockets kennenlernen.
// In diesem Thema heute geht es aber um das Interface "Stream<T>", welches Datenströme aus z.B. Listen bereitstellt.

// Beispiel: Wir haben eine CSV-Datei mit Artikeln, die wir filtern und sortieren möchten.
			// Dazu wandeln wir alle Einträge in der CSV-Datei in Artikel-Objekte um
			// und verwenden das Stream<T>-Interface, um zu filtern und zu sortieren.

public class MyStreams
{
	public static void main()
	{
		try
		{
			Map<Integer, Artikel> artikelMap = toMapFromFile("/tag02/unterricht/artikel.csv");
			// für getResourceAsStream muss der Pfad mit / beginnen.
			artikelMap.values().forEach(System.out::println); // Ausgabe aller Artikel
			System.out.println();

			Scanner scanner = new Scanner(System.in);
			System.out.print("Hersteller: ");
			String hersteller = scanner.nextLine(); // Hersteller eingeben lassen
			artikelMap = filterByHersteller(artikelMap, hersteller); // und nach dem eingegebenen Hersteller filtern
			artikelMap.values().forEach(System.out::println); // Ausgabe aller Artikel
			System.out.println();
			System.out.println("Nach Preis sortiert: ");

			// HashMaps können nur nach dem Hashwert und TreeMap nur nach dem Key sortiert sein, also verwenden wir hier eine List.
			List<Artikel> artikelList = orderByPreis(artikelMap);
			artikelList.forEach(System.out::println);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// JH Die Methode erzeugt ein Verzeichnis aus einer Datei. Wir uebergeben den Pfad zur Datei und gibt uns Map<Integer, Artikel> zurueck.
	// Diese Methode liest eine CSV-Datei ein und wandelt jede Zeile in ein Artikel-Objekt um.
	// Anschließend werden alle Artikel in einer Map<Integer, Artikel> gespeichert.
	private static Map<Integer, Artikel> toMapFromFile(String path) throws IOException
	{
		// Anwendungsfall für getResourceAsStream gegenüber anderen Methoden:
        // getResourceAsStream bezieht sich auf Dateien, die mit dem Programm ausgeliefert werden.
        // Dateien, die fester Bestandteil der Anwendung sind, zum Beispiel als JAR verpackt.
        // Aus diesen Dateien kann nur gelesen werden. Ein Schreibzugriff ist nicht möglich.
		// Das wird häufig verwendet, um Grafiken zur Anzeige zu laden, als Icons zum Beispiel.
		// Oder um Standard-Konfigurationen für die Anwendung bereitzustellen.
		// Dazu müssen sich die Dateien im Projekt in einem Resources-Ordner befinden, der auch als Resources-Root markiert ist.

		//			// (JH) Original Code:
		//			Stream<Artikel> artikelStream = reader.lines().skip(1).map(zeile -> {
		//				// Jede Zeile der Datei steht für einen Artikel. Die Werte sind mit Komma getrennt.
		//				String[] werte = zeile.split(",");
		//				// mit split(",") bekommen wir ein Array zurück, in dem jedes Element einzeln steht.
		//				int artikelNummer = Integer.parseInt(werte[0]);
		//				String bezeichnung = werte[1];
		//				double preis = Double.parseDouble(werte[2]);
		//				String hersteller = werte[3];
		//				// Den erzeugten Artikel müssen wir zurückgeben.
		//				return new Artikel(artikelNummer, bezeichnung, preis, hersteller);
		//			});

		// JH: Warum nutzen wir getResourcesAsStream und nicht eine andere Methode. Files.newInputStream(...) z.B.?
		// Antwort: Weil die Datei artikel.csv im resources Ordner liegt. Die Datei gehoert zur Anwendung und speziell fuer Dateien im Resources Ordner sollten wir die getResourceAsStream benutzen, die fuer diesen Zugriff da ist. MyStreams.class liefert das Class-Objekt zurueck. Ueber das Class Objekt kann Java nach Resourcen suchen.
		// Den InputStream kennen wir noch von gestern. Repraesentiert einen Byte-Strom.
		// Der InputStream liefert Bytes. Der BufferedReader möchte Zeichen. Der InputStreamReader ist wieder die Brücke. Der InputStreamReader wandelt die Bytes in Zeichen um: Datei -> Bytes -> InputStream -> InputStreamReader -> Zeichen -> BufferedReader. Der BufferedReader liest gerne grosse Bloecke von Zeichen ein, damit nicht staendig auf die Datei zugegriffen werden muss. Mit diesem BufferedReader wollen wir jetzt arbeiten.
		// JH Der BufferedReader ist deutlich älter als die Stream API. Ursprünglich wurde er entwickelt, um Text effizient und zeilenweise einzulesen. Mit Java 8 erhielt er zusätzlich die Methode lines(), die einen Stream<String> liefert. Dadurch lässt sich der BufferedReader heute nahtlos mit der Stream API kombinieren.
		// "Der BufferedReader puffert Zeichen (keine Bytes) und liefert mit lines() einen Stream<String>. Die Stream API verarbeitet also nicht die Bytes der Datei, sondern die einzelnen Zeilen als String-Objekte. Anschließend werden diese Strings mit map() in Artikel-Objekte umgewandelt."
        try (InputStream in = MyStreams.class.getResourceAsStream(path);
			 BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
		{
			// (JH) Die gepufferten Zeichen befinden sich num im Stream des BufferedReaders.
			// Jetzt beginnt die Stream API ihre Magie zu wirken:
			// readerLines liefert einen Stream von Strings zurueck.
			Stream<String> zeilen = reader.lines();

			// (JH) Die erste Zeile enthaelt die Ueberschrift. Mit skip(1) legen wir einen neuen Stream an
			// ohne die erste Zeile(die Ueberschriften). Streams sind unveraenderlich!
			Stream<String> zeilenOhneUeberschrift = zeilen.skip(1);


			// Jetzt die wichtigste Methode der Stream<API> die map Methode.
			// map verlangt eine Instanz vom Typ function (ein Funktionales Interface).
			// Die map Methode verwandelt die Objekte des Streams in andere Objekte.
			// Jede Zeile hat dann diesen Aufbau z.B.: "200001,Smartphone X1,699.99,TechCorp"
			// Vereinfacht steht da: fuer jede Zeile mache Folgendes
					Stream<Artikel> artikelStream = zeilenOhneUeberschrift.map(zeile ->
					{

						String[] werte = zeile.split(",");

						// Jedes Datum liegt nun als String vor. Wir muessen noch
						// in den richtigen Datentyp konvertieren.
						int artikelNummer = Integer.parseInt(werte[0]);
						String bezeichnung = werte[1];
						double preis = Double.parseDouble(werte[2]);
						String hersteller = werte[3];

						// Jetzt haben wir die Daten um ein Artikel Objekt zu erstellen.
						// Die map Methode wurde so implementiert dass Sie aus Strings zu
						// Artikeln mappt.
						Artikel artikel = new Artikel(
								artikelNummer,
								bezeichnung,
								preis,
								hersteller);

						return artikel;
					});



			// Mit collect() sagen wir. Jetzt beende den Stream und baue uns daraus eine Struktur.
			// collect(toMap()) verwandelt einen Stream in eine Map, indem für jedes Element ein Key und ein Value definiert wird. Alternative mit Lambda statt Methodenreferenzen:
			// Diese Lambda Version ersetzt die Methodenreferenzen: Map<Integer, Artikel> artikelMap = artikelStream.collect( Collectors.toMap( artikel -> artikel.getArtikelNummer(), artikel -> artikel));
			Map<Integer, Artikel> artikelMap = artikelStream.collect(Collectors.toMap(Artikel::getArtikelNummer, Function.identity()));
			// Erklärung:
			// reader.lines() gibt einen Stream auf jede Zeile der Datei zurück. Jede Zeile ist ein Artikel.
			// Mit Ausnahme der ersten Zeile, denn das sind die Überschriften. Die erste Zeile überspringen wir mit skip().
			// mit map() können wir dann jede Zeile in ein Artikel-Objekt umwandeln. Die Rückgabe ist ein Stream auf alle so erstellten Artikel-Objekte. Achtung: Die Methode 'map()' nicht mit der 'Map<Integer, Artikel>' verwechseln! Die Map, oder HashMap, hat nichts mit der Methode 'map()' zu tun.
			// Erst wenn wir collect() aufrufen, machen wir aus den einzelnen Artikeln eine Map mit der Artikelnummer als Key und dem Artikel selbst als Value.
			// Der ganze Ablauf kann wie eine Schleife verstanden werden. lines() iteriert über jede Zeile der Datei, die wir mit map() in einen Artikel umwandeln und mit collect() dann der Map hinzufügen.
			// Dabei ist der erste Wert für den Key, der zweite für den Value. Function.identity() gibt immer den Eingabewert zurück, was hier dann einfach Artikel ist.

			// Wenn wir stattdessen eine Liste wollen, können wir ab Java 16 .toList(); verwenden,
			// oder in früheren Versionen .collect(Collectors.toList());

			return artikelMap; // Die so erstellte Map geben wir dann zurück.
		}
	}

	// JH: Nimmt eine Map von Artikeln und gibt nur die Artikel eines bestimmten Herstellers zurück
	private static Map<Integer, Artikel> filterByHersteller(Map<Integer, Artikel> artikelMap, String hersteller)
	{
		// Wir möchten nach bestimmten Herstellern filtern.
		// Dazu bieten Streams die filter()-Methode an. Hier übergeben wir einen passenden Lambda-Ausdruck, der den Hersteller im Artikel-Objekt mit dem übergebenen Hersteller vergleicht. Das Ergebnis ist wieder ein Stream, bei dem dieser Lambda-Ausdruck für jedes Element true liefert. Mit collect() können wir dann daraus wieder eine Map oder Liste machen und diese zurückgeben.
		return artikelMap.values().stream()
			.filter(artikel -> artikel.getHersteller().equalsIgnoreCase(hersteller))
			.collect(Collectors.toMap(Artikel::getArtikelNummer, Function.identity()));
	}

	private static List<Artikel> orderByPreis(Map<Integer, Artikel> artikelMap)
	{
		// Da HashMaps nicht sortiert sein können (außer nach dem Key bei TreeMap), geben wir hier eine List zurück.
		// Wir nutzen wieder stream() um über alle Values der Map zu iterieren.
		// Mit sorted() geben wir an, nach welchem Kriterium sortiert werden soll.
		// Das Ergebnis wandeln wir mit collect(Collectors.toList()) in eine Liste um, die wir dann zurückgeben.
		return artikelMap.values().stream()
			.sorted(Comparator.comparingDouble(Artikel::getPreis))
			.collect(Collectors.toList());
	}
}
