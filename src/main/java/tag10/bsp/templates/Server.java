package main.java.tag10.bsp.templates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);
    private static final String KORREKTE_CODE = "008";
    private static final int PORT = 5000;
    public static final String ENDE = "ENDE";
    private static final String PATH = "src/main/resources/";
    private static final String AUFTRAG_FILE = "auftrag.txt";

    static void main() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            log.info("Server gestartet auf Port {}. Warte auf Verbindungen...", PORT);

            boolean serverLeuft = true;

            while (serverLeuft) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(
                             new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     BufferedWriter writer = new BufferedWriter(
                             new OutputStreamWriter(socket.getOutputStream()))) {

                    log.info("Client ist verbunden.");

                    String code = reader.readLine();
                    log.info("Code vom Client erhalten: {}", code);

                    if ("EXIT".equalsIgnoreCase(code)) {
                        log.info("Exit-Befehl empfangen. Server wird beendet...");
                        writer.write("Server wird heruntergefahren...");
                        writer.newLine();
                        writer.flush();
                        serverLeuft = false;
                    }

                    else if (!KORREKTE_CODE.equalsIgnoreCase(code)) {
                        log.info("Falscher Code: {}. Verbindung wird geschlossen.", code);
                        writer.write("Falscher Code. Zugriff verweigert.");
                        writer.newLine();
                        writer.flush();
                    } else {
                        log.info("Korrekter Code. Sende Auftrag...");
                        String auftrag = Files.readString(Path.of(PATH + AUFTRAG_FILE));
                        writer.write(auftrag);
                        writer.newLine();
                        writer.write(ENDE);
                        writer.newLine();
                        writer.flush();

                        writer.write("Code ist korrekt. Viel Erfolg, Agent!");
                        writer.newLine();
                        writer.flush();
                    }

                } catch (IOException e) {
                    log.error("Fehler bei der Client-Verbindung: {}", e.getMessage());
                }
            }

            log.info("Server wurde erfolgreich beendet.");

        } catch (IOException e) {
            log.error("Fehler beim Starten des Servers: {}", e.getMessage());
        }
    }
}