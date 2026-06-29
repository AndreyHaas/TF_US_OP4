package tag01.zusatzFPA.filesDirectories.src.aufgaben.lösung_2;

import java.io.File;
import java.util.List;

/*
Erstellen Sie eine Klasse namens "DirectoryHandling", die folgende Methoden enthält:

1. Eine Methode mit dem Namen "createDirectory", die einen neuen Ordner an einem bestimmten Pfad erstellt.
2. Eine Methode mit dem Namen "listFilesInDirectory", die alle Dateien in einem vorhandenen Ordner auflistet und zurückgibt.

Zum Auflisten der Dateien können list() oder listFiles() der File-Instanz verwendet werden.
Schreiben Sie anschließend eine Main-Methode, um Ihre Methoden aufzurufen und testen Sie sie anhand von Beispielordnern, deren Pfade als Parameter übergeben werden können.
 */
public class DirectoryHandling
{
	public static boolean createDirectory(String path)
	{
		File f = new File(path);

		return f.mkdirs();
	}

	/**
	 * Erzeugt eine Liste des Inhalts eines angegebenen Ordners und gibt diese Liste zurück.
	 * @param path Der Pfad zum Ordner, dessen Inhalt aufgelistet werden soll.
	 * @return Eine File-Liste mit dem Inhalt des Ordners. Ist der Ordner leer, ist auch die Liste leer.
	 * @throws IllegalArgumentException Wenn der übergebene String keinen Ordner darstellt.
	 */
	public static List<File> listFilesInDirectory(String path) throws IllegalArgumentException
	{
		File f = new File(path);

		if (f.isDirectory())
		{
			File[] fArray = f.listFiles();

			assert fArray != null;
			return List.of(fArray);
			//return Arrays.asList(fArray);
		}
		else
		{
			//throw new NotDirectoryException(path);
			throw new IllegalArgumentException(path + " is not a Directory.");
		}
	}
}
