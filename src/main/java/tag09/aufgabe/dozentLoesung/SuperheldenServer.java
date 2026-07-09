package main.java.tag09.aufgabe.dozentLoesung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*
    Erstellen Sie folgende Client-Server Anwendung:
Sie managen eine Gruppe von Superhelden, die ihre abgeschlossenen Rettungsmissionen über ein Terminal melden sollen.
Erstellen Sie eine Server-Anwendung, die eine eingehende Verbindung von Clients akzeptiert und eine Bestätigung
über den erfolgreichen Abschluss der Mission erwartet. Der Superheld (Client) sendet den Abschlussbericht seiner Mission
an den Server.

Der Server speichert den Bericht in einer Datei "MissionBericht.txt". Der Superheld bestätigt den Abschluss der Mission
durch Eingabe seiner Helden-ID. Diese Helden-ID wird vom Client an den Server geschickt. Stimmt die ID, wird der Bericht
gespeichert und dem Superhelden vom Server gratuliert.

(Die ID können Sie fest im Server einprogrammieren, z.B. "1234".)

Beispiel "MissionBericht.txt"
Held: Superman
Mission: Erfolgreich abgeschlossen
Details: Die Stadt wurde vor dem Meteor gerettet und alle Bürger sind sicher.

     */

/*
 * Server einer einfachen Client-Server-Anwendung.
 *
 * Aufgabe des Servers:
 *
 * 1. Auf eingehende Verbindungen warten.
 * 2. Die Helden-ID des Clients empfangen.
 * 3. Die Helden-ID überprüfen.
 * 4. Bei gültiger ID den Missionsbericht empfangen.
 * 5. Den Bericht in einer Datei speichern.
 * 6. Dem Client eine Bestätigung senden.
 *
 * Der Server startet zuerst und wartet anschließend,
 * bis sich ein Client verbindet.
 */
public class SuperheldenServer
{
    // Port, auf dem der Server lauscht:
    private static final int PORT = 12345;

    // Gueltige HeroID:
    private static final String VALID_HERO_ID = "1234";

    // Speicherort der Missionsberichte. Hier ein relativer Pfad als String.
    private static final String MISSION_REPORT_FILE = "resources/MissionsBericht.txt";
    public static void main(String[] args)
    {
        SuperheldenServer server = new SuperheldenServer();
        try
        {
            server.start(PORT);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void start(int port) throws IOException
    {
        try(ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server laeuft und wartet auf Verbindungen...");

            // accept() blockiert den Thread.
            // Der Server wartet hier so lange, bis sich ein Client verbindet.
            try(Socket clientSocket = serverSocket.accept())
            {
                if(clientSocket.isConnected())
                {
                    System.out.println("Client verbunden");
                }

                // BufferedReader empfaengt Textnachrichten.
                // PrintWriter versendet Textnachrichten ueber ein Netzwerk.
                try(PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
                {
                    // Die erste Nachricht des Clients enthaelt die Helden-ID
                   String heroId = reader.readLine();

                   // IDs vergleichen:
                   if(VALID_HERO_ID.equals(heroId))
                   {
                       writer.println("ID bestaetigt. Bitte senden Sie den Missionsbericht.");

                       String missionReport = reader.readLine();
                       saveMissionReport(missionReport);

                       writer.println("Bericht gespeichert. Gratulation zur erfolgreichen Mission!");
                   }
                   else
                   {
                       writer.println("Fehler die Hero ID war nicht korrekt.");
                   }
                }
            }
        }
    }

    /*
     * Speichert den Missionsbericht in einer Textdatei.
     *
     * Existiert der Ordner noch nicht,
     * wird dieser automatisch erstellt.
     *
     * Existiert die Datei bereits,
     * wird der neue Bericht angehängt.
     */
    private void saveMissionReport(String missionReport)
    {
        Path path = Paths.get(MISSION_REPORT_FILE);

        try{
            // Erzeugt neues Verzeichnis resources wenn es noch nicht existiert:
            Files.createDirectories(path.getParent());

            // Erzeugt die Datei im resources Ordner, wenn sie noch nicht existiert, ansonsten
            // wird der Text einfach angehaengt am Inhalt der bestehenden Datei:
            Files.writeString(path, missionReport + "\n-------------------------\n", StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
        catch(IOException e)
        {
            System.err.println("Fehler beim Speichern des Berichts " + e.getMessage());
        }
    }
}
