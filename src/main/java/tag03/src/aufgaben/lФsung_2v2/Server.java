package main.java.tag03.src.aufgaben.lФsung_2v2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server
{
	public static void main(String[] args)
	{
		try
		{
			start(1234);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static void start(int port) throws IOException
	{
		try (ServerSocket serverSocket = new ServerSocket(port))
		{
			while (true)
			{
				List<Object> objectList = new ArrayList<>();

				System.out.println("Warte auf Verbindungen...");
				try (Socket clientSocket = serverSocket.accept();
					 ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream()))
				{
					System.out.println("Empfange Daten...");
					Object o;
					do
					{
						o = inputStream.readObject();

						if (o != null)
						{
							objectList.add(o);
						}

					} while (o != null);

					objectList.forEach(System.out::println);
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
