package main.java.tag06.src.template;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.*;
import java.time.LocalDate;

// Einfache Klassen, wie wir sie bisher immer geschrieben haben, werden als POJO (Plain Old Java Object) bezeichnet.
// Bei den POJO gibt es keine Regeln zur Benennung von Attribute und Methoden (natürlich wird trotzdem die allgemeine Konvention verwendet).
// Java Beans sind auch einfach nur normale Klassen, mit zwei Besonderheiten:
// Java Beans implementieren typischerweise das Serializable-Interface.
// Das Serializable-Interface hat selbst keine Vorgaben,
// aber es sollten öffentliche Getter und Setter für Attribute erstellt werden und es muss ein parameterloser Konstruktor vorhanden sein.
// Außerdem werden die Attribute einer Java Bean unterteilt in einfache, indizierte, gebundene und eingeschränkte Eigenschaften,
// wobei es keine Pflicht ist, gebundene und eingeschränkte Eigenschaften zu verwenden.
public class Client implements PropertyChangeListener, VetoableChangeListener
{
    public static void main(String[] args)
    {
        Client client = new Client();

		String[] hobbies = new String[] {"Spielen", "Schreiben", "Schlafen"};
		PersonBean person = new PersonBean("Vorname", "Ahuja", LocalDate.of(1990,5,12), hobbies);
		person.addPropertyChangeListener(client);
		person.addVetoableChangeListener(client);

        System.out.println(person);

        // Vorname ändern: Löst PropertyChangeEvent über PropertyChangeSupport aus.
        person.setVorname("Kamya");

        try
        {
            // Geburtsdatum ändern: Löst PropertyChangeEvent über VetoableChangeSupport aus.
            person.setGeburtsdatum(LocalDate.of(1700, 1, 1));
        }
        catch (PropertyVetoException e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println(person);


        // Serialisierung:
        // https://de.wikipedia.org/wiki/Serialisierung
        System.out.println("\nSerialisierung:");

		try (FileOutputStream fileOutputStream = new FileOutputStream("person.dat");
			 ObjectOutputStream out = new ObjectOutputStream(fileOutputStream))
		{
			out.writeObject(person);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		person = null;
		// De-Serialisierung:
		try (FileInputStream fileInputStream = new FileInputStream("person.dat");
			 ObjectInputStream in = new ObjectInputStream(fileInputStream))
		{
			Object o = in.readObject(); // readObject() gibt das gelesene Objekt vom Datentyp Object zurück.
			if (o instanceof PersonBean) // Damit wir es weiterverarbeiten können, müssen wir es in den richtigen Typen konvertieren.
				person = (PersonBean) o;
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		System.out.println(person);

	}

    // Listener-Methode für PropertyChangeSupport:
    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        System.out.println("Etwas hat sich geändert!");
        System.out.println(evt);
    }

	// Listener-Methode für VetoableChangeSupport:
	@Override
	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
	{
		System.out.println("Etwas soll sich ändern!");
		System.out.println(evt);

        // Das Geburtsdatum einer Person ändert sich normalerweise nicht. Wird das trotzdem gemacht, werfen wir eine Exception!
        // (Natürlich lässt sich sowas auch über den Setter direkt lösen. Das hier ist nur ein einfaches Beispiel.)
        if (evt.getOldValue() != null)
            throw new PropertyVetoException("Alter Wert ist nicht Null", evt);
    }
}
