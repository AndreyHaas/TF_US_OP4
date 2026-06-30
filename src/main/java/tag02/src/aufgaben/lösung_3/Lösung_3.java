package tag02.src.aufgaben.lösung_3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Lösung_3
{
	public static void main(String[] args)
	{
		try
		{
			List<ArbeitszeitEintrag> einträge = ladeArbeitszeiten("resources/arbeitszeiten.csv");
			einträge.forEach(System.out::println);

			//arbeitszeitJeMitarbeiter(einträge);
			arbeitszeitJeMitarbeiterV2(einträge);

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static List<ArbeitszeitEintrag> ladeArbeitszeiten(String pfad) throws IOException
	{
		// Die Datei können wir bequem mit Files.readAllLines() lesen.
		List<String> zeilen = Files.readAllLines(Paths.get(pfad), StandardCharsets.UTF_8);

		// Jede Zeile der Datei (außer der ersten) beinhaltet einen Arbeitszeit-Eintrag.
		List<ArbeitszeitEintrag> einträge = zeilen.stream().skip(1).map(s -> {
			// Die Daten sind in der Datei mit Semikolon getrennt und werden mit split() auseinandergenommen.
			String[] gesplittet = s.split(";");

			int id = Integer.parseInt(gesplittet[0]);
			String name = gesplittet[1];
			LocalDate datum = LocalDate.parse(gesplittet[2]);
			double stunden = Double.parseDouble(gesplittet[3]);

			// Aus den einzelnen Daten erzeugen wir ein Objekt.
			ArbeitszeitEintrag eintrag = new ArbeitszeitEintrag(id, name, datum, stunden);
			return eintrag;
		}).collect(Collectors.toList());

		// Und geben am Schluss die Liste zurück.
		return einträge;
	}

	private static void arbeitszeitJeMitarbeiter(List<ArbeitszeitEintrag> einträge)
	{
		// Wir benötigen zwei Maps: Eine Map um die Zeiten zu summieren und eine für die Namen der Mitarbeiter. Die Keys sind jeweils die MitarbeiterIDs, damit wir die Zeiten den Mitarbeitern zuordnen können.
		Map<Integer, Double> zeitenJeMitarbeiter = new HashMap<>();
		Map<Integer, String> mitarbeiter = new TreeMap<>();

		// Wir iterieren über alle Arbeitszeiten:
		for (ArbeitszeitEintrag eintrag : einträge)
		{
			int id = eintrag.getMitarbeiterId();
			double stunden = eintrag.getArbeitsstunden();

			// Wenn die ID noch nicht in der Map ist, fügen wir sie hinzu.
			if (!zeitenJeMitarbeiter.containsKey(id))
			{
				zeitenJeMitarbeiter.put(id, stunden);
				// Und fügen den Mitarbeiternamen gleichzeitig auch der entsprechenden Map hinzu.
				mitarbeiter.put(id, eintrag.getName());
			}
			else // Wenn sie schon vorhanden ist, addieren wir die Zeiten.
			{
				double summe = zeitenJeMitarbeiter.get(id) + stunden;
				zeitenJeMitarbeiter.put(id, summe);
			}
		}

		// Ergebnis ausgeben, indem wir über die Mitarbeiter-Map iterieren:
		for (Integer key : mitarbeiter.keySet())
		{
			System.out.println(mitarbeiter.get(key) +
							   " (ID: " + key + "): " +
							   zeitenJeMitarbeiter.get(key) +
							   " Stunden");
		}
	}

	// Alternative mit Stream-API
	private static void arbeitszeitJeMitarbeiterV2(List<ArbeitszeitEintrag> einträge)
	{
		// Nach MitarbeiterID gruppiert
		Map<Integer, List<ArbeitszeitEintrag>> einträgeGruppiert = einträge.stream().collect(Collectors.groupingBy(ArbeitszeitEintrag::getMitarbeiterId));

		// Gruppierungen iterieren, aufsummieren und ausgeben
		einträgeGruppiert.values().forEach(arbeitszeitEinträge -> {
			double summe = arbeitszeitEinträge.stream().mapToDouble(ArbeitszeitEintrag::getArbeitsstunden).sum();
			System.out.println(arbeitszeitEinträge.getFirst().getName() +
			" (ID: " + arbeitszeitEinträge.getFirst().getMitarbeiterId() + "): " +
			summe + " Stunden");
		});
	}
}
