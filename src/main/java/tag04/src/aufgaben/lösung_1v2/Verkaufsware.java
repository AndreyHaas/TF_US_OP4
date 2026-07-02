package aufgaben.lösung_1v2;

import java.text.NumberFormat;

/**
 * Basisklasse für die Ware, die zum Verkauf steht
 */
abstract class Verkaufsware
{
	private String bezeichnung;
	private double preis;

	public String getBezeichnung()
	{
		return bezeichnung;
	}

	public double getPreis()
	{
		return preis;
	}


	// Konstruktoren in abstrakten Klassen sind in der Regel protected

	/**
	 * Konstruktor
	 *
	 * @param b Bezeichnung
	 * @param p Preis
	 */
	protected Verkaufsware(String b, double p)
	{
		bezeichnung = b;
		preis = p;
	}

	// ToString()-Methode überschreiben

	/**
	 * @return Gibt einen String mit Bezeichnung und Preis der Ware zurück.
	 */
	@Override
	public String toString()
	{
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String money = formatter.format(preis);
		return String.format("Bezeichnung: %s - Preis: %s", bezeichnung, money);
	}
}
