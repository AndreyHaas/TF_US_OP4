package aufgaben.input.lösung_4v2;

public abstract class Verkaufsware
{
	private String bezeichnung;
	private double preis;

	protected Verkaufsware(String bezeichnung, double preis)
	{
		this.bezeichnung = bezeichnung;
		this.preis = preis;
	}

	@Override
	public String toString()
	{
		return "Verkaufsware{" +
		"bezeichnung='" + bezeichnung + '\'' +
		", preis=" + preis +
		'}';
	}
}
