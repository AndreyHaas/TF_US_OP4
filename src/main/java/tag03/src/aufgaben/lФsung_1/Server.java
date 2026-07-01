/* Erweitern Sie das Client-Server-Beispiel so, dass der Server in einer Schleife mehrere Befehle verarbeiten kann.
    Dazu soll auch der Client in der Lage sein, nacheinander Befehle an den Server schicken zu können.

    Befehle könnten zum Beispiel sein:
    Aktuelle Uhrzeit des Servers
    Working Directory des Servers
    Eine Auflistung der Dateien und Ordner im Verzeichnis des Servers


    Weitere mögliche Erweiterung des Programms:
    Der Server protokolliert alle empfangenen Befehle in einer Datei.

 */

package main.java.tag03.src.aufgaben.lФsung_1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

public class Server
{
    public static void main(String[] args)
    {
        try
        {
            start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void start() throws IOException
    {
        try (ServerSocket serverSocket = new ServerSocket(8080))
        {
            System.out.println("Warte auf Verbindung...");
            // Wichtig! AutoFlush nicht vergessen!
            try (Socket client = serverSocket.accept();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter writer = new PrintWriter(client.getOutputStream(), true))
            {
                System.out.println("Verbunden...");

                // Solange der Socket nicht geschlossen ist...
                while (!client.isClosed())
                {
                    // lese einen Befehl aus dem Stream
                    String command = reader.readLine();

                    // Befehl ins Protokoll schreiben
                    String log = String.valueOf(LocalDateTime.now()).concat(" : ").concat(command).concat(System.lineSeparator());
                    Files.writeString(Paths.get("resources/protokoll.txt"), log, StandardOpenOption.APPEND, StandardOpenOption.CREATE);

                    switch (command.toLowerCase())
                    {
                        case "time":
                            System.out.println("time");
                            writer.println(LocalTime.now());
                            break;
                        case "directory":
                            System.out.println("directory");
                            writer.println(System.getProperty("user.dir"));
                            break;
                        case "list":
                            System.out.println("list");
                            try (Stream<Path> s = Files.list(Path.of(System.getProperty("user.dir"))))
                            {
                                List<Path> list = s.toList();
                                StringBuilder sb = new StringBuilder();
                                list.forEach(p -> sb.append(p.toString()).append("\n"));
                                writer.println(sb.toString());
                            }
                            break;
                        case "exit":
                                 System.out.println("exit");
                                 writer.println("bye!");
                                 client.close();
                            break;
                        default:
                            System.out.println("default");
                            writer.println("Unbekannter Befehl!");
                    }
                }
            }
        }
    }
}
