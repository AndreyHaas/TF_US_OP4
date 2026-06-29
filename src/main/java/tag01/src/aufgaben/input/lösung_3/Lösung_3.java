/*
Wir haben ein log, in dem die Zugriffe einzelner User protokolliert werden. Es wird der Timestamp(UNIX), Username, IP, URL sowie ob der Zugriff gewährt oder verweigert wurde gespeichert.

Beispiel:
1661156269 Max Mustermann 192.168.178.42 google.de granted
1661156577 Erika Mustermann 192.168.178.47 youtube.de denied

Datei einlesen und als neue Datei mit folgenden Werten als short.csv (delimiter ,) ausgeben:
Username, IP, Zugriff

und als long.csv (delimiter ,):
Timestamp(humanreadable), Username, IP, URL, Zugriff

Eine weitere Datei, in der aufgelistet wird, wie oft welcher User der Zugriff verweigert wurde.
 */

package tag01.src.aufgaben.input.lösung_3;

import java.io.*;
import java.nio.file.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Lösung_3
{
    public static void main(String[] args)
    {
        try
        {
            ladeProtokoll("resources/input/protokoll.txt");

            Eintrag.getEinträge().forEach(System.out::println);

            erstelleCSV("resources/input");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void ladeProtokoll(String pfad) throws Exception
    {
        // try(BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(pfad)))))
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(pfad)))
        {
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                String[] elements = line.split(" ");
                LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(elements[0])), ZoneId.systemDefault());

                StringBuilder name = new StringBuilder();

                // Die Namen in der Datei könnten aus mehreren Vornamen und Nachnamen bestehen.
                // Damit wir alle Namen dynamisch abfragen können, starten wir bei Index 1
                // und laufen so lange durch das Array, bis bei length - 3 die IP beginnt.
                // Alles dazwischen gehört dann zum Namen.
                for (int i = 1; i < elements.length - 3; i++)
                {
                    name.append(elements[i]).append(" ");
                }

                String IP = elements[elements.length-3]; // Die IP steht im Array bei length-3
                String adresse = elements[elements.length-2]; // Die Adresse steht im Array bei length-2
                Eintrag.Ergebnis ergebnis = Eintrag.Ergebnis.valueOf(elements[elements.length-1].toUpperCase());

                new Eintrag(timestamp, name.toString().trim(), IP, adresse, ergebnis);

            }
        }
    }

    public static void erstelleCSV(String pfadZumInputOrdner) throws Exception
    {

        Path ordner = Paths.get(pfadZumInputOrdner + "/output");
        if (!Files.exists(ordner))
        {
            Files.createDirectories(ordner);
        }

        Path shortCSV = Paths.get(pfadZumInputOrdner + "/output/short.csv");
        Path longCSV = Paths.get(pfadZumInputOrdner + "/output/long.csv");

        Files.deleteIfExists(shortCSV);
        try (BufferedWriter writer = Files.newBufferedWriter(shortCSV, StandardOpenOption.CREATE, StandardOpenOption.WRITE))
        {
            writer.write("Username,IP,Zugriff");
            writer.newLine();
            for (Eintrag e : Eintrag.getEinträge())
            {
                writer.write(e.toShortString());
                writer.newLine();
            }
        }

        Files.deleteIfExists(longCSV);
        try (BufferedWriter writer = Files.newBufferedWriter(longCSV, StandardOpenOption.CREATE, StandardOpenOption.WRITE))
        {
            writer.write("Timestamp(humanreadable),Username,IP,URL,Zugriff");
            writer.newLine();
            for (Eintrag e : Eintrag.getEinträge())
            {
                writer.write(e.toString());
                writer.newLine();
            }
        }
    }
}

