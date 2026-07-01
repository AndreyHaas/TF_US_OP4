package main.java.tag03.src.aufgaben.lФsung_2;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class Client
{
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        Client client = new Client();
        client.connect("localhost", 1234);
    }

    private Sensordaten erzeugeDaten()
    {
        int[] messwerte = new int[10];

        for (int i = 0; i < messwerte.length; i++)
        {
            messwerte[i] = random.nextInt(1,100);
        }

        return new Sensordaten(messwerte, "Testdaten", LocalDateTime.now());
    }

    private void connect(String ip, int port)
    {
        // ObjectOutputStream kann ganze Objekte als Bytes auf einen Stream schreiben.
        System.out.println("Verbinde...");
        try (Socket clientSocket = new Socket(ip, port);
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream()))
        {
            System.out.println("Verbindung hergestellt.");

            String auswahl;
            do
            {
                System.out.print("Daten erstellen? (Y/N): ");
                auswahl = scanner.nextLine();

                if (auswahl.equalsIgnoreCase("Y"))
                {
                    System.out.println("Erzeuge Daten...");
                    Sensordaten daten = erzeugeDaten(); // Daten erzeugen.

                    System.out.println("Sende Daten...");
                    output.writeObject(daten); // Objekt serialisieren und auf den Stream schreiben.
                }

            } while (!auswahl.equalsIgnoreCase("N"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
