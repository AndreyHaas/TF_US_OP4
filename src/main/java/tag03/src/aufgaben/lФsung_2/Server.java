package main.java.tag03;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server
{
    public static void main(String[] args)
    {
        Server server = new Server();
        server.start(1234);
    }

    private void start(int port)
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            // Durch die Schleife können sich immer wieder neue Clients verbinden.
            do
            {
                List<Sensordaten> liste = new ArrayList<>();

                System.out.println("Warte auf Verbindung...");
                try (Socket clientSocket = serverSocket.accept(); // Eingehende Verbindung akzeptieren.
                     ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream()))
                {
                    System.out.println("Verbindung angenommen.");

                    while (!clientSocket.isClosed())
                    {
                        // Objekt wird deserialisiert und einer Variable zugewiesen.
                        Object o = input.readObject();
                        System.out.println("Daten empfangen.");
                        if (o instanceof Sensordaten)
                            liste.add((Sensordaten) o);
                    }
                }
                catch (EOFException e)
                {
                    // Wird ausgelöst, wenn der Client die Verbindung beendet, während der Server ein Object vom Stream lesen möchte.
                    System.out.println("Client hat Verbindung getrennt.");
                    liste.forEach(System.out::println);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            } while (true);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
