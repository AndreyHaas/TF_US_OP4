package tag01.src.aufgaben.input.lösung_4v2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Imbiss
{
	public static void main(String[] args)
	{
		try
		{
			List<Speise> speisen = ladeSpeisen("resources/Speisen.csv");
			speisen.forEach(System.out::println);
			System.out.println();

			List<Getränk> getränke = ladeGetränke("resources/Getränke.csv");
			getränke.forEach(System.out::println);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// Java-Doc (Dokumentation)
	/**
	 * Methode zum Laden der Speisen aus einer CSV-Datei.
	 * @param pfad Der Pfad zur Datei.
	 * @return Eine Liste mit Speise-Objekten.
	 * @throws IOException Wenn beim Laden der Datei etwas schief läuft.
	 */
	public static List<Speise> ladeSpeisen(String pfad) throws IOException
	{
		// Wir wollen die Speise-Objekte in einer Liste zurückgeben, also erzeugen wir eine ArrayList.
		List<Speise> speisen = new ArrayList<>();

		// Mit readAllLines lesen wir alle Zeilen aus der CSV. In der CSV-Datei sind die Speisen jeweils in Zeilen gespeichert. Jede Zeile eine Speise.
		List<String> lines = Files.readAllLines(Paths.get(pfad), StandardCharsets.UTF_8);

		// Diese Zeilen wollen wir in Speise-Objekte umwandeln, also iterieren wir über die Zeilen.
		for (String line : lines)
		{
			// Die einzelnen Informationen sind in den Zeilen durch Komma getrennt. Wir teilen eine Zeile mit split() in ihre einzelnen Bestandteile auf.
			String[] splitted = line.split(",");

			// Aus dem daraus resultierenden Array entnehmen wir die einzelnen Informationen über die Angabe des Indexes.
			// Index 0 ist die Bezeichnung, Index 1 der Preis, der Rest sind die Zutaten.
			String bezeichnung = splitted[0];
			double preis = Double.parseDouble(splitted[1]);
			List<String> zutaten = new ArrayList<>();
			for (int i = 2; i < splitted.length; i++)
			{
				zutaten.add(splitted[i]);
			}
			// Aus den einzelnen Informationen erzeugen wir das Speise-Objekt und fügen es der Liste hinzu.
			Speise s = new Speise(bezeichnung, preis, zutaten);
			speisen.add(s);
		}

		// Zum Schluss geben wir die Liste zurück.
		return speisen;
	}

	public static List<Getränk> ladeGetränke(String pfad) throws IOException
	{
		List<Getränk> getränke = new ArrayList<>();

		List<String> lines = Files.readAllLines(Paths.get(pfad), StandardCharsets.UTF_8);

		for (String line : lines)
		{
			String[] splitted = line.split(",");
			String bezeichnung = splitted[0];
			double preis = Double.parseDouble(splitted[1]);
			int füllmenge = Integer.parseInt(splitted[2]);

			Getränk g = new Getränk(bezeichnung, preis, füllmenge);
			getränke.add(g);
		}

		return getränke;
	}
}
