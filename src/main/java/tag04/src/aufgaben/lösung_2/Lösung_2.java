package aufgaben.lösung_2;

/*
Gegeben ist eine XML-Datei mit folgendem Inhalt:

<bookstore>
  <book category="Science Fiction">
    <title>The Hitchhiker's Guide to the Galaxy</title>
    <author>Douglas Adams</author>
    <price>12.99</price>
  </book>

  <book category="Fantasy">
    <title>The Lord of the Rings</title>
    <author>J.R.R. Tolkien</author>
    <price>29.99</price>
  </book>
</bookstore>


Schreiben Sie ein Java-Programm, das diese Datei einliest und die Daten in einem DOM-Objekt (Klasse Document) speichert.
Erstellen Sie anschließend eine Methode `printData()`, die alle Bücher des Objekts auf der Konsole ausgibt.

Beispiel-Ausgabe:

---
Buch: The Hitchhiker's Guide to the Galaxy
Autor: Douglas Adams
Kategorie: Science Fiction
Preis: $12.99
---
Buch: The Lord of the Rings
Autor: J.R.R. Tolkien
Kategorie: Fantasy
Preis: $29.99
---
 */

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.text.NumberFormat;
import java.util.Locale;

public class Lösung_2
{
    public static void main(String[] args)
    {
        try
        {
            // Lade die XML-Datei
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("resources/bookstore.xml");

            // Rufe die Wurzel des Dokuments ab
            Element rootElement = doc.getDocumentElement();

            // Durchlaufe alle <book>-Elemente im Dokument
            NodeList bookNodes = rootElement.getElementsByTagName("book");

            for (int i = 0; i < bookNodes.getLength(); i++)
            {
                Node bookNode = bookNodes.item(i);

                if (bookNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element bookElement = (Element) bookNode;

                    String title = bookElement.getElementsByTagName("title").item(0).getTextContent();
                    String author = bookElement.getElementsByTagName("author").item(0).getTextContent();
                    String category = ((Attr) bookNode.getAttributes().getNamedItem("category")).getValue();
                    double price = Double.parseDouble(bookElement.getElementsByTagName("price").item(0).getTextContent());

                    System.out.println("---");
                    System.out.println("Buch: " + title);
                    System.out.println("Autor: " + author);
                    System.out.println("Kategorie: " + category);
                    NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
                    String money = formatter.format(price);
                    System.out.printf("Preis: %s\n", money);
                }
            }

            System.out.println("---");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
