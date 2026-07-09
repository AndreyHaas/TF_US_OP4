package main.java.tag09.aufgabe.dozentLoesungBearbeitet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.apache.commons.lang3.StringUtils;

public class SuperheldenServerNew {

  private static final int PORT = 12345;
  private static final String VALID_HERO_ID = "1234";
  // muss geändert werden
  private static final String FILE_NAME = "MissionBericht.txt";
  private static final String RESOURCES_PATH = "src/main/java/tag09/aufgabe/dozentLoesungBearbeitet/resources/";
  private static boolean running = true;

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Server läuft auf Port " + PORT);
      System.out.println("Zum Beenden sende 'Q' oder 'q' als Helden-ID.");
      processMissionReport(serverSocket);
    } catch (IOException e) {
      System.err.println("Server konnte nicht starten: " + e.getMessage());
    }
  }

  private static void processMissionReport(ServerSocket serverSocket) {
    while (running) {
      try (Socket clientSocket = serverSocket.accept();
          BufferedReader reader = new BufferedReader(
              new InputStreamReader(clientSocket.getInputStream()));
          PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

        System.out.println("Client verbunden");

        String auftrag = Files.readString(Path.of(RESOURCES_PATH + "Auftrag.txt"),
            StandardCharsets.UTF_8);
        writer.println(auftrag);
        writer.println("ENDE_AUFTRAG");

        StringBuilder report = new StringBuilder();
        String line;
        while (!(line = reader.readLine()).equals("ENDE_BERICHT")) {
          report.append(line).append(System.lineSeparator());
        }

        String heroId = reader.readLine();

        System.out.println(StringUtils.EMPTY);
        System.out.println("Empfangene ID: " + heroId);

        if ("Q".equalsIgnoreCase(heroId)) {
          writer.println("Server wird heruntergefahren!");
          System.err.println("Der Held lehnte ab, indem er 'Q' eingab.\n" + "Server wird heruntergefahren!");
          running = false;
          return;
        }

        if (VALID_HERO_ID.equals(heroId)) {
          saveReport(report.toString());
          writer.println("Mission erfolgreich gespeichert! Gratulation, Superheld!");
        } else {
          writer.println("Falsche Helden-ID! Mission nicht gespeichert.");
        }

      } catch (IOException e) {
        System.err.println("Fehler: " + e.getMessage());
      }
    }
  }

  private static void saveReport(String report) {
    Path path = Paths.get(RESOURCES_PATH + FILE_NAME);
    try {
//      Files.createDirectories(path.getParent());
      Files.writeString(path,
          report + "----------------------------------------" + System.lineSeparator(),
          StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    } catch (IOException e) {
      System.err.println("Fehler beim Speichern: " + e.getMessage());
    }
  }
}