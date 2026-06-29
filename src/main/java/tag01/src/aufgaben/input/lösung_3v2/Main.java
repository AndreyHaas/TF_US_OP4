package aufgaben.input.lösung_3v2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

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

public class Main
{
    public static void main(String[] args)
    {
        //Eintrag e = new Eintrag(1661156269, "Max Mustermann", InetAddress.getByName("192.168.178.42"), "google.de", true);

        int anzahl = ladeLogDatei();
        System.out.println(anzahl);

        for (Eintrag e : Eintrag.einträge)
        {
            System.out.println(e);
            System.out.println(e.toShortCsv());
            System.out.println(e.toLongCsv());
            System.out.println();
        }

        schreibeCsvDatei();
    }

    private static int ladeLogDatei()
    {
        Path p = Paths.get("resources/log.txt"); // Pfad muss eventuell angepasst werden.
        int anzahl = 0;

        if (Files.exists(p))
        {
            try (BufferedReader reader = Files.newBufferedReader(p))
            {
                String line = null;
                while ((line = reader.readLine()) != null) // Zeilenweise aus der Datei lesen
                {
                    anzahl++;
                    String[] elemente = line.split(" "); // Elemente pro Zeile am Leerzeichen trennen

                    long timestamp = Long.parseLong(elemente[0]); // Timestamp befindet sich immer an Index 0

                    // Der Name befindet sich ab Index 1 bis elemente.length - 3 (exklusiv)
                    StringBuilder username = new StringBuilder();
                    for (String name : Arrays.copyOfRange(elemente, 1, elemente.length - 3))
                    {
                        username.append(name).append(" ");
                    }

                    String ipAddressString = elemente[elemente.length - 3]; // ipAddress befindet sich an vor-vorletzter Stelle.
                    InetAddress ipAddress = InetAddress.getByName(ipAddressString);

                    String url = elemente[elemente.length - 2]; // Url befindet sich immer an vorletzter Stelle.

                    String grantedString = elemente[elemente.length - 1]; // Granted befindet sich immer an der letzten Stelle.
                    boolean granted = grantedString.equalsIgnoreCase("granted");

                    new Eintrag(timestamp, username.toString().trim(), ipAddress, url, granted);
                }
            }
            catch (IOException e)
            {
                System.out.println("Fehler beim Laden der Datei!");
            }
        }
        else
        {
            System.out.println("Datei existiert nicht!");
        }

        return anzahl;

    }

    private static void schreibeCsvDatei()
    {
        Path shortCsv = Paths.get("resources/short.csv");
        Path longCsv = Paths.get("resources/long.csv");

        try
        {
            Files.deleteIfExists(shortCsv);
            Files.deleteIfExists(longCsv);
        }
        catch (IOException e)
        {
            System.out.println("Fehler beim Erstellen der Datei!");
        }


        try (BufferedWriter writerShort = Files.newBufferedWriter(shortCsv, StandardOpenOption.CREATE);
            BufferedWriter writerLong = Files.newBufferedWriter(longCsv, StandardOpenOption.CREATE))
        {
            StringBuilder sbShort = new StringBuilder();
            StringBuilder sbLong = new StringBuilder();
            for (Eintrag e : Eintrag.einträge)
            {
                sbShort.append(e.toShortCsv()).append("\n");
                sbLong.append(e.toLongCsv()).append("\n");
            }

            writerShort.write(sbShort.toString());
            writerLong.write(sbLong.toString());

        }
        catch (IOException e)
        {
            System.out.println("Fehler beim Schreiben der Datei!");
        }
    }
}
