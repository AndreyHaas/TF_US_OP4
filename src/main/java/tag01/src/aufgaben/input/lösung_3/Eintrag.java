package tag01.src.aufgaben.input.lösung_3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Eintrag
{
	private static final List<Eintrag> einträge = new ArrayList<>();

	public static List<Eintrag> getEinträge()
	{
		return new ArrayList<>(einträge);
	}

	enum Ergebnis
	{
		GRANTED("granted"),
		DENIED("denied");

		private final String value;

		Ergebnis(String value)
		{
			this.value = value;
		}

		public String getValue()
		{
			return value;
		}
	}

	private LocalDateTime zeit;
	private String name;
	private String IP;
	private String adresse;
	private Ergebnis ergebnis;

	public LocalDateTime getZeit()
	{
		return zeit;
	}

	public String getName()
	{
		return name;
	}

	public String getIP()
	{
		return IP;
	}

	public String getAdresse()
	{
		return adresse;
	}

	public Ergebnis getErgebnis()
	{
		return ergebnis;
	}

	public Eintrag(LocalDateTime zeit, String name, String IP, String adresse, Ergebnis ergebnis)
	{
		this.zeit = zeit;
		this.name = name;
		this.IP = IP;
		this.adresse = adresse;
		this.ergebnis = ergebnis;

		einträge.add(this);
	}

	@Override
	public String toString()
	{
		return zeit + "," + name + "," + IP + "," + adresse + "," + ergebnis.getValue();
	}

	public String toShortString()
	{
		return name + "," + IP + "," + ergebnis.getValue();
	}

}
