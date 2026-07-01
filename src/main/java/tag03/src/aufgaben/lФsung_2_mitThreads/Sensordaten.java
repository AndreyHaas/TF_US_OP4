package main.java.tag03.src.aufgaben.lФsung_2_mitThreads;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/*
Schreiben Sie folgende Server-Client-Anwendung:

Auf der Client-Seite werden Messdaten erzeugt, die als Objekte vom Typ 'Sensordaten' gespeichert werden.
Dabei beinhaltet jedes Objekt ein Array mit den Messwerten, eine Bezeichnung als String und das Datum der Erzeugung mit Uhrzeit.
Objekte dieser Klasse werden über einen 'ObjectOutputStream' an den Server geschickt, welcher die Daten mit 'ObjectInputStream' empfängt.
Dazu muss 'Sensordaten' das Interface 'Serializable' implementieren.

Nach Verbindungsaufbau zum Server fragt der Client per Eingabe, ob Daten erzeugt werden sollen. Wird dies bestätigt, wird ein Objekt von 'Sensordaten' erstellt und an den Server gesendet. Danach kann wieder entschieden werden, ob weitere Daten erzeugt werden sollen.
Der Server empfängt die Daten so lange, bis die Verbindung vom Client beendet wird. Anschließend kann sich ein neuer Client mit dem Server verbinden.
Alle empfangenen Objekte werden in einer Liste gespeichert.

Wird der Server beendet, werden die in der Liste gespeicherten Sensordaten ausgegeben.

 */



// Damit Objekte Serialisiert werden können, muss Serializable implementiert sein.
public class Sensordaten implements Serializable
{
    private int[] daten;
    private String bezeichnung;
    private LocalDateTime zeit;

    // Zum Serialisieren müssen Getter vorhanden sein.
    public int[] getDaten()
    {
        return daten;
    }

    // Zum De-Serialisieren müssen Setter und ein parameterloser Konstruktor vorhanden sein.
    public void setDaten(int[] daten)
    {
        this.daten = daten;
    }

    public String getBezeichnung()
    {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung)
    {
        this.bezeichnung = bezeichnung;
    }

    public LocalDateTime getZeit()
    {
        return zeit;
    }

    public void setZeit(LocalDateTime zeit)
    {
        this.zeit = zeit;
    }

    public Sensordaten()
    {
    }

    public Sensordaten(int[] daten, String bezeichnung, LocalDateTime zeit)
    {
        this.daten = daten;
        this.bezeichnung = bezeichnung;
        this.zeit = zeit;
    }

    // toString() für einfache Ausgabe.
    @Override
    public String toString()
    {
        return "Sensordaten { " +
            "daten = " + Arrays.toString(daten) +
            ", bezeichnung = '" + bezeichnung + '\'' +
            ", zeit = " + zeit.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")) +
            " }";
    }
}
