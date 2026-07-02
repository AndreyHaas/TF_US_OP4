package main.java.tag04.unterricht;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import main.java.tag04.unterricht.model.Entry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DomParser {

  public static void main(String[] args) {
    DomParser.parseXml();
  }

  private static List<Entry> parseXml() {
    List<Entry> entries = new ArrayList<>();

    // Definiert eine Fabrik-API zur Erzeugung von DOM-Parsern:
    // (JH) Die Factory erzeugt Builder, die spaeter den Parser bereitstellen ;)
    // (JH) So umstaendlich da man den Parser erst noch konfigurieren moechte. (Mit Validierung, Schema, Sicherheit etc.)
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    try {
      // optional, aber empfohlen:
      // Sichere Verarbeitung von XML, Vermeidung von Angriffen wie XML External Entities (XXE)
      // https://portswigger.net/web-security/xxe
      // (JH) Diese Zeile aktiviert Sicherheitsmechanismen des XML-Parsers
      factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

      // Wir haben unserem XML Dokument eine Document Type Definition (DTD) gegeben.
      // Damit können wir das Dokument vor der Verarbeitung validieren.
      // https://wiki.selfhtml.org/wiki/XML/DTD
      factory.setValidating(true);

      // Eine andere (JH:modernere) Variante zum Validieren von XML Dokumenten ist das XML Schema:
//            factory.setSchema(SchemaFactory.newDefaultInstance().newSchema(new File("resource/input.xsd")));

      // (JH) Endlich erzeugen wir den nu konfigurierten Parser zum Einlesen von XML-Dateien
      DocumentBuilder builder = factory.newDocumentBuilder();

      // (JH) Falls beim Einlesen etwas schiefgeht meldet sich der Parser.
      // Es gibt drei Methoden:
      builder.setErrorHandler(new ErrorHandler() {
        // (JH) Eine Warnung, das XML kann trotzdem gelesen werden
        @Override
        public void warning(SAXParseException exception) throws SAXException {
          System.err.println(exception.getMessage());
        }

        // (JH) Es wird eine Exception geschmissen. Das Parsen wird abgebrochen.
        @Override
        public void error(SAXParseException exception) throws SAXException {
          System.err.println(exception.getMessage());
          throw exception;
        }

        // (JH) Ein schwerer Fehler. Auch hier beendet der Parser die Verarbeitung.
        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
          System.err.println(exception.getMessage());
          throw exception;
        }
      });

      // (JH) Bis hierhin wurde noch kein XML eingelesen.
      // Wir haben lediglich den Parser konfiguriert.
      // Mit parse() wird die XML-Datei vollständig gelesen, optional validiert
      // und als DOM-Baum im Arbeitsspeicher aufgebaut.
      Document document = builder.parse("src/main/java/tag04/resources/input.xml");
      System.out.println(document.getDocumentElement().getNodeName());
      NodeList list = document.getElementsByTagName("entry");

      for (int i = 0; i < list.getLength(); i++) {
        Node item = list.item(i);
        if (item.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) item;
          String id = element.getAttribute("id");

        }
      }

    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }

    return entries;
  }
}
