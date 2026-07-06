package main.java.tag06.src.template;

import java.beans.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

// Beispiel:
public class PersonBean implements Serializable
{
    // Einfache, indizierte (Arrays), gebundene (Observer-Muster) und eingeschränkte (Veto) Eigenschaft.
    // https://docs.oracle.com/javase/tutorial/javabeans/writing/properties.html

	private String vorname;
	private String nachname;
	private LocalDate geburtsdatum;
	private String[] hobbies;

	// Für gebundene Eigenschaften verwenden wir ein Observer Muster, um andere Objekte über den veränderten Zustand zu informieren.
	// In dem Fall von Java Beans verwenden wir speziell die Klasse "PropertyChangeSupport"
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    // Für eingeschränkte Eigenschaften verwenden wir ebenfalls das Observer Muster.
    // Allerdings wird bei PropertyChange nur über die Zustandsänderung informiert,
    // bei VetoableChange kann die Änderung abgelehnt werden, was dann zu einer Exception führt.
    private final VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);

    // Beans verfügen oft über einen parameterlosen Konstruktor.
    public PersonBean()
    {
    }

	// Weitere Konstruktoren sind trotzdem erlaubt:
	public PersonBean(String vorname, String nachname, LocalDate geburtsdatum, String[] hobbies)
	{
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtsdatum = geburtsdatum;
		this.hobbies = hobbies;
	}

    // Getter und Setter:
    public String getVorname()
    {
        return vorname;
    }

    public void setVorname(String vorname)
    {
        String old = this.vorname;
        this.vorname = vorname;

        // Wir lösen das PropertyChange-Event aus, wenn sich der Wert geändert hat.
        // Damit wird 'vorname' zu einer gebundenen Eigenschaft.
        propertyChangeSupport.firePropertyChange("vorname", old, this.vorname);
    }

    public String getNachname()
    {
        return nachname;
    }

    public void setNachname(String nachname)
    {
        this.nachname = nachname;
    }

    public LocalDate getGeburtsdatum()
    {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) throws PropertyVetoException
    {
        // Wir lösen das VetoableChange-Event aus, wenn sich der Wert ändern soll.
        // Diese Änderung wird nur dann durchgeführt, wenn sie nicht abgelehnt wurde.
        // Damit wird 'geburtsdatum' zu einer eingeschränkten Eigenschaft.
        vetoableChangeSupport.fireVetoableChange("geburtsdatum", this.geburtsdatum, geburtsdatum);

		this.geburtsdatum = geburtsdatum;
		// Optional: Geburtsdatum ebenfalls zu einer gebundenen Eigenschaft machen, damit Listener benachrichtigt werden, wenn die Eigenschaft erfolgreich geändert wurde.
	}

	public String[] getHobbies()
	{
		return hobbies;
	}

	public void setHobbies(String[] hobbies)
	{
		this.hobbies = hobbies;
	}

	// Observer werden als Listener dem PropertyChangeSupport hinzugefügt:
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.removePropertyChangeListener(listener);
	}
	// Observer werden als Listener dem VetoableChangeSupport hinzugefügt:
	public void addVetoableChangeListener(VetoableChangeListener listener)
	{
		vetoableChangeSupport.addVetoableChangeListener(listener);
	}
	public void removeVetoableChangeListener(VetoableChangeListener listener)
	{
		vetoableChangeSupport.removeVetoableChangeListener(listener);
	}

	// Typischerweise werden bei Beans auch die toString() überschrieben:
	@Override
	public String toString()
	{
		return "PersonBean{" +
		"vorname='" + vorname + '\'' +
		", nachname='" + nachname + '\'' +
		", geburtsdatum=" + geburtsdatum +
		", hobbies=" + Arrays.toString(hobbies) +
		'}';
	}

	// Neben toString() wird auch gerne noch equals() und hashCode() implementiert.
	@Override
	public boolean equals(Object o)
	{
		if (o == null || getClass() != o.getClass())
			return false;
		PersonBean that = (PersonBean) o;
		return Objects.equals(vorname, that.vorname) && Objects.equals(nachname, that.nachname) && Objects.equals(geburtsdatum, that.geburtsdatum) && Objects.deepEquals(hobbies, that.hobbies);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(vorname, nachname, geburtsdatum, Arrays.hashCode(hobbies));
	}
}
