/*
Schreiben Sie eine Klasse "Konto", die folgende Eigenschaften hat: Kontonummer (eindeutiger Integer), Kontostand (double) und Inhaber (Person-Objekt). Implementieren Sie diese Klasse als Java Bean, indem Sie private Felder für jede Eigenschaft erstellen und öffentliche Getter- und Setter-Methoden für den Zugriff auf diese Felder bereitstellen.

Schreiben Sie dann ein kleines Programm, das mehrere Instanzen der Konto-Klasse erstellt. Legen Sie einige Werte für die Kontonummer, den Kontostand und den Inhaber fest. Verwenden Sie dann die Getter-Methoden, um sicherzustellen, dass die Werte korrekt gespeichert wurden.

Erweitern Sie das Programm nun um eine Methode "transfer()", mit der Geld von einem Konto auf ein anderes überwiesen werden kann. Die Methode sollte drei Parameter haben: das Quellkonto und das Zielkonto sowie einen Betrag zum Überweisen.

Implementieren Sie einen PropertyChangeSupport für den Kontostand. Wird der Kontostand geändert, soll die Main-Klasse den aktuellen Kontostand ausgeben.
 */

package main.java.tag06.src.aufgaben;

import java.beans.JavaBean;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

class Konto implements Serializable
{
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private int kontonummer;
    private double kontostand;
    private Person inhaber;

    public int getKontonummer()
    {
        return kontonummer;
    }

    public void setKontonummer(int kontonummer)
    {
        this.kontonummer = kontonummer;
    }

    public double getKontostand() {
        return kontostand;
    }

    public void setKontostand(double kontostand)
    {
        double old = this.kontostand;
        this.kontostand = kontostand;
        propertyChangeSupport.firePropertyChange("Kontostand", old, kontostand);
    }

    public Person getInhaber(){
        return inhaber;
    }

    public void setInhaber(Person inhaber){
        this.inhaber=inhaber;
    }


    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }


    public static void transfer(Konto von, Konto zu, double betrag)
    {
        if (von.getKontostand() >= betrag)
        {
            von.setKontostand(von.getKontostand() - betrag);
            zu.setKontostand(zu.getKontostand() + betrag);
            System.out.println(betrag + " wurde von " + von.getKontonummer() + " auf " + zu.getKontonummer() + " überwiesen.");
        }
        else
        {
            System.out.println("Fehler: Nicht genügend Guthaben vorhanden!");
        }
    }

}

class Person implements Serializable
{
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Person()
    {

    }
}

public class Lösung_2 implements PropertyChangeListener
{
    public static void main(String[] args)
    {
        Lösung_2 l = new Lösung_2();

        Person p = new Person();
        p.setName("Sabine");

        Konto k1 = new Konto();
        k1.setKontonummer(12345);
        k1.setKontostand(1000.0);
        k1.setInhaber(p);
        k1.addPropertyChangeListener(l);

        Konto k2 = new Konto();
        k2.setKontonummer(67890);
        k2.setKontostand(500.0);
        k2.setInhaber(p);
        k2.addPropertyChangeListener(l);

        Konto.transfer(k1, k2, 200.0);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        System.out.println("Konto: " + ((Konto)evt.getSource()).getKontonummer());
        System.out.println(evt.getPropertyName() + " geändert!");
        System.out.println("Neuer Wert: " + evt.getNewValue());
    }
}
