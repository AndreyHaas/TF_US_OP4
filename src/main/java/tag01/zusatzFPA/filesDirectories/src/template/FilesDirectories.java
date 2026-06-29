package template;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

// Mit den Klassen File, Path, Files und Paths können wir mit Dateien und Ordnern im Dateisystem arbeiten.
// File ist im Paket java.io, die anderen in java.nio;
// "nio" steht für "non-blocking input output" und ist ab Java 7 verfügbar.
// nio erweitert die Funktionalitäten zum Arbeiten mit Dateien.
public class FilesDirectories
{
    public static void main(String[] args)
    {
        // Erstellt eine einzelne Datei.
        // Achtung: Der Pfad muss angepasst werden, falls der Ordner "resources" nicht existiert (oder der Ordner muss manuell erstellt werden).
        // Hinweis: Unter Run -> Edit Configurations -> Edit Configuration Templates -> Application -> Working Directory -> $MODULE_WORKING_DIR$ einfügen.
        createFile("resources" + File.separator + "FilesAndDirectories.txt");
        // Löscht die Datei.
        delete("resources" + File.separator + "FilesAndDirectories.txt");

        System.out.println(File.separator); // File.separator gibt uns an, welches Trennzeichen für Dateipfade unser System verwendet.
        // Unter Windows ist für gewöhnlich / als auch \ möglich.

        // Das Working Directory lässt sich über System.getProperty("user.dir") ermitteln:
        String userDir = System.getProperty("user.dir");
        System.out.println(userDir);

        // https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
        //System.getProperties().list(System.out);
        System.out.println(System.getProperty("user.home"));

        System.out.println();

        createDirectory("resources/Ordner1");
        createDirectory("resources/Ordner2/Ordner3");

        delete("resources/Ordner1");
        delete("resources/Ordner2/Ordner3");
        // 'delete' löscht immer nur das letzte Element und Überordner bleiben erhalten.
        //delete("resources/Ordner2");
        // Aufpassen! Gelöschte Elemente landen NICHT im Papierkorb!

        // Ab Java 9 können Elemente über java.awt in den Papierkorb verschoben werden.
        //java.awt.Desktop.getDesktop().moveToTrash(new File("resources/Ordner2"));

		// Weitere Funktionen für Dateien und Ordner:
		functions();

        System.out.println();

        // walk: Durchläuft den Dateibaum beginnend bei der angegebenen Datei oder dem Ordner.
        try (Stream<Path> s = Files.walk(Paths.get("resources").toAbsolutePath().getParent()))
        {
            s.forEach(System.out::println);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void createFile(String path)
    {
        // Erzeugt eine neue File-Instanz mit dem angegebenen Pfad.
        // Hinweis: Die Datei existiert in diesem Moment eventuell noch nicht!
        File file = new File(path);

        // Alternative mit dem nio-Gegenstück zu File:
        //Path p = Paths.get(path);

        try
        {
            if (file.createNewFile())
                System.out.println("Datei erstellt: " + file.getName());
            else
                System.out.println("Datei existiert bereits.");

            // Alternative mit Path-Interface:
            // Unterschied: Files.createFile wirft eine Exception, wenn die Datei bereits existiert.
            //Files.createFile(p);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void delete(String path)
    {
        try
        {
            File file = new File(path);
            if (file.exists())
            {
                // Löscht die Datei oder den Ordner sofort (Es können nur leere Ordner gelöscht werden).
                // Es werden dabei keine Überordner gelöscht, sondern immer nur der letzte Ordner im angegebenen Pfad.
                // Achtung: Die Datei wird unwiederbringlich gelöscht. Sie wird NICHT in den Papierkorb verschoben!
                if (file.delete())
                    System.out.println(file.getName() + " wurde gelöscht!");
                else
                    System.out.println(file.getName() + " konnte nicht gelöscht werden!");

                //file.deleteOnExit(); // Markiert eine Datei oder einen Ordner zum Löschen beim Beenden der virtuellen Maschine (JVM).

                // Alternative mit Path-Interface:
                // Unterschied: Hier wird eine Exception geworfen, wenn etwas nicht gelöscht werden kann, z.B. wenn die Datei nicht existiert.
                //Files.delete(file.toPath());

                // Löscht eine Datei oder einen Ordner, wenn es existiert. Es können nur leere Ordner gelöscht werden.
                //Files.deleteIfExists(file.toPath());
            }
            else
                System.out.println(file.getName() + " existiert nicht.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void createDirectory(String path)
    {
        File file = new File(path);
        Path p = Paths.get(path);

        try
        {
            // Prüft, ob die Datei oder das Verzeichnis existiert.
            if (!file.exists())
            {
                //if (file.mkdir()) // 'mkdir' erstellt nur einen Ordner. Wenn der Pfad mehrere Ordner beinhaltet, die nicht existieren, schlägt das Erstellen fehl.
                if (file.mkdirs()) // 'mkdirs' erstellt alle notwendigen Ordner, wenn der Pfad mehrere Ordner beinhaltet, die noch nicht existieren.
                    System.out.println("Ordner erstellt!");
                else
                    System.out.println("Ordner konnte nicht erstellt werden!");

                // Alternative:
                //Files.createDirectory(p); // Erstellt einen Ordner, wirft eine Exception, wenn das nicht möglich ist, weil z.B. die Überordner fehlen.
                //Files.createDirectories(p); // Erstellt alle Ordner, inklusive notwendiger Überordner.
            }
            else
                System.out.println("Ordner existiert bereits!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void functions()
    {
        try
        {
            File f = new File("resources/Examples1.txt");
            Path p = Paths.get("resources/Examples2.txt"); // oder Path.of() ab Java 11

            createFile(f.getPath());
            createFile(p.toFile().getPath());

            // Prüft, ob die Anwendung die angegebene Datei beschreiben kann.
            System.out.println(f.canWrite());
            System.out.println(Files.isWritable(p));

            // Die Schreibberechtigung ändern:
            System.out.println(f.setWritable(false));
            System.out.println(f.canWrite());
            f.setWritable(true);

            // Prüft, ob die Anwendung die angegebene Datei lesen kann:
            System.out.println(f.canRead());
            System.out.println(Files.isReadable(p));

            // Mit 'f.setReadOnly()' kann eine Datei auch als ReadOnly gesetzt werden.

            // Größe der Datei in Bytes:
            System.out.println(f.length());
            System.out.println(Files.size(p));

            // Gibt den absoluten Pfad zurück:
            System.out.println(f.getAbsolutePath()); // Als String
            System.out.println(p.toAbsolutePath()); // Als Path-Objekt

            // Gibt den Pfadnamen des übergeordneten Verzeichnisses zurück:
            System.out.println(f.getParent()); // Als String
            System.out.println(p.getParent().toAbsolutePath().getParent().getParent()); // Als Path-Objekt

            // Wurzel der Verzeichnisse abfragen:
            for (File fRoot : File.listRoots())
                System.out.println(fRoot);
            System.out.println(p.toAbsolutePath().getRoot());

			// Datei verschieben:
			// 'REPLACE_EXISTING' überschreibt das Ziel, wenn es schon existiert.
			p = Files.move(p, Paths.get("resources\\Ordner2\\" + p.getFileName()), StandardCopyOption.REPLACE_EXISTING);
			// Datei kopieren:
			Path fCopy = Files.copy(f.toPath(), Paths.get("resources\\Ordner2\\" + f.getName()), StandardCopyOption.REPLACE_EXISTING);

			delete(f.toString()); // Examples1.txt am Quellverzeichnis löschen.
			delete(p.toString()); // Die Datei wurde durch Files.move verschoben und das Path-Objekt durch die Rückgabe ersetzt.
			delete(fCopy.toString()); // Die Kopie von Examples1.txt löschen

            // Weitere interessante Klassen sind FileSystem und FileSystems:
            FileSystem fs = FileSystems.getDefault();
            System.out.println(fs);
            fs.getRootDirectories().forEach(System.out::println);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
