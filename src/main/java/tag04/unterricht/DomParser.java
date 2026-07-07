package main.java.tag04.unterricht;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
    List<Entry> entries = DomParser.parseXml();
    entries.forEach(System.out::println);
    DomParser.writeXML(entries);
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
      // factory.setSchema(SchemaFactory.newDefaultInstance().newSchema(new File("resource/input.xsd")));

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
      // Das ganze XML Dokument wird als DOM-Baum (Document) repraesentiert.
      Document document = builder.parse("src/main/java/tag04/resources/input.xml");

      // Gibt den Namen des Wurzelelements (Root-Element) des XML-Dokuments aus.
      // In unserem Fall lautet der Name "op4_xml".
      System.out.println("Document Element: " + document.getDocumentElement().getNodeName());

      NodeList list = document.getElementsByTagName("entry");

      for (int i = 0; i < list.getLength(); i++) {
        // Liefert den i-ten Knoten aus der NodeList.
        // Da die NodeList verschiedene Knotentypen enthalten kann,
        // erhalten wir zunächst eine allgemeine Node.
        Node node = list.item(i);

        // Prüfen, ob es sich tatsächlich um einen Element-Knoten handelt.
        // Das ist eine Sicherheitsmaßnahme, bevor wir den Knoten casten.
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          // Cast von Node auf Element.
          // Erst dadurch stehen uns element-spezifische Methoden wie
          // getAttribute() oder getElementsByTagName() zur Verfügung.
          Element element = (Element) node;

          // Liest den Wert des Attributs "id".
          // Rückgabewert ist immer ein String.
          String id = element.getAttribute("id");

          Node titleNode = element.getElementsByTagName("title").item(0);

          String title = titleNode.getTextContent();

          String description = element.getElementsByTagName("description").item(0).getTextContent();
          String date = element.getElementsByTagName("date").item(0).getTextContent();

          // Das balance-Element benötigen wir später zusätzlich,
          // da wir auf dessen Attribut "currency" zugreifen möchten.
          Node balanceNode = element.getElementsByTagName("balance").item(0);
          String balance = balanceNode.getTextContent();

          // Attribute werden im DOM ebenfalls als Nodes dargestellt.
          // getAttributes() liefert eine NamedNodeMap,
          // getNamedItem("currency") liefert das Attribut "currency".
          Node currencyNode = balanceNode.getAttributes().getNamedItem("currency");

          // Liest den Attributwert über die allgemeine Node-Schnittstelle.
          String currency = currencyNode.getNodeValue();

          // Mapping der XML-Daten auf unser Java-Objekt (POJO).
          // Dabei werden Datentypen passend umgewandelt.
          Entry entry = new Entry(id, title, description, LocalDate.parse(date),
              Integer.parseInt(balance), currency);

          entries.add(entry);
        }
      }
    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }

    return entries;
  }

  private static void writeXML(List<Entry> entries) {
    /*
     * Die DocumentBuilderFactory ist eine Fabrikklasse.
     * Sie erzeugt DocumentBuilder-Objekte.
     * Eine Factory kapselt die Erzeugung komplexer Objekte.
     * Dadurch muss der Entwickler nicht wissen,
     * welche konkrete Implementierung verwendet wird.
     */
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    try {
      // Der DocumentBuilder erzeugt neue XML-Dokumente oder liest (parst)
      // vorhandene XML Dokumente.
      DocumentBuilder builder = factory.newDocumentBuilder();

      // Wir erzeugen ein leeres Dokument:
      Document document = builder.newDocument();

      Element root = document.createElement("op4_xml");

      for (Entry e : entries) {
        Element entry = document.createElement("entry");
        entry.setAttribute("id", e.getId());

        Element title = document.createElement("title");
        title.setTextContent(e.getTitle());
        entry.appendChild(title);

        Element description = document.createElement("description");
        description.setTextContent(e.getDescription());
        entry.appendChild(description);

        Element date = document.createElement("date");
        date.setTextContent(e.getDate().toString());
        entry.appendChild(date);

        Element balance = document.createElement("currency");
        balance.setAttribute("currency", e.getCurrency());
        balance.setTextContent(String.valueOf(e.getBalance()));
        entry.appendChild(balance);

        root.appendChild(entry);
      }

      /*
       * Nachdem alle Entry-Elemente erzeugt wurden,
       * wird das Wurzelelement dem Dokument hinzugefügt.
       *
       * Erst jetzt ist das XML-Dokument vollständig.
       */
      document.appendChild(root);

      // Ein Transformer wandelt den DOM-Baum in ein anderes Ausgabeformat um.
      // Hier wollen wir den DOM-Baum zu einer XML-Datei transformieren.
      TransformerFactory transformerFactory = TransformerFactory.newInstance();

      Transformer transformer = transformerFactory.newTransformer();

      transformer.setOutputProperty(OutputKeys.INDENT, "yes");

      // DOMSource beschreibt die Eingabequelle.
      // Hier ist dies unser im Speicher aufgebauter DOM-Baum (repraesentiert durch das document):
      DOMSource source = new DOMSource(document);

      StreamResult result = new StreamResult(new File("src/main/java/tag04/resources/output.xml"));

      transformer.transform(source, result);
    } catch (ParserConfigurationException | TransformerException e) {
      e.printStackTrace();
    }
  }
}