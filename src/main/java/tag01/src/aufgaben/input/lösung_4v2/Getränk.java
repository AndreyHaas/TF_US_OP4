package tag01.src.aufgaben.input.lösung_4v2;

public class Getränk extends Verkaufsware
{
	private int füllmenge;

	public Getränk(String bezeichnung, double preis, int füllmenge)
	{
		super(bezeichnung, preis);
		this.füllmenge = füllmenge;
	}

	@Override
	public String toString()
	{
		return super.toString() + " Getränk{" +
		"füllmenge=" + füllmenge + "}";
	}
}
