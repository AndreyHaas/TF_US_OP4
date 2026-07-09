package main.java.tag09.aufgabe.dozentLoesung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
 * Client der Superhelden-Anwendung.
 *
 * Aufgabe des Clients:
 *
 * 1. Verbindung zum Server herstellen.
 * 2. Helden-ID eingeben.
 * 3. Antwort des Servers empfangen.
 * 4. Missionsbericht senden.
 * 5. Abschlussmeldung anzeigen.
 */
public class SuperheldenClient
{
    // Server und Client laufen auf dem selben Rechner, beide befinden sich auf dem localhost.
    private static final String SERVER_ADDRESS = "127.0.0.1";

    // Port des Servers
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args)
    {
        SuperheldenClient client = new SuperheldenClient();

        try
        {
            client.connect(SERVER_ADDRESS, SERVER_PORT);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /*
    Baut die Verbindung zum Server auf und fuehrt anschliessend das vereinbarte Kommunikationsprotokoll
    aus.
     */
    private void    connect(String ip, int port) throws IOException
    {
        // Socket stellt die Verbindung zum Server her:
        try(Socket clientSocket = new Socket(ip, port))
        {
            if(clientSocket.isConnected())
            {
                System.out.println("Erfolgreich mit Server verbunden");
            }

            // PrintWriter zum senden von Text
            // BufferedReader empfaengt Text
            try(PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
            {
                Scanner scanner = new Scanner(System.in);

                /*
                 * Erste Nachricht:
                 * Helden-ID eingeben und an den Server senden.
                 */
                System.out.println("Bitte geben Sie Ihre Helden-ID ein: ");
                String heroId = scanner.nextLine();

                // Senden der HeroID an den Server:
                writer.println(heroId);

                // Antwort des Servers lesen:
                String serverResponse = reader.readLine();

                if(serverResponse.contains("ID bestaetigt"))
                {
                    System.out.println("Bitte geben Sie den Missionsbericht ein: ");
                    String missionReport = scanner.nextLine();

                    // Senden des Missionsberichts an den Server:
                    writer.println(missionReport);

                    // Abschlussmeldung des Servers:
                    serverResponse = reader.readLine();
                    System.out.println("Server: " + serverResponse);
                }
                else
                {
                    System.out.println(serverResponse);
                }
            }
        }
    }
}
