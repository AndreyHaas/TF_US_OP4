package tag01.src.aufgaben.input.lösung_3v3;

/*
Wir haben eine Log-Datei, in der die Zugriffe einzelner User protokolliert werden.
Es wird der Timestamp(UNIX), Username, IP, URL sowie ob der Zugriff gewährt oder verweigert wurde gespeichert.

Beispiel:
1661156269 Max Mustermann 192.168.178.42 google.de granted
1661156577 Erika Mustermann 192.168.178.47 youtube.de denied

Die Datei soll eingelesen und als neue Datei mit folgenden Werten als short.csv (delimiter ,) ausgeben werden:
Username, IP, Zugriff
Beispiel:
Max Mustermann,192.168.178.42,granted

und als long.csv (delimiter ,):
Timestamp(humanreadable), Username, IP, URL, Zugriff
Beispiel:
2022-08-22T08:22:57,Erika Mustermann,192.168.178.47,youtube.de,denied


Möglicher Lösungsansatz:
Sie Erstellen eine Klasse "Eintrag", in der die Informationen des Protokolls geladen werden.
Die Klasse beinhaltet zwei Methoden, welche die Daten passend für short.csv und long.csv ausgeben können.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Lösung_3
{
	public static void main(String[] args)
	{
		try
		{
			List<Eintrag> einträge = dateiLaden("resources/log.txt");
			/*for (Eintrag e : einträge)
			{
				System.out.println(e.toShortCSV());
				System.out.println(e.toLongCSV());
			}*/

			schreibeShortCSV("resources/short.csv", einträge);
			schreibeLongCSV("resources/long.csv", einträge);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static List<Eintrag> dateiLaden(String pfad) throws IOException
	{
		Path p = Paths.get(pfad);
		List<Eintrag> einträge = new ArrayList<>();

		List<String> lines = Files.readAllLines(p);

		for (String l : lines)
		{
			String[] arr = l.split(" ");
			Instant timestamp = Instant.ofEpochSecond(Long.parseLong(arr[0]));
			String username = arr[1] + " " + arr[2];
			InetAddress ip = InetAddress.getByName(arr[3]);
			URI url = URI.create(arr[4]);
			Zugriff zugriff = Zugriff.valueOf(arr[5].toUpperCase());

			Eintrag e = new Eintrag(timestamp, username, ip, url, zugriff);
			einträge.add(e);
		}

		return einträge;
	}

	public static void schreibeShortCSV(String pfad, List<Eintrag> einträge) throws IOException
	{
		try (FileWriter writer = new FileWriter(pfad))
		{
			for (Eintrag e : einträge)
				writer.write(e.toShortCSV() + "\n");
		}
	}

	public static void schreibeLongCSV(String pfad, List<Eintrag> einträge) throws IOException
	{
		try (FileWriter writer = new FileWriter(pfad))
		{
			for (Eintrag e : einträge)
				writer.write(e.toLongCSV() + "\n");
		}
	}
}
