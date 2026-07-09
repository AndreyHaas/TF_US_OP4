package main.java.tag09.aufgabe.loesung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

  private static final String SERVER_ADDRESS = "localhost";
  private static final int SERVER_PORT = 12345;

  public static void main() {

    System.out.println("_-= Superhelden-Client gestartet =-_");

    try (Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(socket.getInputStream()))) {

      System.out.println("Verbindung zum Server hergestellt.");

      System.out.println(System.lineSeparator() + "Gib deinen Missionsbericht ein:");
      System.out.println("   (Tippe 'ENDE' in einer neuen Zeile zum Abschließen)");
      System.out.print("Held: ");
      String held = scanner.nextLine();
      System.out.print("Mission: ");
      String mission = scanner.nextLine();
      System.out.print("Details: ");
      String details = scanner.nextLine();

      writer.println("Held: " + held);
      writer.println("Mission: " + mission);
      writer.println("Details: " + details);
      writer.println("ENDE_BERICHT");

      System.out.print(System.lineSeparator() + "Bitte gib deine Helden-ID ein: ");
      String id = scanner.nextLine();
      writer.println(id);

      String response = reader.readLine();
      System.out.println(System.lineSeparator() + "Vom Server: " + response);

    } catch (IOException e) {
      System.err.println("Fehler bei der Verbindung zum Server: " + e.getMessage());
    }
  }
}