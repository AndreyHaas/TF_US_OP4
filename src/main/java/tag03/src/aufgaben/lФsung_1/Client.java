package main.java.tag03.src.aufgaben.lФsung_1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        try
        {
            connect("localhost", 8080);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void connect(String ip, int port) throws IOException
    {
        // Wichtig! AutoFlush nicht vergessen!
        try (Socket client = new Socket(ip, port);
             PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream())))
        {
            System.out.println("Verbunden...");

            Scanner scanner = new Scanner(System.in);
            String command;
            while(!client.isClosed())
            {
                System.out.print("> ");
                command = scanner.nextLine();
                writer.println(command);
                do
                {
                    String answer = reader.readLine();
                    System.out.println(answer);
                } while (reader.ready()); // Wenn eine Nachricht vom Server mehrere Lines beinhaltet (z.B. bei der Auflistung der Dateien und Ordner), müssen alle Lines auch gelesen werden. Solange Zeilen vorhanden sind, gibt ready() true zurück.

                if (command.equals("exit"))
                    client.close();

            }
        }
    }
}