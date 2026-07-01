package main.java.tag03.aufgaben.loesungen.loesung_3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server
{
	public static void main(String[] args)
	{
		Server s = new Server();
		try
		{
			s.start(1234);
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	private void start(int port) throws IOException, ClassNotFoundException
	{
		try (ServerSocket server = new ServerSocket(port))
		{
			System.out.println("Wartet auf Verbindung...");
			Socket client = server.accept();
			System.out.println("Client verbunden...");

			// Die Wetterdaten wollen wir als Integer-Array an den Server schicken.
			// Integer-Arrays sind Objekte, die über ObjectInput- und ObjectOutputStreams übertragen werden können.
			ObjectInputStream input = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());

			// Das Array wird mit 'readObject()' gelesen und als Object zurückgegeben.
			Object object = input.readObject();
			// Wir wissen aber, dass der Client das Objekt als int[] gesendet hat, also können wir das Object in int[] casten.
			int[] wetterdaten = (int[]) object;

			// Die geforderten Werte ermitteln wir über Stream-API.
			// Wenn ein leeres Array an den Server geschickt wurde, wird eine Exception geworfen (die wir hier aber einfach ignorieren).
			double durchschnitt = Arrays.stream(wetterdaten).average().orElseThrow();
			int min = Arrays.stream(wetterdaten).min().orElseThrow();
			int max = Arrays.stream(wetterdaten).max().orElseThrow();

			// Die Ergebnisse schicken wir an den Client zurück.
			output.writeDouble(durchschnitt);
			output.writeInt(min);
			output.writeInt(max);
			output.flush();
		}
	}
}
