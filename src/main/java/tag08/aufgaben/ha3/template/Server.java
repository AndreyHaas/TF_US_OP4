package main.java.tag08.aufgaben.ha3.template;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Server {

    private static final int PORT = 8080;
    private static final int MAX_CLIENTS = 10;
    private static volatile boolean running = true;
    private static int clientCount = 0;

    static void main() {
        System.out.println("=== Bücher-Server gestartet ===");
        System.out.println("Port: " + PORT);
        System.out.println("Max Clients: " + MAX_CLIENTS);
        System.out.println("Zum Beenden 'q' drücken.");

        // Shutdown-Thread
        new Thread(() -> {
            try (Scanner scanner = new Scanner(System.in)) {
                while (running) {
                    if ("q".equalsIgnoreCase(scanner.nextLine().trim())) {
                        running = false;
                        System.out.println("Server wird beendet...");
                        break;
                    }
                }
            }
        }).start();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("✅ Server läuft auf Port " + PORT);

            while (running && clientCount < MAX_CLIENTS) {
                try {
                    serverSocket.setSoTimeout(1000);
                    Socket clientSocket = serverSocket.accept();
                    clientCount++;
                    System.out.println(System.lineSeparator() + "[Client #" + clientCount + "] verbunden: " + clientSocket.getInetAddress());
                    System.out.println("  Verbleibende Slots: " + (MAX_CLIENTS - clientCount));

                    System.out.println("   🔄 Starte ClientHandler...");
                    ClientHandler handler = new ClientHandler(clientSocket);
                    Thread thread = new Thread(handler);
                    thread.start();
                    System.out.println("   ✅ ClientHandler gestartet (Thread: " + thread.getName() + ")");

                } catch (SocketTimeoutException _) {
                    // Timeout ist normal
                }
            }

            System.out.println(System.lineSeparator() + "Server wurde beendet.");
            System.out.println("  Bearbeitete Clients: " + clientCount);
            System.exit(0);

        } catch (IOException e) {
            System.err.println("❌ Server-Fehler: " + e.getMessage());
            e.printStackTrace();
        }
    }
}