package aufgaben;

/*  Schreiben Sie eine Konsolenanwendung, bei der ein Benutzer zwei Dateipfade angeben kann.
    Die Datei des ersten Pfades wird an die Stelle des zweiten Pfades kopiert.
    Der Dateiname soll aus dem ersten Pfad automatisch übernommen werden.
    Fehler sollen abgefangen und entsprechende Meldungen ausgegeben werden.
*/

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class Lösung_3
{
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        System.out.println("Geben Sie die Quelldatei inklusive Pfad an:");
        String quelle = scanner.nextLine();
        System.out.println("Geben Sie den Zielpfad an:");
        String ziel = scanner.nextLine();

        String filename = quelle.substring(quelle.lastIndexOf('\\'));

        if (ziel.charAt(ziel.length()-1) == '\\')
            ziel = ziel + filename;
        else
            ziel = ziel + "\\" + filename;

        Path pQuelle = Paths.get(quelle);
        Path pZiel = Paths.get(ziel);

        if (!pQuelle.toString().equals(pZiel.toString()))
        {
            try
            {
                Files.copy(pQuelle, pZiel);
                System.out.println("Erledigt.");
            }
            catch (IOException e)
            {
                System.out.println("Fehlgeschlagen!");
                System.out.println(e);
            }
        }
        else
        {
            System.out.println("Quelle und Ziel sind identisch!");
        }

    }
}
