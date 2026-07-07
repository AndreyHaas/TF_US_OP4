package main.java.tag07.src.aufgaben.lösung_3v2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import tools.jackson.databind.ObjectMapper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client
{
	public static void main(String[] args)
	{
		try
		{
			connect("localhost", 1234);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void connect(String host, int port) throws IOException
	{
		try (Socket clientSocket = new Socket(host, port);
			 ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			 ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream()))
		{
			System.out.println("Verbindung mit Server hergestellt.");

			System.out.println("Sende Bücher...");
			List<Buch> bücher = getBücher();
			// Damit der Server weiß, wie viele Bücher wir ihm schicken wollen, schicken wir ihm zuerst die Anzahl.
			int anzahl = bücher.size();
			out.writeInt(anzahl);
			for (Buch b : bücher)
			{
				out.writeObject(b);
			}

			System.out.println("Erhalte Bücher sortiert...");
			bücher = new ArrayList<>();
			for (int i = 0; i < anzahl; i++)
			{
				bücher.add((Buch)in.readObject());
			}

			bücher.forEach(System.out::println);
			writeJson(bücher);
		}
		catch (ClassNotFoundException | ParserConfigurationException | SAXException e)
		{
			e.printStackTrace();
		}
	}

	private static List<Buch> getBücher() throws ParserConfigurationException, IOException, SAXException
	{
		List<Buch> bücher = new ArrayList<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = factory.newDocumentBuilder();

		Document document = builder.parse("resources/Regal.xml");

		NodeList nodeList = document.getElementsByTagName("Buch");

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			Element element = (Element) node;
			int id = Integer.parseInt(element.getAttribute("id"));
			String title = element.getElementsByTagName("title").item(0).getTextContent();
			String description = element.getElementsByTagName("description").item(0).getTextContent();
			LocalDate date = LocalDate.parse(element.getElementsByTagName("date").item(0).getTextContent());
			Node priceNode = element.getElementsByTagName("price").item(0);
			double price = Double.parseDouble(priceNode.getTextContent());
			String currency = priceNode.getAttributes().getNamedItem("currency").getNodeValue();

			Buch buch = new Buch(id, title, description, date, price, currency);
			bücher.add(buch);
		}

		return bücher;
	}

	private static void writeJson(List<Buch> bücher) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();

		mapper.writerWithDefaultPrettyPrinter().writeValue(new File("resources/Regal.json"), bücher);
	}
}