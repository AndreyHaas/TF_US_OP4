package main.java.tag03.src.aufgaben.lФsung_1v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args)
	{
		Client c = new Client();
		try
		{
			c.connect("localhost", 1234);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void connect(String adresse, int port) throws IOException
	{
		try (Socket clientSocket = new Socket(adresse, port);
			PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
		{
			System.out.println("Verbunden");

			String message = "";

			do
			{
				System.out.print("> ");
				message = scanner.nextLine();
				writer.println(message);
				do
				{
					String response = reader.readLine();
					System.out.println(response);
				} while (reader.ready());
			} while (!message.equalsIgnoreCase("stop"));
		}
	}
}
