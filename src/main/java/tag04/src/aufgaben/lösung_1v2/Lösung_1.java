package aufgaben.lösung_1v2;

import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lösung_1
{
	public static void main(String[] args)
	{
		List<Verkaufsware> verkaufswaren = ladeXML();
		verkaufswaren.forEach(System.out::println);
	}

	private static List<Verkaufsware> ladeXML()
	{
		List<Verkaufsware> verkaufswaren = new ArrayList<>();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		// Validierung ist optional.
		builderFactory.setValidating(true);

		try
		{
			DocumentBuilder builder = builderFactory.newDocumentBuilder();

			// Ist die Validierung aktiviert, sollte ein ErrorHandler festgelegt werden.
			builder.setErrorHandler(new ErrorHandler()
			{
				@Override
				public void warning(SAXParseException exception) throws SAXException
				{
					System.out.println(exception.getMessage());
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

			Document document = builder.parse("resources/Verkaufswaren.xml");

			Element root = document.getDocumentElement();
			NodeList nodeList = root.getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Node node = nodeList.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					NamedNodeMap attributes = node.getAttributes();
					String bezeichnung = attributes.getNamedItem("bezeichnung").getNodeValue();
					String preis = attributes.getNamedItem("preis").getNodeValue();

					if (node.getNodeName().equals("Speise"))
					{
						List<String> zutatenList = new ArrayList<>();

						NodeList zutatenNodeList = ((Element)node).getElementsByTagName("Zutat");
						for (int j = 0; j < zutatenNodeList.getLength(); j++)
						{
							zutatenList.add(zutatenNodeList.item(j).getTextContent());
						}

						verkaufswaren.add(new Speise(bezeichnung, Double.parseDouble(preis), zutatenList));
					}
					else if (node.getNodeName().equals("Getränk"))
					{
						String füllmenge = attributes.getNamedItem("füllmenge").getNodeValue();

						verkaufswaren.add(new Getränk(bezeichnung, Double.parseDouble(preis), Integer.parseInt(füllmenge)));
					}
				}
			}
		}
		catch (ParserConfigurationException | IOException | SAXException e)
		{
			e.printStackTrace();
		}

		return verkaufswaren;
	}
}
