package main.java.tag07.src.aufgaben.lösung_3;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class Client
{
    private Socket socket;

    private ObjectOutputStream objectWriter;
    private ObjectInputStream objectReader;

    public static void main(String[] args)
    {
        // Create instance of class
        Client client = new Client();

        try
        {
            client.readXML("resources/Regal.xml");

            client.connect("localhost", 1234);

            List<Buch> bücher = client.sendMessage(Buch.buchListe);

            if (client.ObjectToJsonFile(bücher, "resources/Regal.json"))
            {
                System.out.println("Ergebnisdatei geschrieben");
            }
            else
            {
                System.out.println("Fehler beim schreiben!");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                client.disconnect();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    private void readXML(String path) throws Exception
    {
        File file = new File(path);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        document.getDocumentElement().normalize();

        NodeList booklist = document.getElementsByTagName("Buch");

        for (int i = 0; i < booklist.getLength(); i++)
        {
            Node node = booklist.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;

                String id = element.getAttribute("id");

                String title = element.getElementsByTagName("title").item(0).getTextContent();

                String description = element.getElementsByTagName("description").item(0).getTextContent();

                String date = element.getElementsByTagName("date").item(0).getTextContent();

                Node priceNode = element.getElementsByTagName("price").item(0);
                int price = Integer.parseInt(priceNode.getTextContent());
                String currency = priceNode.getAttributes().getNamedItem("currency").getNodeValue();

                new Buch(id, title, description, date, price, currency);
            }
        }
    }

    private boolean ObjectToJsonFile(Object object, String path)
    {
        try
        {
            // Anstatt jedes mal "writerWithDefaultPrettyPrinter" aufzurufen, kann man das INDENT_OUTPUT Feature setzen.
            ObjectMapper mapper = JsonMapper.builder().enable(SerializationFeature.INDENT_OUTPUT).build();
            mapper.writeValue(new File(path), object);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private void connect(String ip, int port) throws IOException
    {
        socket = new Socket(ip, port);

        if (!socket.isConnected())
        {
            throw new IOException("Kein Server gefunden!");
        }

        objectWriter = new ObjectOutputStream(socket.getOutputStream());
        objectReader = new ObjectInputStream(socket.getInputStream());
    }

    private List<Buch> sendMessage(List<Buch> bücher) throws IOException, ClassNotFoundException
    {
        objectWriter.writeObject(bücher);

        @SuppressWarnings("unchecked")
        List<Buch> liste = (List<Buch>) objectReader.readObject();

        return liste;
    }

    private void disconnect() throws IOException
    {
        objectReader.close();
        objectWriter.close();
        objectWriter.close();
        socket.close();
    }
}
