package main.java.tag07.src.aufgaben.lösung_3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Comparator;
import java.util.List;

public class Server
{
    private ServerSocket serverSocket;
    private Socket clientSocket;

    private ObjectInputStream objectReader;
    private ObjectOutputStream objectWriter;

    public static void main(String[] args)
    {
        // create instance of class
        Server server = new Server();

        try
        {
            server.start(1234);
        }
        catch (IOException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                server.stop();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    private void start(int port) throws IOException, ClassNotFoundException
    {
        serverSocket = new ServerSocket(port);

        clientSocket = serverSocket.accept();

        if (!clientSocket.isConnected())
        {
            throw new IOException("Verbindung konnte nicht aufgebaut werden");
        }

        objectReader = new ObjectInputStream(clientSocket.getInputStream());
        objectWriter = new ObjectOutputStream(clientSocket.getOutputStream());

        @SuppressWarnings("unchecked")
        List<Buch> buecher = (List<Buch>) objectReader.readObject();

        buecher.sort(Comparator.comparing(Buch::getId));

        objectWriter.writeObject(buecher);
    }

    private void stop() throws IOException
    {
        objectWriter.close();
        objectReader.close();
        clientSocket.close();
        serverSocket.close();
    }
}
