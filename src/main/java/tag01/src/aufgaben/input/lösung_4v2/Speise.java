package aufgaben.input.lösung_4v2;

import java.util.List;


public class Speise extends Verkaufsware
{
	private List<String> zutaten;

	public Speise(String bezeichnung, double preis, List<String> zutaten)
	{
		super(bezeichnung, preis);
		this.zutaten = zutaten;
	}

	@Override
	public String toString()
	{
		return super.toString() + " Speise{" +
		"zutaten=" + zutaten + "}";
	}
}
