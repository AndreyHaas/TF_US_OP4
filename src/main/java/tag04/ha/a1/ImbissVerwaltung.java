package main.java.tag04.ha.a1;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.util.List;

public class ImbissVerwaltung {

  public static void main() {
    try {
      JAXBContext context = JAXBContext.newInstance(WarenListe.class, Speise.class, Getraenk.class);

      File xmlFile = new File("src/main/java/tag04/ha/a1/Verkaufswaren.xml");
      WarenListe warenListe = (WarenListe) context.createUnmarshaller().unmarshal(xmlFile);

      List<Ware> waren = warenListe.getWaren();
      System.out.println("=== Imbiss-Waren (aus XML) ===" + System.lineSeparator());

      for (int i = 0; i < waren.size(); i++) {
        System.out.println((i + 1) + ". " + waren.get(i));
      }

    } catch (JAXBException e) {
      System.err.println("Fehler beim Laden dem XML: " + e.getMessage());
      e.printStackTrace();
    }
  }
}