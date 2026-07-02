/* Sie betreiben einen Imbiss und möchten eine Verwaltungssoftware für die verkauften Waren erstellen.
 * Die Verkaufsware unterteilt sich in Speisen und Getränke. Alle Waren werden in der Basisklasse, von der kein Objekt erstellt werden darf, in einer Liste gespeichert.
 * Für alle Waren erfassen Sie die Bezeichnung und den Preis. Speisen bestehen zudem aus einer Auflistung der Zutaten, zu Getränken speichern Sie die Füllmenge in Milliliter.
 * Alle benötigten Daten werden über Konstruktoren erfasst.
 * Um alle Waren bequem ausgeben zu können, überschreiben Sie die toString-Methode der Klassen. Dabei vermeiden Sie doppelten Code und beachten die Abkapselung und Trennung von Darstellung und Programmlogik.
 *
 * Speise und Getränke sind in XML Dateien gespeichert. Eine Datei für Speisen und eine für Getränke (oder gerne auch beides in einer Datei).
 * Lassen Sie die Waren aus den XML Dateien importieren und ausgeben.
 *
 */

package aufgaben.lösung_1;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Lösung_1
{
    public static void main(String[] args)
    {
        try
        {
            ladeVerkaufswaren();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // Ausgabe
        for (Verkaufsware ware : Verkaufsware.warenliste)
        {
            System.out.println(ware); // Die toString()-Methode wird automatisch von print und println aufgerufen!
        }

    }

    public static void ladeVerkaufswaren() throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        // Wir haben unserem XML Dokument eine Document Type Definition (DTD) gegeben.
        // Damit können wir das Dokument vor der Verarbeitung validieren.
        // https://wiki.selfhtml.org/wiki/XML/DTD
        factory.setValidating(true);


        DocumentBuilder builder = factory.newDocumentBuilder();

        // Zur Validierung brauchen wir einen ErrorHandler, mit dem wir angeben, was bei Fehlern geschehen soll.
        // Der ErrorHandler ist ein Interface, von dem wir hier eine anonyme Klasse erzeugen.
        builder.setErrorHandler(new ErrorHandler()
        {
            @Override
            public void warning(SAXParseException exception) throws SAXException
            {
                exception.printStackTrace();
            }

            @Override
            public void error(SAXParseException exception) throws SAXException
            {
                throw exception;
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException
            {
                throw exception;
            }
        });

        Document document = builder.parse("resources/Verkaufswaren.xml");
        document.normalizeDocument();

        // Wurzel-Element abfragen: Verkaufswaren
        Element root = document.getDocumentElement();

        //System.out.println(root.getNodeName());

        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);
            // Unser Dokument enthält Newlines, welche als #text Nodes gelesen werden.
            // Wir wollen aber nur Element Nodes
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                //System.out.println(node.getNodeName());
                // In Element casten, damit wir die Attribute über den Attributnamen abfragen können
                Element element = (Element)node;

                // Speisen und Getränke haben beide Bezeichnung und Preis
                String bezeichnung = element.getAttribute("bezeichnung");
                String preis = element.getAttribute("preis");

                if (element.getNodeName().equals("Speise"))
                {
                    // Zutaten abfragen
                    NodeList zutatenNodes = element.getElementsByTagName("Zutaten").item(0).getChildNodes();

                    ArrayList<String> zutaten = new ArrayList<>();

                    for (int j = 0; j < zutatenNodes.getLength(); j++)
                    {
                        // Wir wollen nur Element Nodes, keine Text-Nodes.
                        if (zutatenNodes.item(j).getNodeType() == Node.ELEMENT_NODE)
                        {
                            zutaten.add(zutatenNodes.item(j).getTextContent());
                        }
                    }

                    new Speise(bezeichnung, Double.parseDouble(preis), zutaten);

                }
                else if (element.getNodeName().equals("Getränk"))
                {
                    // Getränke haben Füllmenge.
                    String füllmenge = element.getAttribute("füllmenge");

                    new Getränk(bezeichnung, Double.parseDouble(preis), Integer.parseInt(füllmenge));
                }
            }
        }
    }
}

/**
 * Basisklasse für die Ware, die zum Verkauf steht
 */
abstract class Verkaufsware
{
    /**
     * Liste, die alle Waren beinhaltet
     */
    public static final List<Verkaufsware> warenliste = new ArrayList<Verkaufsware>();

    private String bezeichnung;
    private double preis;

    public String getBezeichnung()
    {
        return bezeichnung;
    }

    public double getPreis()
    {
        return preis;
    }


    // Konstruktoren in abstrakten Klassen sind in der Regel protected
    /**
     * Konstruktor
     * @param b Bezeichnung
     * @param p Preis
     */
    protected Verkaufsware(String b, double p)
    {
        bezeichnung = b;
        preis = p;

        warenliste.add(this); // "this" zeigt auf das aktuelle Objekt, welches gerade durch den Konstruktor instanziiert wird.
        // heißt: In "this" steht die Speicheradresse des Objektes.
    }

    // ToString()-Methode überschreiben
    /**
     * @return Gibt einen String mit Bezeichnung und Preis der Ware zurück.
     */
    @Override
    public String toString()
    {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String money = formatter.format(preis);
        return String.format("Bezeichnung: %s - Preis: %s", bezeichnung, money);
    }
}

/**
 * Spezialisierung von Verkaufsware
 */
class Speise extends Verkaufsware
{
    /**
     * Liste der Zutaten
     */
    private List<String> zutaten;

    public List<String> getZutaten()
    {
        return new ArrayList<>(zutaten);
    }

    public Speise(String bezeichnung, double preis, ArrayList<String> zutaten)
    {
        super(bezeichnung, preis);
        this.zutaten = zutaten;
    }

    /**
     *
     * @return Gibt einen String mit den Daten der Basisklasse plus zusätzlich den Zutaten zurück.
     */
    @Override
    public String toString()
    {
        // Mit dem StringBuilder können bequem Strings zusammengebaut werden
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString()).append("\n"); // Die Informationen der Basisklasse
        builder.append("Zutaten:\n");
        for (String s : zutaten)
        {
            builder.append(s).append("\n"); // Zutaten der Zutatenliste dem String hinzufügen
        }

        return builder.toString();
    }
}

/**
 * Spezialisierung von Verkaufsware
 */
class Getränk extends Verkaufsware
{
    private int füllmenge;

    public int getFüllmenge()
    {
        return füllmenge;
    }

    public Getränk(String bezeichnung, double preis, int füllmenge)
    {
        super(bezeichnung, preis);
        this.füllmenge = füllmenge;
    }

    /**
     *
     * @return Gibt einen String mit den Daten der Basisklasse plus zusätzlich der Füllmenge in Milliliter zurück.
     */
    @Override
    public String toString()
    {
        return super.toString() + "\n" +
                "Füllmenge: " + füllmenge + " ml\n";
    }
}
