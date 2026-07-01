package main.java.tag03.ha.a1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Server {

    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private ServerSocket serverSocket;
    private boolean running = true;
    private final Map<String, String> users = new HashMap<>();

    static {
        try {
            FileHandler fileHandler = new FileHandler("server_log_secure.txt", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("Fehler beim Initialisieren des Loggings: " + e.getMessage());
        }
    }

    public Server(int port) {
        // Benutzer für Authentifizierung
        users.put("admin", "admin123");
        users.put("user", "pass123");

        try {
            serverSocket = new ServerSocket(port);
            logger.info("Secure Server gestartet auf Port " + port);
            System.out.println("Secure Server gestartet auf Port " + port);
        } catch (IOException e) {
            logger.severe("Fehler beim Starten des Servers: " + e.getMessage());
        }
    }

    public void start() {
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                logger.info("Neue Verbindung von " + clientSocket.getInetAddress());
                new AuthenticatedClientHandler(clientSocket, users).start();
            } catch (IOException e) {
                if (running) {
                    logger.severe("Fehler beim Akzeptieren der Verbindung: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(12345);
        try {
            server.start();
        } catch (Exception e) {
            server.running = false;
            try {
                if (server.serverSocket != null) {
                    server.serverSocket.close();
                }
            } catch (IOException ex) {
                logger.severe("Fehler beim Schließen des Servers: " + ex.getMessage());
            }
        }
    }
}