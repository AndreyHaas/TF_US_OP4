package main.java.tag08.aufgaben.ha3.template;

import main.java.tag08.aufgaben.ha3.model.Buch;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.List;

public class ClientHandler implements Runnable {

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("   ⏳ Warte auf Daten vom Client...");

            try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

                System.out.println("   📥 Streams erstellt.");

                //@SuppressWarnings("unchecked")
                List<Buch> buecher = (List<Buch>) in.readObject();
                System.out.println("   ✅ " + buecher.size() + " Bücher empfangen.");

                System.out.println("   🔄 Sortiere Bücher nach ID...");
                Collections.sort(buecher);
                System.out.println("   ✅ Sortierung abgeschlossen.");

                System.out.println("   📤 Sende sortierte Bücher zurück...");
                out.writeObject(buecher);
                out.flush();
                System.out.println("   ✅ " + buecher.size() + " Bücher gesendet.");

            } catch (ClassNotFoundException e) {
                System.err.println("❌ ClassNotFoundException: " + e.getMessage());
                e.printStackTrace();
            }

            socket.close();
            System.out.println("   🔌 Verbindung zum Client geschlossen.");

        } catch (IOException e) {
            System.err.println("❌ Fehler bei Client-Verarbeitung: " + e.getMessage());
            e.printStackTrace();
        }
    }
}