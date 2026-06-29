package tag01.src.aufgaben.input.Lösung_4.imbiss;

/**
 * Spezialisierung von Verkaufsware
 */
class Getränk extends Verkaufsware
{
	private int füllmenge;

	public int getFüllmenge()
	{
		return füllmenge;
	}

	public Getränk(String bezeichnung, double preis, int füllmenge)
	{
		super(bezeichnung, preis);
		this.füllmenge = füllmenge;
	}

	/**
	 * @return Gibt einen String mit den Daten der Basisklasse plus zusätzlich der Füllmenge in Milliliter zurück.
	 */
	@Override
	public String toString()
	{
		return super.toString() + "\n" +
		"Füllmenge: " + füllmenge + " ml\n";
	}
}
