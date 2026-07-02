package main.java.tag04.src.template;

// Videotutorial zu XML und Java
//https://www.youtube.com/watch?v=11D6AtMhLLs
//https://www.youtube.com/watch?v=HfGWVy-eMRc

// Tutorials für XML
//https://www.w3schools.com/xml
//https://www.baeldung.com/java-xml

// DOM steht für Document Object Model. Es ist eine plattform- und programmiersprachen unabhängige Schnittstelle,
// die von verschiedenen Programmiersprachen wie Java, JavaScript, Python usw. unterstützt wird.
// Das DOM stellt ein hierarchisches Modell eines XML-Dokuments bereit und ermöglicht es Entwicklern, auf das Dokument zuzugreifen und es zu manipulieren.
// Das DOM repräsentiert ein XML-Dokument als Baumstruktur aus Knotenobjekten (Nodes), die Elemente, Attribute oder Textknoten darstellen können.
// Jeder Knoten im Baum hat einen Typ (z.B. ELEMENT_NODE für Elemente oder ATTRIBUTE_NODE für Attribute) und kann über seine Eigenschaften (wie Name oder Wert) sowie über seine Beziehungen zu anderen Knoten im Baum navigiert werden.

import main.java.tag04.handout.Entry;
import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DOM_Parser
{
	public static void main(String[] args)
	{
		List<Entry> entries = parseXML();
		entries.forEach(System.out::println);
		writeXML(entries);
	}

	private static List<Entry> parseXML()
	{
		List<Entry> entries = new ArrayList<>();

		// Definiert eine Fabrik-API zur Erzeugung von DOM-Parsern:
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try
        {
            // optional, aber empfohlen:
            // Sichere Verarbeitung von XML, Vermeidung von Angriffen wie XML External Entities (XXE)
            // https://portswigger.net/web-security/xxe
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // Wir haben unserem XML Dokument eine Document Type Definition (DTD) gegeben.
            // Damit können wir das Dokument vor der Verarbeitung validieren.
            // https://wiki.selfhtml.org/wiki/XML/DTD
            factory.setValidating(true);

            // Eine andere Variante zum Validieren von XML Dokumenten ist das XML Schema.
            //factory.setSchema(SchemaFactory.newDefaultInstance().newSchema(new File("resources/input.xsd")));

            // Erzeugt einen neuen DocumentBuilder:
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Error Handling für die Validierung setzen:
            builder.setErrorHandler(new ErrorHandler()
            {
                @Override
                public void warning(SAXParseException exception)
                {
                    System.err.println(exception.getMessage());
                }

                @Override
                public void error(SAXParseException exception) throws SAXException
                {
                    System.err.println(exception.getMessage());
                    throw exception;
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException
                {
                    System.err.println(exception.getMessage());
                    throw exception;
                }
            });

			// Achtung auf richtigen Import! org.w3c.dom.*
			Document document = builder.parse("resources/input.xml");

			System.out.println("Document Element: " + document.getDocumentElement().getNodeName());

            // Abfrage aller Knoten, die "entry" heißen:
            NodeList list = document.getElementsByTagName("entry");

			for (int i = 0; i < list.getLength(); i++)
			{
				// Jedes Element in der NodeList ist eine Node.
				Node node = list.item(i);

                // Handelt es sich um ein Element Node?
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    // Wenn ja, können wir den Knoten in ein Element casten.
                    Element element = (Element) node;

					// Fragt den Wert des Attributs ab und gibt String zurück.
					String id = element.getAttribute("id");

                    // Abfrage des Kind-Knotens mit dem Namen "title".
                    // Der Typ ist auch hier ELEMENT_NODE
                    Node titleNode = element.getElementsByTagName("title").item(0);
                    System.out.println(titleNode.getNodeType());
                    String title = titleNode.getTextContent();

                    String description = element.getElementsByTagName("description").item(0).getTextContent();
                    String date = element.getElementsByTagName("date").item(0).getTextContent();

					Node balanceNode = element.getElementsByTagName("balance").item(0);
					String balance = balanceNode.getTextContent();

                    // getAttributes() liefert eine NodeMap zurück.
                    // Das heißt: Attribute werden so ebenfalls als Node dargestellt. Der NodeType ist hier ATTRIBUTE_NODE.
					Node currencyNode = balanceNode.getAttributes().getNamedItem("currency");
					System.out.println(currencyNode.getNodeType());
					String currency = currencyNode.getNodeValue();

                    // Da wir wissen, dass es sich bei dem Knoten um ein Attribute Node handelt, können wir auch in Attr casten.
                    Attr currencyNodeAttribute = (Attr) currencyNode;
                    currency = currencyNodeAttribute.getValue();

					// Objekte der Model-Klasse erzeugen:
					Entry entry = new Entry(id, title, description, LocalDate.parse(date), Integer.parseInt(balance), currency);
					entries.add(entry);
				}
			}
		}
		catch (ParserConfigurationException | SAXException | IOException e)
		{
			e.printStackTrace();
		}

		return entries;

	}

	private static void writeXML(List<Entry> entries)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Erstelle ein neues Document:
            Document document = builder.newDocument();

            // Erstelle das Wurzelelement des Dokuments:
            Element root = document.createElement("op4_xml");

			for (Entry e : entries)
			{
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

				Element balance = document.createElement("balance");
				balance.setAttribute("currency", e.getCurrency());
				balance.setTextContent(String.valueOf(e.getBalance()));
				entry.appendChild(balance);

                root.appendChild(entry);
            }

            // Füge das Wurzelelement dem Dokument hinzu:
            document.appendChild(root);

			// Um aus dem Dokument eine Datei zu erstellen, verwenden wir einen Transformer.
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// Damit hat das Dokument Zeilenumbrüche für bessere Lesbarkeit.
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("resources/output.xml"));

            // Erzeuge das Dokument als Datei.
            transformer.transform(source, result);
        }
        catch (ParserConfigurationException | TransformerException e)
        {
            e.printStackTrace();
        }
    }
}
