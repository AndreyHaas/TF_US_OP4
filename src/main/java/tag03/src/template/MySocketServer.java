package main.java.tag03;

/*
 * Der Begriff Socket-Programmierung bezieht sich auf das Schreiben von
 * Programmen, die auf mehreren Computern ausgeführt werden, wobei die Programme
 * alle über ein Netzwerk miteinander verbunden sind.
 *
 * Es gibt zwei Kommunikationsprotokolle, die wir für die Socket-Programmierung
 * verwenden können: User Datagram Protocol (UDP) und Transfer Control Protocol (TCP).
 *
 * Der Hauptunterschied zwischen den beiden besteht darin, dass UDP
 * verbindungslos ist, was bedeutet, dass es keine Sitzung zwischen dem Client
 * und dem Server gibt, während TCP verbindungsorientiert ist, was bedeutet,
 * dass zuerst eine exklusive Verbindung zwischen dem Client und dem Server
 * hergestellt werden muss, damit die Kommunikation stattfinden kann.
 *
 * Dies ist eine Einführung in die Socket-Programmierung über TCP/IP-Netzwerke
 * und demonstriert, wie Client/Server-Anwendungen in Java geschrieben werden.
 * Für UDP Beispiele: https://www.baeldung.com/udp-in-java
 *
 * Da es hier nur um eine kurze Einführung geht, verwenden wir eine unverschlüsselte
 * Kommunikation zwischen Server und Client. Eine verschlüsselte Kommunikation kann
 * mit TLS/SSL realisiert werden.
 * https://kodejava.org/how-do-i-implement-secure-socket-communication-with-sslsocket-and-sslserversocket-in-java/
 *
 * Klassen und Schnittstellen, die sich um Kommunikationsdetails auf niedriger
 * Ebene zwischen Client und Server kümmern, befinden sich größtenteils im
 * java.net-Paket
 *
 * Der Einfachheit halber führen wir unsere Client- und Serverprogramme auf
 * demselben Computer aus. Wenn wir sie auf verschiedenen vernetzten Computern
 * ausführen würden, würde sich nur die IP-Adresse ändern. In diesem Fall
 * verwenden wir localhost auf 127.0.0.1
 *
 * Es wird eine bidirektionale Kommunikationsanwendung sein, bei der der Client
 * den Server begrüßt und der Server antwortet.
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//Was ist ein Socket
/*
 * Im Bereich der Client-Server-Architekturen sind Sockets das
 * Standardhilfsmittel mit denen Server beliebige Services an eine große Zahl
 * von Clients verteilen.
 */
public class MySocketServer
{
    public static void main(String[] args)
    {
        MySocketServer server = new MySocketServer();
        try
        {
            server.start(1234);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

	public void start(int port) throws IOException
	{
		// Socket für den Server.
		// Wir erzeugen einen Server-Socket, der an den angegebenen Port gebunden ist.
		try (ServerSocket serverSocket = new ServerSocket(port))
		{
			System.out.println("Wartet auf Verbindungen...");

			// Der Client, der sich mit dem Server verbindet, wird durch die Klasse Socket repräsentiert.
			try (Socket clientSocket = serverSocket.accept())
			{
				if (clientSocket.isConnected())
					System.out.println("Verbindungsanfrage angenommen.");

				// Verschiedene Methodenaufrufe zum Testen:
				System.out.println(serverSocket.toString()); // Der Server ist ungebunden. Darum nimmt er Verbindungen von jeder anderen IP-Adresse an. (0.0.0.0)
				System.out.println(serverSocket.getInetAddress());
				System.out.println(serverSocket.getLocalSocketAddress());
				System.out.println(clientSocket.getInetAddress());

				// Um mit dem Client kommunizieren zu können, fragen wir den Output- und InputStream ab.
                // Aus diesen Streams bilden wir Writer und Reader.
                // Mit dem Writer schicken wir Daten an Clients und mit dem Reader können wir eingehende Daten lesen.
                // Oracle empfiehlt in der Doku für Java 8, PrintWriter und BufferedReader zu verwenden.
                // https://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html
                // Hinweis: Damit der PrintWriter die Nachrichten sofort auf den Stream schreibt, sollte autoFlush auf true gesetzt werden.
                // In manchen Situationen kann es aber notwendig sein, manuell zu flushen.
				try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
					 BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
				{
					while (true)
					{
						// Warten auf Nachricht des Clients
						System.out.println("Warte auf Input");

						// Eine Zeile Text aus dem InputStream lesen.
						String input = reader.readLine();
						System.out.println(input);

						// Auf die Nachricht antworten.
						if (input.equalsIgnoreCase("Hallo Server"))
							writer.println("Hallo Client");
						else if (input.equalsIgnoreCase("stop"))
						{
							writer.println("stop");
							break;
						}
						else
							writer.println("Unbekannte Anfrage");
					}
				}
			}
		}
	}
}
