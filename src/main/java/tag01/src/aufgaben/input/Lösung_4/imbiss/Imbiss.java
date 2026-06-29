package aufgaben.input.Lösung_4.imbiss;

/* Sie betreiben einen Imbiss und möchten eine Verwaltungssoftware für die verkauften Waren erstellen.
 * Die Verkaufsware unterteilt sich in Speisen und Getränke. Alle Waren werden in der Basisklasse, von der kein Objekt erstellt werden darf, in einer Liste gespeichert.
 * Für alle Waren erfassen Sie die Bezeichnung und den Preis. Speisen bestehen zudem aus einer Auflistung der Zutaten, zu Getränken speichern Sie die Füllmenge in Milliliter.
 * Alle benötigten Daten werden über Konstruktoren erfasst.
 * Um alle Waren bequem ausgeben zu können, überschreiben Sie die toString-Methode der Klassen. Dabei vermeiden Sie doppelten Code und beachten die Abkapselung und Trennung von Darstellung und Programmlogik.
 *
 * Die Daten zu den Verkaufswaren sollen bei Start des Programmes aus .csv-Dateien geladen werden. Beispiel:
 * Speisen.csv
 Burger,4.99,Brötchen,Rindfleisch-Patties,Gurken,Ketchup
 Pizza Groß,5.99,Pizzateig,Tomatensoße,Käse,Salami
 * Getränke.csv
 Cola,1.49,500
 Fanta,1.49,500
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Imbiss
{
    public static void main(String[] args)
    {
        // Objekte erzeugen
        /*new Speise("Burger", 4.99, new ArrayList<String>(Arrays.asList("Brötchen", "Rindfleisch-Patties", "Gurken", "Ketchup")));
        new Speise("Pizza Groß", 5.99, new ArrayList<String>(Arrays.asList("Pizzateig", "Tomatensoße", "Käse", "Salami" )));

        new Getränk("Cola", 1.49, 500);
        */

        try
        {
            ladeSpeisen();
            ladeGetränke();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Ausgabe
        for (Verkaufsware ware : Verkaufsware.warenliste)
        {
            System.out.println(ware); // Die toString()-Methode wird automatisch von print und println aufgerufen!
        }
    }

    public static void ladeSpeisen() throws IOException
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("resources/Speisen.csv")))
        {
            String line;
            String[] array;
            String bezeichnung;
            double preis;
            ArrayList<String> zutaten;

            while (reader.ready())
            {
                line = reader.readLine();
                //System.out.println(line);

                array = line.split(",");
                bezeichnung = array[0];
                preis = Double.parseDouble(array[1]);

                zutaten = new ArrayList<>();

                for (int i = 2; i < array.length; i++)
                    zutaten.add(array[i]);

                new Speise(bezeichnung, preis, zutaten);

            }
        }
    }

    public static void ladeGetränke() throws IOException
    {
        Path file = Paths.get("resources/Getränke.csv");

        Files.readAllLines(file).forEach(line -> {
            String[] array = line.split(",");
            new Getränk(array[0], Double.parseDouble(array[1]), Integer.parseInt(array[2]));
        });
    }
}

