package main.java.tag06.src.aufgaben.lösung_2v2;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Konto implements Serializable
{
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

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

	public double getKontostand()
	{
		return kontostand;
	}

	public void setKontostand(double kontostand)
	{
		double old = this.kontostand;
		this.kontostand = kontostand;
		propertyChangeSupport.firePropertyChange("kontostand", old, this.kontostand);
	}

	public Person getInhaber()
	{
		return inhaber;
	}

	public void setInhaber(Person inhaber)
	{
		this.inhaber = inhaber;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public Konto(int kontonummer, double kontostand, Person inhaber)
	{
		this.kontonummer = kontonummer;
		this.kontostand = kontostand;
		this.inhaber = inhaber;
	}

	public Konto()
	{
	}

	public static boolean transfer(Konto quelle, Konto ziel, double betrag)
	{
		if (quelle.getKontostand() - betrag >= 0)
		{
			quelle.setKontostand(quelle.getKontostand() - betrag);
			ziel.setKontostand(ziel.getKontostand() + betrag);
			return true;
		}
		return false;
	}

	public boolean transfer(Konto ziel, double betrag)
	{
		if (this.getKontostand() - betrag >= 0)
		{
			this.setKontostand(this.getKontostand() - betrag);
			ziel.setKontostand(ziel.getKontostand() + betrag);
			return true;
		}
		return false;
	}
}
