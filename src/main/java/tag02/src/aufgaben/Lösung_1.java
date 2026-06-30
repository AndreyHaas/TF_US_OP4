package tag02.src.aufgaben;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/* Schreiben Sie folgendes Java Programm:
 * Dem Benutzer wird ein Menü angezeigt.
 * Beispiel: "Neuer Eintrag (N) | Eintrag Laden (L)"
 * Der Benutzer kann nun durch Eingabe des angezeigten Buchstabens eine Funktion auswählen.
 * Entscheidet er sich für "Neuer Eintrag", so soll er nacheinander bestimmte Informationen eingeben.
 * Dies könnte zum Beispiel "Auftragsnummer", "Bezeichnung", "Datum" und "Preis" sein.
 * Nach erfolgreicher Eingabe aller Daten werden diese in einer Textdatei abgespeichert.
 * Der Name der Datei ist dabei die eingegebene Auftragsnummer.
 * Entscheidet sich der Benutzer für "Eintrag Laden", so werden ihm alle Einträge (also Textdateien)
 * die ihm zur Verfügung stehen, angezeigt.
 * Nach Eingabe der Auftragsnummer wird der Inhalt der Datei ausgegeben.
 */
public class Lösung_1
{
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        Lösung_1 lösung1 = new Lösung_1();

        String funktion;
        do
        {
            System.out.println("Welche Funktion?");
            System.out.println("1 - Neuer Eintrag");
            System.out.println("2 - Eintrag Laden");
            System.out.println("0 - Exit");
            System.out.print("> ");
            funktion = scanner.nextLine();

            switch (funktion)
            {
                case "1":
                    lösung1.neuerEintrag();
                    break;
                case "2":
                    lösung1.list();
                    lösung1.eintragLaden();
                    break;
            }

        } while (!funktion.equals("0"));
    }

    private void neuerEintrag()
    {
        try
        {
            StringBuilder sb = new StringBuilder();

            System.out.println("--NEUER EINTRAG--");

            System.out.print("Auftragsnummer: ");
            String auftragsnummer = scanner.nextLine();
            sb.append(auftragsnummer).append("\n");

            System.out.print("Bezeichnung: ");
            String bezeichnung = scanner.nextLine();
            sb.append(bezeichnung).append("\n");

            System.out.print("Datum (ISO Format): ");
            LocalDate datum = LocalDate.parse(scanner.nextLine());
            sb.append(datum).append("\n");

            System.out.print("Preis: ");
            double preis = Double.parseDouble(scanner.nextLine());
            sb.append(preis).append("\n");

            Path p = Paths.get("resources/" + auftragsnummer + ".txt");
            Files.writeString(p, sb.toString());
        }
        catch (IOException e)
        {
            System.out.println("Fehler beim erstellen der Datei!");
        }
        catch (Exception e)
        {
            System.out.println("Fehler bei der Eingabe!");
        }
    }

    private void list()
    {
        System.out.println("--VORHANDENE EINTRÄGE--");
        try (Stream<Path> pathStream = Files.list(Paths.get("resources")))
        {
            pathStream.forEach((p) ->
            {
                String datei = p.getFileName().toString();
                if (datei.endsWith(".txt"))
                    System.out.println(datei.substring(0, datei.lastIndexOf(".")));
            });
        }
        catch (IOException e)
        {
            System.out.println("Fehler beim Auflisten der vorhandenen Aufträge!");
        }
    }

    private void eintragLaden()
    {
        System.out.println("--EINTRAG LADEN--");
        System.out.print("Auftragsnummer: ");
        String auftragsnummer = scanner.nextLine();

        Path datei = Paths.get("resources/" + auftragsnummer + ".txt");

        if (Files.exists(datei))
        {
            try
            {
                List<String> lines = Files.readAllLines(datei);

                System.out.println("Bezeichnung: " + lines.get(1));
                System.out.println("Datum (ISO Format): " + lines.get(2));
                System.out.println("Preis: " + lines.get(3));
            }
            catch (IOException e)
            {
                System.out.println("Fehler beim Laden der Datei!");
            }
        }
        else
        {
            System.out.println("Datei mit dieser Auftragsnummer existiert nicht!");
        }
    }
}
