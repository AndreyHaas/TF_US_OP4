package tag01.zusatzFPA.filesDirectories.src.aufgaben.lösung_2;

import java.util.Scanner;

public class Lösung_2
{
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args)
	{
		String auswahl;
		String path;

		do
		{
			System.out.println("0: exit");
			System.out.println("1: Ordner erstellen");
			System.out.println("2: Inhalt auflisten");
			System.out.print("Auswahl: ");

			auswahl = scanner.nextLine();

			switch (auswahl)
			{
				case "0":
					System.out.println("bye");
					break;
				case "1":
					System.out.print("Bitte Pfad eingeben: ");
					path = scanner.nextLine();
					System.out.println(DirectoryHandling.createDirectory(path) ? "Erfolgreich" : "Fehlgeschlagen");
					break;
				case "2":
					System.out.print("Bitte Pfad eingeben: ");
					path = scanner.nextLine();

					// Gibt den ganzen Inhalt aus
					DirectoryHandling.listFilesInDirectory(path).forEach((file -> System.out.println(file.getName())));

					// Gibt nur die Dateien aus, aber nicht die Ordner
					/*DirectoryHandling.listFilesInDirectory(path).forEach((file -> {
						if (file.isFile())
							System.out.println(file.getName());
					}));*/
					break;
				default:
					System.out.println("Falsche Eingabe");
			}

		} while(!auswahl.equals("0"));
	}
}
