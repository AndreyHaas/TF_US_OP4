package main.java.tag03.unterricht;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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
public class SocketServer {
    static void main() {
        SocketServer server = new SocketServer();
        try {
            server.start(1234);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(int port) throws IOException {
        // Erzeugt ein neues ServerSocket-Objekt.
        // Der Konstruktor reserviert beim Betriebssystem den angegebenen Port.
        // Ab diesem Zeitpunkt "lauscht" unser Programm auf Verbindungsanfragen.
        //
        // Try-With-Resources sorgt dafür, dass der ServerSocket beim Verlassen
        // des Blocks automatisch wieder geschlossen wird.
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Wartet auf Verbindungen...");

            // Der Client, der sich mit dem Server verbindet, wird durch die Klasse Socket repreaesentiert.
            // Die accept Methode wartet auf eingehende Verbindungen. Der Programmablauf wird so lange blockiert
            // bis ein Client sich verbindet.
            // Erst wenn eine Verbindung hergestellt wurde,
            // liefert accept() ein Socket-Objekt zurück.
            // Dieses Socket-Objekt repräsentiert genau eine Verbindung
            // zwischen unserem Server und genau einem Client.
            try (Socket clientSocket = serverSocket.accept()) {
                // Prüft, ob die Verbindung erfolgreich hergestellt wurde.
                if (clientSocket.isConnected()) {
                    System.out.println("Verbindungsanfrage angenommen.");
                }

                // Gibt Informationen über den ServerSocket aus.
                // toString() liefert unter anderem den verwendeten Port.
                System.out.println(serverSocket);

                // Liefert die IP-Adresse,
                // an die der ServerSocket gebunden ist.
                // Da hier keine bestimmte IP angegeben wurde,
                // lauscht der Server auf allen Netzwerkadressen (0.0.0.0).
                System.out.println(serverSocket.getInetAddress());

                // Gibt die komplette lokale Socket-Adresse aus.
                // Diese besteht aus IP-Adresse und Port.
                System.out.println(serverSocket.getLocalSocketAddress());

                // Gibt die IP-Adresse des verbundenen Clients aus.
                // Da Client und Server in diesem Beispiel auf demselben Rechner laufen,
                // wird hier normalerweise 127.0.0.1 bzw. localhost angezeigt.
                System.out.println(clientSocket.getInetAddress());

                // Über den Socket können wir Daten senden und empfangen.
                // getOutputStream() liefert einen Byte-Ausgabestrom.
                // Diesen umschließen wir mit einem PrintWriter,
                // damit wir komfortabel Text senden können.
                // Der Parameter true aktiviert AutoFlush.
                // Dadurch werden Daten sofort gesendet,
                // ohne dass writer.flush() aufgerufen werden muss.

                // Mit dem Writer schicken wir Daten an Clients und mit dem Reader können wir eingehende Daten lesen.
                // Oracle empfiehlt in der Doku für Java 8, PrintWriter und BufferedReader zu verwenden.
                // https://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html
                try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    while (true) {
                        // Warten auf Nachrichten des Clients
                        System.out.println("Warte auf Input");

                        // Eine Zeile Text aus dem InputStream lesen:
                        String input = reader.readLine();
                        System.out.println(input);

                        // Server soll auf die Nachricht antworten:
                        if (input.equalsIgnoreCase("Hallo Server")) {
                            writer.println("Hallo Client");
                        } else if (input.equalsIgnoreCase("stop")) {
                            writer.println("stop");
                            break;
                        } else {
                            writer.println("Unbekannte Anfrage");
                        }
                    }
                }
            }
        }
    }
}