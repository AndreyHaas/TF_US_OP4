package main.java.tag04.ha.a2;

import static javax.xml.parsers.DocumentBuilderFactory.newInstance;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BookstoreParser {

  public static void main() {
    try {
      DocumentBuilderFactory factory = newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();

      File xmlFile = new File("src/main/java/tag04/ha/a2/bookstore.xml");
      Document document = builder.parse(xmlFile);

      printData(document);

    } catch (Exception e) {
      System.err.println("Fehler beim Parsen dem XML: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void printData(Document document) {
    Element root = document.getDocumentElement();

    NodeList bookList = root.getElementsByTagName("book");

    for (int i = 0; i < bookList.getLength(); i++) {
      Node bookNode = bookList.item(i);

      if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
        Element bookElement = (Element) bookNode;

        System.out.println("---");
        System.out.println("Buch: " + bookElement.getElementsByTagName("title").item(0).getTextContent());
        System.out.println("Autor: " + bookElement.getElementsByTagName("author").item(0).getTextContent());
        System.out.println("Kategorie: " + bookElement.getAttribute("category"));
        System.out.println("Preis: $" + bookElement.getElementsByTagName("price").item(0).getTextContent());
      }
    }

    System.out.println("---");
  }
}