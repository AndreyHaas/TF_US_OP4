package main.java.tag06.src.aufgaben.lösung_2v2;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/*
Schreiben Sie eine Klasse "Konto", die folgende Eigenschaften hat: Kontonummer (eindeutiger Integer), Kontostand (double) und Inhaber (Person-Objekt). Implementieren Sie diese Klasse als Java Bean, indem Sie private Felder für jede Eigenschaft erstellen und öffentliche Getter- und Setter-Methoden für den Zugriff auf diese Felder bereitstellen.

Schreiben Sie dann ein kleines Programm, das mehrere Instanzen der Konto-Klasse erstellt. Legen Sie einige Werte für die Kontonummer, den Kontostand und den Inhaber fest. Verwenden Sie dann die Getter-Methoden, um sicherzustellen, dass die Werte korrekt gespeichert wurden.

Erweitern Sie das Programm nun um eine Methode "transfer()", mit der Geld von einem Konto auf ein anderes überwiesen werden kann. Die Methode sollte drei Parameter haben: das Quellkonto und das Zielkonto sowie einen Betrag zum Überweisen.

Implementieren Sie einen PropertyChangeSupport für den Kontostand. Wird der Kontostand geändert, soll die Main-Klasse den aktuellen Kontostand ausgeben.
 */
public class Main implements PropertyChangeListener
{
	public static void main(String[] args)
	{
		Main m = new Main();
		Person p = new Person("Alex");
		Konto k1 = new Konto(1, 100, p);
		k1.addPropertyChangeListener(m);
		Konto k2 = new Konto(2, 200, p);
		k2.addPropertyChangeListener(m);

		System.out.println(k1.getKontonummer());
		System.out.println(k1.getKontostand());
		System.out.println(k1.getInhaber().getName());

		System.out.println(k2.getKontonummer());
		System.out.println(k2.getKontostand());
		System.out.println(k2.getInhaber().getName());

		System.out.println("Überweisung von k1 auf k2");
		if (Konto.transfer(k1, k2, 150.00))
			System.out.println("Überweisung erfolgreich");
		else
			System.out.println("Überweisung fehlgeschlagen");

		System.out.println("Überweisung von k2 auf k1");
		if (k2.transfer(k1, 150))
			System.out.println("Überweisung erfolgreich");
		else
			System.out.println("Überweisung fehlgeschlagen");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		if (evt.getSource() instanceof Konto)
		{
			Konto k = (Konto) evt.getSource();
			System.out.printf("Kontostand hat sich geändert. Alter Wert: %.2f - Neuer Wert: %.2f - Kontonummer: %d\n", (double)evt.getOldValue(), k.getKontostand(), k.getKontonummer());
		}
	}
}
