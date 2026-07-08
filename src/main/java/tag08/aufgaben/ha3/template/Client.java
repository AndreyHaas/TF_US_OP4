package main.java.tag08.aufgaben.ha3.template;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import main.java.tag08.aufgaben.ha3.model.Buch;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private static final String PATH = "src/main/java/tag08/aufgaben/ha3/";
    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final int TIMEOUT = 5000; // 5 Sekunden
    private static final String XML_FILE = "regal.xml";
    private static final String JSON_OUTPUT = "neue_Liste.json";

    static void main() {
        System.out.println("=== Client gestartet ===");

        try {
            System.out.println(System.lineSeparator() + "1. Lese XML-Datei ein...");
            List<Buch> buecher = liesXmlDatei(PATH + XML_FILE);
            System.out.println("   " + buecher.size() + " Bücher eingelesen.");

            System.out.println(System.lineSeparator() + "--- Ursprüngliche Reihenfolge ---");
            buecher.forEach(System.out::println);

            System.out.println(System.lineSeparator() + "2. Verbinde zum Server " + HOST + ":" + PORT + "...");
            System.out.println("   Timeout: " + TIMEOUT + "ms");

            verbindungErstellenUndBuecherMitteilen(buecher);

        } catch (ConnectException e) {
            System.err.println("\n❌ FEHLER: Server nicht erreichbar!");
            System.err.println("   📌 Starte zuerst den Server: Server.main()");
            System.err.println("   📌 Prüfe: Läuft der Server auf Port " + PORT + "?");
        } catch (SocketTimeoutException _) {
            System.err.println("\n❌ FEHLER: Verbindungs-Timeout (" + TIMEOUT + "ms)!");
            System.err.println("   📌 Server antwortet nicht.");
            System.err.println("   📌 Prüfe: Läuft der Server?");
        } catch (Exception e) {
            System.err.println("Fehler im Client: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println(System.lineSeparator() + "=== Client beendet ===");
    }

    private static void verbindungErstellenUndBuecherMitteilen(List<Buch> buecher)
            throws IOException, ClassNotFoundException {

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(HOST, PORT), TIMEOUT);
            System.out.println("   ✅ Verbindung hergestellt!");

            try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                System.out.println(System.lineSeparator() + "3. Sende Bücher an Server...");
                out.writeObject(buecher);
                out.flush();
                System.out.println("   ✅ " + buecher.size() + " Bücher gesendet.");

                System.out.println(System.lineSeparator() + "4. Empfange sortierte Bücher vom Server...");
                @SuppressWarnings("unchecked")
                List<Buch> sortierteBuecher = (List<Buch>) in.readObject();
                System.out.println("   ✅ " + sortierteBuecher.size() + " Bücher empfangen.");

                System.out.println(System.lineSeparator() + "--- Sortierte Reihenfolge (nach ID) ---");
                sortierteBuecher.forEach(System.out::println);

                System.out.println("\n5. Schreibe sortierte Liste in JSON-Datei...");
                schreibeJsonDatei(sortierteBuecher, PATH + JSON_OUTPUT);
                System.out.println("   ✅ Datei '" + JSON_OUTPUT + "' wurde erstellt.");

            }
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Buch> liesXmlDatei(String dateiName) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Buch.class, BuecherListe.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        BuecherListe liste = (BuecherListe) unmarshaller.unmarshal(new File(dateiName));
        return liste.getBuecher();
    }

    private static void schreibeJsonDatei(List<Buch> buecher, String dateiName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(dateiName), buecher);
    }

    @XmlRootElement(name = "Buecher")
    static class BuecherListe {
        private List<Buch> buecher = new ArrayList<>();

        @XmlElement(name = "Buch")
        public List<Buch> getBuecher() {
            return buecher;
        }

        public void setBuecher(List<Buch> buecher) {
            this.buecher = buecher;
        }
    }
}