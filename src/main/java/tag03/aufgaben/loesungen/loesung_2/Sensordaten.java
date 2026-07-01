package main.java.tag03.aufgaben.loesungen.loesung_2;

/*
Schreiben Sie folgende Server-Client-Anwendung:

Auf der Client-Seite werden Messdaten erzeugt, die als Objekte vom Typ 'Sensordaten' gespeichert werden.
Dabei beinhaltet jedes Objekt ein Array mit den Messwerten, eine Bezeichnung als String und das Datum der Erzeugung mit Uhrzeit.
Objekte dieser Klasse werden über einen 'ObjectOutputStream' an den Server geschickt, welcher die Daten mit 'ObjectInputStream' empfängt.
Dazu muss 'Sensordaten' das Interface 'Serializable' implementieren.

Nach Verbindungsaufbau zum Server fragt der Client per Eingabe, ob Daten erzeugt werden sollen. Wird dies bestätigt, wird ein Objekt von 'Sensordaten' erstellt und an den Server gesendet. Danach kann wieder entschieden werden, ob weitere Daten erzeugt werden sollen.
Der Server empfängt die Daten so lange, bis die Verbindung vom Client beendet wird. Anschließend kann sich ein neuer Client mit dem Server verbinden.

Die übertragenen Daten eines Clients werden in einer Liste gespeichert.
Beendet ein Client die Verbindung, gibt der Server alle übertragenen Daten des Clients aus.

 */

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;

public class Sensordaten implements Serializable
{
    private int[] messwerte;
    private String bezeichnung;
    private LocalDateTime datum;

    public Sensordaten(int[] messwerte, String bezeichnung, LocalDateTime datum)
    {
        this.messwerte = messwerte;
        this.bezeichnung = bezeichnung;
        this.datum = datum;
    }

    @Override
    public String toString()
    {
        return "Sensordaten{" +
            "messwerte=" + Arrays.toString(messwerte) +
            ", bezeichnung='" + bezeichnung + '\'' +
            ", datum=" + datum.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) +
            '}';
    }
}
