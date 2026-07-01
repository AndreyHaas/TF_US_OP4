package main.java.tag03.src.aufgaben.lФsung_3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Client
{
	public static void main(String[] args)
	{
		Client c = new Client();
		try
		{
			c.connect("localhost", 1234);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void connect(String ip, int port) throws IOException
	{
		try (Socket client = new Socket(ip, port))
		{
			// Die Wetterdaten sind in einer CSV-Datei gespeichert.
			// Mit 'readAllLines()' lesen wir die ganze Datei ein und erhalten eine Liste von Strings.
			List<String> lines = Files.readAllLines(Paths.get("resources/wetterdaten.csv"), StandardCharsets.UTF_8);

			// Wir wollen die Daten als Integer-Array an den Server schicken.
			// Dazu müssen wir die Strings in int[] umwandeln.
			// Die erste Zeile überspringen wir mit 'skip(1)', da dort nur die Überschriften sind.
			// Jede weitere Zeile wandeln wir mit 'map()' in int[] um.
			// Da wir potenziell mehrere Zeilen an Daten haben, erhalten wir als Ergebnis eine Liste von Integer-Arrays.
			List<int[]> wetterdaten = lines.stream().skip(1).map(line ->
			{
				String[] values = line.split(",");
				return Arrays.stream(values).mapToInt(Integer::parseInt).toArray();
			}).toList();

			// Mit ObjectOutput- und ObjectInputStream können die Daten an den Server gesendet und die Ergebnisse empfangen werden.
			ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(client.getInputStream());

			// Die Werte werden an den Server gesendet.
			output.writeObject(wetterdaten.get(0));

			// Die Ergebnisse werden empfangen.
			double durchschnitt = input.readDouble();
			int min = input.readInt();
			int max = input.readInt();

			System.out.println("Durchschnitt: " + durchschnitt);
			System.out.println("Min: " + min);
			System.out.println("Max: " + max);
		}
	}
}
