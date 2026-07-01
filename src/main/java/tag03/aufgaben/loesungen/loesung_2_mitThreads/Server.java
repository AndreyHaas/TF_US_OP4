package main.java.tag03.aufgaben.loesungen.loesung_2_mitThreads;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server
{
    private final List<Sensordaten> sensordatenList = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        Server server = new Server();
        // Server in eigenem Thread starten. (Andere Lösungen möglich)
        // Dadurch kann der Main-Thread auf eine Eingabe warten und nach korrekter Eingabe das Programm beenden.
        Thread t = new Thread(server::start);
        t.setDaemon(true); // Hintergrund-Thread, damit er beendet wird, wenn das Programm endet.
        t.start(); // Alternativ CompletableFuture mit ForkJoinPool verwenden.

        // Main-Thread wartet auf die Eingabe von "exit"...
        while(!scanner.nextLine().equalsIgnoreCase("exit"))
        {
            System.out.println("('exit' zum Beenden)");
        }

        // Es werden alle übermittelten Daten ausgegeben.
        server.sensordatenList.forEach(System.out::println);
    }

    private void start()
    {
        // Socket erstellen und auf Port 8080 hören.
        try (ServerSocket server = new ServerSocket(8080))
        {
            // Endlos-Schleife. Beendet ein Client die Verbindung, kann sich ein neuer Client verbinden.
            while(true)
            {
                System.out.println("Warte auf Verbindung...");
                try (Socket client = server.accept(); // Eingehende Verbindung akzeptieren.
                     ObjectInputStream input = new ObjectInputStream(client.getInputStream()))
                {
                    System.out.println("Verbunden");

                    // Solange der Client nicht geschlossen ist
                    while (!client.isClosed())
                    {
                        // lesen wir das Object aus dem Stream
                        Object o = input.readObject();
                        // Ist es ein Sensordaten-Objekt?
                        if (o instanceof Sensordaten a)
                        {
                            sensordatenList.add(a); // Objekt der Liste hinzufügen.
                            System.out.println(a); // Objekt ausgeben.
                        }
                    }
                }
                catch (EOFException e)
                {
                    // Wird ausgelöst, wenn der Client die Verbindung beendet, während der Server ein Object vom Stream lesen möchte.
                    System.out.println("Client hat Verbindung beendet.");
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
