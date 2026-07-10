package main.java.tag10.bsp.templates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static template.Server.ENDE;

public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    public static final String HOST = "localhost";
    public static final int PORT = 5000;

    static void main() {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("EXIT zum Beenden des Servers? (ja/nein): ");
            String benutzerbefehl = scanner.nextLine();

            if ("ja".equalsIgnoreCase(benutzerbefehl)) {
                log.info("Sende EXIT-Befehl an Server...");
                writer.write("EXIT");
                writer.newLine();
                writer.flush();

                String antwort = reader.readLine();
                if (antwort != null) {
                    log.info("Server sagt: {}", antwort);
                }

                return;
            }

            System.out.print("Dein Code, Agent: ");
            String code = scanner.nextLine();

            writer.write(code);
            writer.newLine();
            writer.flush();

            log.info("===== AUFTRAG =====");

            String auftrag;
            do {
                auftrag = reader.readLine();

                if (auftrag == null || auftrag.equals(ENDE)) {
                    break;
                }

                log.info(auftrag);
            } while (true);

            if (auftrag == null) {
                log.error("Verbindung zum Server verloren.");
                return;
            }

            String antwort = reader.readLine();
            if (antwort != null) {
                log.info("Antwort bekommen: {}", antwort);
            }

        } catch (IOException e) {
            log.error("Fehler: {}", e.getMessage());
        }
    }
}