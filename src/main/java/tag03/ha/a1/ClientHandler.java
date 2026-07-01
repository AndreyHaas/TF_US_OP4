package main.java.tag03.ha.a1;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class ClientHandler extends Thread {
    private final Socket socket;
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String command;
            while ((command = in.readLine()) != null) {
                logger.info("Befehl von " + socket.getInetAddress() + ": " + command);
                String response = processCommand(command);
                out.println(response);
            }
        } catch (IOException e) {
            logger.severe("Fehler bei Client " + socket.getInetAddress() + ": " + e.getMessage());
        } finally {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
                logger.info("Verbindung zu " + socket.getInetAddress() + " geschlossen");
            } catch (IOException e) {
                logger.severe("Fehler beim Schließen der Socket: " + e.getMessage());
            }
        }
    }

    private String processCommand(String command) {
        command = command.trim().toLowerCase();

        return switch (command) {
            case "time" -> "Server-Zeit: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            case "workingdir", "pwd" -> "Working Directory: " + System.getProperty("user.dir");
            case "list", "dir" -> listDirectory();
            case "help" -> getHelp();
            case "quit", "exit" -> "Server wird beendet...";
            case "systeminfo" -> getSystemInfo();
            default -> "Unbekannter Befehl: " + command + ". Verwenden Sie 'help' für eine Liste der Befehle.";
        };
    }

    private String listDirectory() {
        try {
            File directory = new File(".");
            File[] files = directory.listFiles();
            StringBuilder result = new StringBuilder("Verzeichnisinhalt:\n");

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        result.append("[DIR]  ").append(file.getName()).append("/").append(System.lineSeparator());
                    } else {
                        result.append("[FILE] ").append(file.getName())
                                .append(" (").append(file.length()).append(" bytes)").append(System.lineSeparator());
                    }
                }
            }

            return result.toString();
        } catch (Exception e) {
            return "Fehler beim Auflisten des Verzeichnisses: " + e.getMessage();
        }
    }

    private @NotNull String getHelp() {
        return """
                Verfügbare Befehle:
                  time        - Zeigt die aktuelle Server-Zeit an
                  workingdir  - Zeigt das aktuelle Arbeitsverzeichnis des Servers
                  list / dir  - Listet Dateien und Ordner im Server-Verzeichnis auf
                  systeminfo  - Zeigt Systeminformationen an
                  help        - Zeigt diese Hilfe an
                  quit / exit - Beendet die Verbindung zum Server
                """;
    }

    private @NotNull String getSystemInfo() {
        return String.format("""
                        System-Informationen:
                          Betriebssystem: %s
                          OS-Version: %s
                          Java-Version: %s
                          Benutzer: %s
                          Working Directory: %s
                          Prozess-ID: %s (nicht zuverlässig in Java)
                        """,
                System.getProperty("os.name"),
                System.getProperty("os.version"),
                System.getProperty("java.version"),
                System.getProperty("user.name"),
                System.getProperty("user.dir"),
                ProcessHandle.current().pid()
        );
    }
}