package main.java.tag03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MySocketClient
{
	private final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args)
	{
		MySocketClient client = new MySocketClient();
		try
		{
			// Wenn wir den Client auf einem anderen Computer als den Server ausführen, können wir über die Argumente beim Programmstart die IP-Adresse des Servers übergeben. Wird nichts übergeben, verbinden wir einfach auf Localhost.
			// Beispiel: Der Server läuft auf diesem Rechner, der Client wird auf einer virtuellen Maschine ausgeführt.
			// Dann kann der Client mit dem Kommandozeilen-Befehl "java template/MySocketClient.class 172.25.157.8" gestartet werden (die IP-Adresse bei Bedarf anpassen)
			if (args.length == 0)
				client.connect("localhost", 1234);
			else
				client.connect(args[0], 1234);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void connect(String ip, int port) throws IOException
	{
		// Wir erstellen einen Socket und verbinden mit der angegebenen Host-Adresse und Port:
		try (Socket clientSocket = new Socket(ip, port))
		{
			if (clientSocket.isConnected())
				System.out.println("Verbunden");

			// Verschiedene Methoden zum Testen:
			System.out.println(clientSocket.getInetAddress());
			System.out.println(clientSocket.getLocalAddress());
			System.out.println(clientSocket.getRemoteSocketAddress());

			try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
 				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
			{
				String antwort;
				do
				{
					System.out.print("> ");
					String nachricht = scanner.nextLine();
					writer.println(nachricht);
					antwort = reader.readLine();
					System.out.println(antwort);

				} while (!antwort.equalsIgnoreCase("stop"));

			}
		}
	}
}
