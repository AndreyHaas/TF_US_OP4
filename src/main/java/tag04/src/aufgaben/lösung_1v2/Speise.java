package aufgaben.lösung_1v2;

import java.util.ArrayList;
import java.util.List;

/**
 * Spezialisierung von Verkaufsware
 */
class Speise extends Verkaufsware
{
	/**
	 * Liste der Zutaten
	 */
	private List<String> zutaten;

	public List<String> getZutaten()
	{
		return new ArrayList<>(zutaten);
	}

	public Speise(String bezeichnung, double preis, List<String> zutaten)
	{
		super(bezeichnung, preis);
		this.zutaten = zutaten;
	}

	/**
	 * @return Gibt einen String mit den Daten der Basisklasse plus zusätzlich den Zutaten zurück.
	 */
	@Override
	public String toString()
	{
		// Mit dem StringBuilder können bequem Strings zusammengebaut werden
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString()).append("\n"); // Die Informationen der Basisklasse
		builder.append("Zutaten:\n");
		for (String s : zutaten)
		{
			builder.append(s).append("\n"); // Zutaten der Zutatenliste dem String hinzufügen
		}

		return builder.toString();
	}
}
