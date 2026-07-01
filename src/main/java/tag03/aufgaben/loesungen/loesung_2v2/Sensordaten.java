package main.java.tag03.aufgaben.loesungen.loesung_2v2;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Sensordaten implements Serializable
{
	private int[] messwerte;
	private String bezeichnung;
	private LocalDateTime erzeugtAm;

	public Sensordaten(int[] messwerte, String bezeichnung, LocalDateTime erzeugtAm)
	{
		this.messwerte = messwerte;
		this.bezeichnung = bezeichnung;
		this.erzeugtAm = erzeugtAm;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("Sensordaten{");
		sb.append("messwerte=").append(Arrays.toString(messwerte));
		sb.append(", bezeichnung='").append(bezeichnung).append('\'');
		sb.append(", erzeugtAm=").append(erzeugtAm);
		sb.append('}');
		return sb.toString();
	}
}
