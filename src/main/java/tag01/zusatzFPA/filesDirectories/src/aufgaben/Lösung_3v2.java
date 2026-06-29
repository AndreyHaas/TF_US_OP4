package aufgaben;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/*  Schreiben Sie eine Konsolenanwendung, bei der ein Benutzer zwei Dateipfade angeben kann.
    Die Datei des ersten Pfades wird an die Stelle des zweiten Pfades kopiert.
    Der Dateiname soll aus dem ersten Pfad automatisch übernommen werden.
    Fehler sollen abgefangen und entsprechende Meldungen ausgegeben werden.
*/
public class Lösung_3v2
{
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args)
	{
		Path source;
		Path destination;

		System.out.println("Bitte Quellpfad mit Dateiname eingeben: ");
		source = Paths.get(scanner.nextLine());

		// Wir prüfen, ob die Datei existiert
		if (Files.notExists(source))
		{
			// und wenn sie nicht existiert, beenden wir das Programm direkt, da wir eine Datei, die nicht existiert, auch nicht kopieren können.
			System.out.println("Datei existiert nicht.");
			return;
		}

		System.out.println("Bitte Zielpfad ohne Dateiname eingeben: ");
		String zielpfad = scanner.nextLine();

		// Der Zielpfad muss mit \ oder / am Ende eingegeben werden.
		char letztesZeichen = zielpfad.charAt(zielpfad.length() - 1);
		// Wird er das nicht, fügen wir das Zeichen nachträglich hinzu.
		if (letztesZeichen != '\\' && letztesZeichen != '/')
			zielpfad = zielpfad + File.separator;

		//System.out.println(zielpfad);
		//System.out.println(source.getFileName());

		// Aus dem Pfad mit Dateiname erzeugen wir ein Path-Objekt.
		destination = Paths.get(zielpfad + source.getFileName());

		//System.out.println(destination);

		// Wir prüfen, ob der Zielordner existiert.
		if (Files.notExists(destination.getParent()))
		{
			// Wenn er nicht existiert, fragen wir den User, ob der Ordner erstellt werden soll.
			System.out.println("Zielordner existiert nicht. Soll er erstellt werden?");
			String auswahl = scanner.nextLine();
			if (auswahl.equalsIgnoreCase("ja"))
			{
				try
				{
					Files.createDirectories(destination.getParent());
				}
				catch (IOException e)
				{
					System.out.println("Fehler beim Erstellen.");
					return;
				}
			}
			else
				// Wenn der Zielordner nicht existiert, beenden wir das Programm, da wir nicht an ein Ziel kopieren können, das nicht existiert.
				return;
		}

		try
		{
			Files.copy(source, destination);
			if (Files.exists(destination))
				System.out.println("Erfolgreich.");
		}
		catch (Exception e)
		{
			System.out.println("Fehlgeschlagen.");
		}
	}
}
