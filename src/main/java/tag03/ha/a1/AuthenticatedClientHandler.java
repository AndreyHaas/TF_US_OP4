package main.java.tag03.ha.a1;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Logger;

public class AuthenticatedClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Map<String, String> users;
    private boolean authenticated = false;
    private static final Logger logger = Logger.getLogger(AuthenticatedClientHandler.class.getName());

    public AuthenticatedClientHandler(Socket socket, Map<String, String> users) {
        this.socket = socket;
        this.users = users;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Bitte authentifizieren Sie sich.");
            out.println("Benutzername:");
            String username = in.readLine();
            out.println("Passwort:");
            String password = in.readLine();

            if (username != null && password != null &&
                    users.containsKey(username) && users.get(username).equals(password)) {
                authenticated = true;
                out.println("Authentifizierung erfolgreich!");
                logger.info("Benutzer " + username + " authentifiziert von " + socket.getInetAddress());

                String command;
                while ((command = in.readLine()) != null) {
                    logger.info("Befehl von " + username + " (" + socket.getInetAddress() + "): " + command);
                    String response = processCommand(command);
                    out.println(response);
                }
            } else {
                out.println("Authentifizierung fehlgeschlagen. Verbindung wird geschlossen.");
                logger.warning("Authentifizierungsfehler für " + socket.getInetAddress());
                socket.close();
            }

        } catch (IOException e) {
            logger.severe("Fehler bei Client " + socket.getInetAddress() + ": " + e.getMessage());
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
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
            case "time" -> "Server-Zeit: " + java.time.LocalDateTime.now();
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
                        result.append("[DIR]  ").append(file.getName()).append("/\n");
                    } else {
                        result.append("[FILE] ").append(file.getName())
                                .append(" (").append(file.length()).append(" bytes)\n");
                    }
                }
            }
            return result.toString();
        } catch (Exception e) {
            return "Fehler beim Auflisten des Verzeichnisses: " + e.getMessage();
        }
    }

    private String getHelp() {
        String s = """
                Verfügbare Befehle:
                  time        - Zeigt die aktuelle Server-Zeit an
                  workingdir  - Zeigt das aktuelle Arbeitsverzeichnis des Servers
                  list / dir  - Listet Dateien und Ordner im Server-Verzeichnis auf
                  systeminfo  - Zeigt Systeminformationen an
                  help        - Zeigt diese Hilfe an
                  quit / exit - Beendet die Verbindung zum Server
                """;
        return s;
    }

    private String getSystemInfo() {
        return String.format("""
                        System-Informationen:
                          Betriebssystem: %s
                          OS-Version: %s
                          Java-Version: %s
                          Benutzer: %s
                          Working Directory: %s
                          Prozess-ID: %s
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