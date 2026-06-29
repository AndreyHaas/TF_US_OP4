package aufgaben.input.Lösung_4.imbiss;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Basisklasse für die Ware, die zum Verkauf steht
 */
abstract class Verkaufsware
{
	/**
	 * Liste, die alle Waren beinhaltet
	 */
	public static final List<Verkaufsware> warenliste = new ArrayList<Verkaufsware>();

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

		warenliste.add(this); // "this" zeigt auf das aktuelle Objekt, welches gerade durch den Konstruktor instanziiert wird.
		// heißt: In "this" steht die Speicheradresse des Objektes.
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
