package main.java.tag03.aufgaben.loesungen.loesung_1v2;

/*Erweitern Sie das Client-Server-Beispiel aus der Vorlesung so, dass der Server in einer Schleife mehrere Befehle verarbeiten kann.
Dazu soll auch der Client in der Lage sein, nacheinander Befehle an den Server schicken zu können.

Befehle könnten zum Beispiel sein:
Aktuelle Uhrzeit des Servers
Working Directory des Servers
Eine Auflistung der Dateien und Ordner im Verzeichnis des Servers


Weitere mögliche Erweiterung des Programms:
Der Server protokolliert alle empfangenen Befehle in einer Datei.
*/

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server
{
	// Logger um Nachrichten zu protokollieren.
	private static final Logger logger = Logger.getGlobal();

	public static void main(String[] args)
	{
		Server s = new Server();
		try
		{
			logger.addHandler(new FileHandler("resources/log.txt", true));
			s.start(1234);
		}
		catch (IOException e)
		{
			logger.log(Level.SEVERE, e.toString());
		}
	}

	private void start(int port) throws IOException
	{
		try (ServerSocket serverSocket = new ServerSocket(port))
		{
			System.out.println("Warte auf Verbindungen");

			try (Socket clientSocket = serverSocket.accept();
				 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
				 BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
			{
				System.out.println("Verbunden");

				String message = "";

				do
				{
					message = reader.readLine();
					//System.out.println(message);
					logger.log(Level.INFO, message);

					if (message.equalsIgnoreCase("Hallo Server"))
						writer.println("Hallo Client");
					else if (message.equalsIgnoreCase("time"))
						writer.println(LocalTime.now());
					else if (message.equalsIgnoreCase("dir"))
						writer.println(System.getProperty("user.dir"));
					else if (message.equalsIgnoreCase("files"))
					{
						String[] files = new File(System.getProperty("user.dir")).list();

						StringBuilder sb = new StringBuilder();
						for (String f : files)
							sb.append(f).append("\n");

						// Bei einem print müssen wir trotz autoflush manuell flush() aufrufen.
						writer.print(sb.toString());
						writer.flush();
						//writer.println(Arrays.toString(files));
					}
					// Mit dem Befehl "get DATEINAME" möchte ich den Inhalt einer Datei vom Server an den Client schicken.
					// Dazu prüfe ich, ob der Befehl ein Leerzeichen hat. Wenn ja, prüfe ich, ob der Teil vor dem Leerzeichen das "get" ist.
					// Wenn ja, nehme ich den Teil nach dem Leerzeichen, also den Dateinamen, lese den Inhalt der Datei als String und schicke das per Writer an den Client.
					else if (message.contains(" ") && message.substring(0, message.indexOf(" ")).equalsIgnoreCase("get"))
					{
						String file = message.substring(message.indexOf(" ") + 1);
						String content = Files.readString(Paths.get(System.getProperty("user.dir") + File.separator + file));
						writer.println(content);
					}
					else if (message.equalsIgnoreCase("stop"))
						writer.println("stop");
					else
						writer.println("Unbekannter Befehl");

				} while (!message.equalsIgnoreCase("stop"));
			}
		}
	}
}
