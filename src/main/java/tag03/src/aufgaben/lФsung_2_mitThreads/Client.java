package main.java.tag03;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class Client
{
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args)
    {
        Client client = new Client();
        // Client verbindet mit Server auf Localhost und Port 8080.
        client.connect("localhost", 8080);
    }

    private void connect(String adresse, int port)
    {
        System.out.println("Verbinde...");
        try (Socket socket = new Socket(adresse, port); // Verbinden.
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream()))
        {
            boolean weiter = true; // Steuert durch Benutzereingabe, ob weitere Daten erzeugt werden sollen.
            do
            {
                System.out.print("Daten erstellen? (Y/N): ");
                String eingabe = scanner.nextLine();

                switch (eingabe.toUpperCase())
                {
                    case "Y":
                        Sensordaten s = createDaten(); // Daten erzeugen.
                        output.writeObject(s); // Objekt serialisieren und auf den Stream schreiben.
                        break;
                    case "N":
                        weiter = false;
                        break;
                }
            } while (weiter);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private Sensordaten createDaten()
    {
        // Zufallsdaten erzeugen.
        int[] daten = new int[10];
        for (int i = 0; i < 10; i++)
            daten[i] = random.nextInt(100);

        Sensordaten s = new Sensordaten(daten, "Testdaten", LocalDateTime.now());

        return s;
    }
}
