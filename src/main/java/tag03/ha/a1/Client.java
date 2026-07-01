package main.java.tag03.ha.a1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket = null;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader console;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            console = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Verbunden mit Server " + host + ":" + port);
            System.out.println("Geben Sie 'help' ein für eine Liste der Befehle.");
            System.out.println("Geben Sie 'quit' ein zum Beenden.");

        } catch (IOException e) {
            System.err.println("Fehler beim Verbinden zum Server: " + e.getMessage());
            System.exit(1);
        }
    }

    public void run() {
        try {
            String command;
            while (true) {
                System.out.print(System.lineSeparator() + "> ");
                command = console.readLine();

                if (command == null || command.trim().isEmpty()) {
                    continue;
                }

                if (command.trim().equalsIgnoreCase("quit") ||
                        command.trim().equalsIgnoreCase("exit")) {
                    System.out.println("Verbindung wird beendet...");
                    break;
                }

                out.println(command);
                String response = in.readLine();
                System.out.println(response);
            }
        } catch (IOException e) {
            System.err.println("Fehler bei der Kommunikation mit dem Server: " + e.getMessage());
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                if (console != null) {
                    console.close();
                }
                System.out.println("Client beendet");
            } catch (IOException e) {
                System.err.println("Fehler beim Schließen der Verbindung: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Ungültiger Port. Verwende Standardport 12345");
            }
        }

        Client client = new Client(host, port);
        client.run();
    }
}