package main.java.tag09.aufgabe.dozentLoesungBearbeitet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import org.jetbrains.annotations.NotNull;

public class SuperheldenClientNew {

  private static final String SERVER_ADDRESS = "127.0.0.1"; //oder localhost
  private static final int SERVER_PORT = 12345;

  public static void main(String[] args) {
    try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Scanner scanner = new Scanner(System.in)) {

      System.out.println("Verbunden mit Server");

      auftragAuslesen(reader);

      chatMitHeld(scanner, writer, reader);
    } catch (IOException e) {
      System.err.println("Fehler: " + e.getMessage());
    }
  }

  private static void chatMitHeld(
      @NotNull Scanner scanner,
      @NotNull PrintWriter writer,
      @NotNull BufferedReader reader) throws IOException {
    System.out.println("Gib deinen Missionsbericht ein (Held, Mission, Details):");
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

    System.out.print("Gib deine Helden-ID ein: ");
    String id = scanner.nextLine();
    writer.println(id);

    String response = reader.readLine();
    System.out.println("Server: " + response);
  }

  private static void auftragAuslesen(@NotNull BufferedReader reader) throws IOException {
    System.out.println("Auftrag vom Server:");
    String line;

    while (!(line = reader.readLine()).equals("ENDE_AUFTRAG")) {
      System.out.println(line);
    }

    System.out.println();
  }
}