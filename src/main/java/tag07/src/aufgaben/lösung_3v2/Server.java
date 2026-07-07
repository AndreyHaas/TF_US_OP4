package main.java.tag07.src.aufgaben.lösung_3v2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
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
			e.printStackTrace();
		}
	}

	private static void start(int port) throws IOException
	{
		try (ServerSocket serverSocket = new ServerSocket(port))
		{
			System.out.println("Warte auf eingehende Verbindungen...");

			try (Socket clientSocket = serverSocket.accept();
				 ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
				 ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream()))
			{
				System.out.println("Client verbunden...");

				System.out.println("Erhalte Bücher...");

				List<Buch> bücher = new ArrayList<>();
				int anzahl = in.readInt();
				for (int i = 0; i < anzahl; i++)
				{
					bücher.add((Buch) in.readObject());
				}

				bücher.sort(Comparator.comparingInt(Buch::getId));

				for (Buch b : bücher)
				{
					out.writeObject(b);
				}
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}
}
