package tag01.zusatzFPA.filesDirectories.src.aufgaben;

/*
Erstellen Sie eine Klasse namens "DirectoryHandling", die folgende Methoden enthält:

1. Eine Methode mit dem Namen "createDirectory", die einen neuen Ordner an einem bestimmten Pfad erstellt.
2. Eine Methode mit dem Namen "listFilesInDirectory", die alle Dateien in einem vorhandenen Ordner auflistet und zurückgibt.

Zum Auflisten der Dateien können list() oder listFiles() der File-Instanz verwendet werden.
Schreiben Sie anschließend eine Main-Methode, um Ihre Methoden aufzurufen und testen Sie sie anhand von Beispielordnern, deren Pfade als Parameter übergeben werden können.
 */

// Hinweis: Dieser Lösungsvorschlag ist wesentlich komplexer als er sein müsste. Bei Unklarheiten bitte ins Tutoring-Gebäude kommen oder im Unterricht nachfragen.

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lösung_2_komplex
{
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        Lösung_2_komplex lösung_2 = new Lösung_2_komplex();
        lösung_2.run();
    }

    private enum Locations
    {
        DESKTOP(),
        WORKING_DIR();

        private final String path;

        Locations()
        {
            if (this.ordinal() == 0)
            {
                FileSystemView view = FileSystemView.getFileSystemView();
                File file = view.getHomeDirectory();
                path = file.getPath();
            }
            else
            {
                path = System.getProperty("user.dir");
            }
        }
    }

    private void createDirectory(Locations ort)
    {
        String sPfad = ort.path;
        sPfad += File.separator;
        System.out.print("Bitte geben Sie den Verzeichnisnamen an der im Pfad \"" + sPfad + "\" erstellt werden soll: ");
        sPfad += scanner.nextLine();
        try
        {
            Path pfad = FileSystems.getDefault().getPath(sPfad);
            if (Files.notExists(pfad))
            {
                Files.createDirectory(pfad);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void listFilesInDirectory(File file)
    {
        List<File> list = new ArrayList<>();

        filesInDirectoryToList(list, file);

        for (File f : list)
        {
            System.out.println(f.getAbsolutePath());
        }
    }

    /**
     * Fügt alle Dateien und Unterordner eines Verzeichnisses einer Liste hinzu.
     *
     * @param files Liste vom Typ File, die die einzelnen gefundenen Dateien aufnimmt
     * @param file  Objekt vom Typ File welches die eben bearbeitetet "Datei" enthält<br>
     *              ist es ein Verzeichnis erfolgt ein rekursiver Aufruf dieser Funktion<br>
     *              ist es kein Verzeichnis wird das DateiObjekt der Liste hinzugefügt und die Methode kehrt zum Aufrufer zurück<br>
     */
    private void filesInDirectoryToList(List<File> files, File file)
    {
        if (file.isDirectory())
        {
            File[] dateien = file.listFiles();
            if (dateien != null)
            {
                for (File f : dateien)
                {
                    filesInDirectoryToList(files, f);
                }
            }
        }
        else
        {
            files.add(file);
        }
    }


    public void run()
    {
        int iAuswahl;
        do
        {
            System.out.println("Herzlich willkommen im Programm **DirectoryHandling**\n Was möchten Sie tun:");
            System.out.println("1 --> Ein Verzeichnis im Pfad " + Locations.DESKTOP.path + " erstellen");
            System.out.println("2 --> Ein Verzeichnis im Pfad " + Locations.WORKING_DIR.path + " erstellen");
            System.out.println("3 --> Alle Dateien im Verzeichnis " + Locations.DESKTOP.path + " auslesen");
            System.out.println("4 --> Alle Dateien im Verzeichnis " + Locations.WORKING_DIR.path + " auslesen");
            System.out.println("5 --> Programm beenden");
            System.out.print("> ");
            iAuswahl = new Scanner(System.in).nextInt();

            switch (iAuswahl)
            {
                case 1:
                    createDirectory(Locations.DESKTOP);
                    break;
                case 2:
                    createDirectory(Locations.WORKING_DIR);
                    break;
                case 3:
                    listFilesInDirectory(new File(Locations.DESKTOP.path));
                    break;
                case 4:
                    listFilesInDirectory(new File(Locations.WORKING_DIR.path));
                    break;
            }
        } while (iAuswahl != 5);
    }
}
