package main.java.tag03.unterricht;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    // Der Scanner liest Benutzereingaben von der Konsole. // System.in ist der Standardeingabestrom der Tastatur. Da der Scanner mehrfach verwendet wird, legen wir ihn als Instanzvariable an.
    private final Scanner scanner = new Scanner(System.in);

    static void main(String[] args) {
        SocketClient client = new SocketClient();

        // Wenn wir den Client auf einem anderen Computer als den Server ausführen, können wir über die Argumente beim Programmstart die IP-Adresse des Servers übergeben. Wird nichts übergeben, verbinden wir einfach auf Localhost.
        // Beispiel: Der Server läuft auf diesem Rechner, der Client wird auf einer virtuellen Maschine ausgeführt.
        // Dann kann der Client mit dem Kommandozeilen-Befehl "java template/MySocketClient.class 172.25.157.8" gestartet werden (die IP-Adresse bei Bedarf anpassen)

        // Beim Programmstart können optional Kommandozeilenparameter übergeben werden. // args enthält alle diese Parameter als String-Array.
        // Wurde keine IP-Adresse angegeben...
        try {
            if (args.length == 0) {
                // ...verbinden wir uns mit dem lokalen Rechner.
                // "localhost" ist ein Hostname für die IP-Adresse 127.0.0.1.

                // // Wurde eine IP-Adresse übergeben, verbinden wir uns mit diesem Rechner.
                // args[0] enthält den ersten Parameter.
                client.connect("localhost", 1234);
            } else {
                client.connect(args[0], 1234);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Erzeugt einen Socket. Der Konstruktor versucht sofort, eine TCP-Verbindung zum
    // angegebenen Rechner aufzubauen. Wir erstellen einen Socket und verbindet
    // mit der angegebenen Host-Adresse und Port:
    public void connect(String ip, int port) throws IOException {
        try (Socket clientSocket = new Socket(ip, port)) {
            if (clientSocket.isConnected()) {
                System.out.println("Verbunden mit Server!");
            }

            // Gibt die IP des Servers aus:
            System.out.println(clientSocket.getInetAddress());

            // Gibt die eigene (lokale) IP-Adresse des Clients aus:
            System.out.println(clientSocket.getLocalAddress());

            // Gibt die komplette Adresse des Servers aus. Diese besteht aus IP-Adresse und Port:
            System.out.println(clientSocket.getRemoteSocketAddress());

            // Über den Socket können wir Daten senden und empfangen.
            // Der PrintWriter schreibt Text auf den OutputStream.
            // true aktiviert AutoFlush.
            // Dadurch wird jede println()-Ausgabe sofort verschickt.
            try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                // Variable fuer die Antwort des Servers.
                String antwort;

                do {
                    System.out.println("> ");
                    String nachricht = scanner.nextLine();
                    writer.println(nachricht);
                    antwort = reader.readLine();
                    System.out.println(antwort);
                } while (!antwort.equalsIgnoreCase("stop"));
            }
        }
    }
}