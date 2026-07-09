package main.java.tag09.aufgabe.loesung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.jetbrains.annotations.NotNull;

public class Server {

  private static final int PORT = 12345;
  private static final String CORRECT_ID = "1234";
  private static final String FILE_NAME = "MissionBericht.txt";
  private static final String RESOURCES_PATH = "src/main/java/tag09/loesung/resources/";
  private static final String EXIT_COMMANDO = "Q";
  public static final String ENDE_BERICHT = "ENDE";
  private static boolean running = true;

  public static void main() {

    System.out.println("_-= Server gestartet. Warte auf Superhelden =-_ ");
    System.out.println("Zum Beenden sende 'Q oder q oder QUIT' als Helden-ID.");

    try (ServerSocket serverSocket = new ServerSocket(PORT)) {

      while (running) {
        String result = processMissionReport(serverSocket);
        System.out.println("DEBUG: processMissionReport zurückgegeben: " + result);
        if (EXIT_COMMANDO.equalsIgnoreCase(result) || result.toUpperCase().startsWith(EXIT_COMMANDO)) {
          running = false;
          System.out.println("Server wird heruntergefahren...");
        }
      }

    } catch (IOException e) {
      System.err.println("Server konnte nicht gestartet werden: " + e.getMessage());
    }
  }

  private static @NotNull String processMissionReport(ServerSocket serverSocket) {
    try (Socket clientSocket = serverSocket.accept();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(
            clientSocket.getOutputStream(), true)) {

      System.out.println("Ein Superheld hat sich verbunden.");

      StringBuilder report = new StringBuilder();
      String line;
      while (!(line = reader.readLine()).equals(ENDE_BERICHT)) {
        report.append(line).append(System.lineSeparator());
      }

      String receivedId = reader.readLine();
      System.out.println("Empfangene ID: " + receivedId);

      if (EXIT_COMMANDO.equalsIgnoreCase(receivedId)) {
        writer.println("Server wird heruntergefahren!");
        System.out.println("SHUTDOWN-Befehl empfangen!");

        return EXIT_COMMANDO;
      }

      if (CORRECT_ID.equals(receivedId)) {
        saveReportToFile(report.toString());
        writer.println("Mission erfolgreich gespeichert! Gratulation, Superheld!");
        System.out.println("Bericht gespeichert.");
      } else {
        writer.println("Falsche Helden-ID! Mission nicht gespeichert.");
        System.err.println("Falsche ID. Bericht verworfen.");
      }

      return "OK";

    } catch (IOException e) {
      System.err.println("Fehler bei der Client-Verbindung: " + e.getMessage());
      return "ERROR";
    }
  }

  private static void saveReportToFile(String report) {
    try (FileWriter fileWriter = new FileWriter(RESOURCES_PATH + FILE_NAME, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

      bufferedWriter.write(report);
      bufferedWriter.write("----------------------------------------" + System.lineSeparator());

    } catch (IOException e) {
      System.err.println("Fehler beim Speichern der Datei: " + e.getMessage());
    }
  }
}