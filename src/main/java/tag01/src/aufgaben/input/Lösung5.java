package tag01.src.aufgaben.input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Lösung5
{
	public static void main(String[] args)
	{
		int errorCount = 0;

		// Logdatei lesen und Fehler zählen
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("resources/server.log")))
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				if (line.contains("ERROR"))
				{
					errorCount++;
				}
			}
		}
		catch (IOException e)
		{
			System.err.println("Fehler beim Lesen der Logdatei: " + e.getMessage());
		}

		// Ergebnisse in Datei schreiben
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("resources/errorSummary.txt")))
		{
			writer.write("Anzahl der Fehler: " + errorCount);
			writer.newLine();
			writer.write("Analyse durchgeführt am: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		}
		catch (IOException e)
		{
			System.err.println("Fehler beim Schreiben der Ergebnisdatei: " + e.getMessage());
		}
	}
}
