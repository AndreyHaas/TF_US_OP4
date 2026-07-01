package main.java.tag03.aufgaben.loesungen.loesung_2v2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Scanner;

public class Client
{
	private static final Scanner scanner = new Scanner(System.in);
	private static final Random random = new Random();

	public static void main(String[] args)
	{
		try
		{
			connect("localhost", 1234);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static void connect(String ip, int port) throws IOException
	{
		try (Socket clientSocket = new Socket(ip, port);
			 ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream()))
		{
			do
			{
				System.out.println("Daten erzeugen (j/n)?");
				System.out.print("> ");
				String eingabe = scanner.nextLine();
				if (!eingabe.equalsIgnoreCase("j"))
				{
					outputStream.writeObject(null);
					break;
				}

				Sensordaten s = erzeugeDaten();
				outputStream.writeObject(s);

			} while (true);
		}
	}

	private static Sensordaten erzeugeDaten()
	{
		int[] messwerte = new int[5];
		for (int i = 0; i < messwerte.length; i++)
			messwerte[i] = random.nextInt(50);

		System.out.print("Bezeichnung: ");
		String bezeichnung = scanner.nextLine();

		return new Sensordaten(messwerte, bezeichnung, LocalDateTime.now());
	}
}
