package main.java.tag06.unterricht.aufgaben;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

// Eine JavaBean ist ein POJO mit einigen Konventionen:
// die wichtigsten vier Punkte:
// - privater Zustand (private Attribute)
// öffentliche Getter und Setter
// parameterloser Konstruktor
// implementiert üblicherweise Serializable

// Serializable ist ein Marker Interface.
// Es besitzt keine Methoden
// es signalisiert der JVM lediglich:
// Objekte dieser Klasse dürfen serialisiert werden.
public class PersonBean implements Serializable {

  private String vorname;
  private String nachname;
  private LocalDate geburtsdatum;
  private String[] hobbies;

  // PersonBean ist im Beobachtermuster das Subjekt.
  // Für gebundene Eigenschaften(Databinding - binded properties) verwenden wir ein Observer pattern,
  // um andere Objekte über den veränderten Zustand zu informieren.
  // In dem FAll von JavaBeans verwenden wir speziell die Klasse "PropertyChangeSupport".
  // PropertyChangeSupport verwaltet intern eine Liste aller Listener(Beobachter).
  private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

  // Für eingeschränkte Eigenschaften verwenden wir ebenfalls das Observer Muster.
  // Allerdings wird bei PropertyChange nur über die Zustandsänderung informiert,
  // bei VetoableChange kann die Änderung abgelehnt werden, was dann zu einer Exception führt.
  private final VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);

  public PersonBean() {
  }

  // Weitere überladene Konstruktoren sind aber erlaubt:
  public PersonBean(String vorname, String nachname, LocalDate geburtsdatum, String[] hobbies) {
    this.vorname = vorname;
    this.nachname = nachname;
    this.geburtsdatum = geburtsdatum;
    this.hobbies = hobbies;
  }

  public String getVorname() {
    return vorname;
  }

  // Wenn sich der WErt des Vornames ändert, wollen wir ein Ereignis auslösen, welches
  // Beobachterobjekte abonniert haben:
  public void setVorname(String vorname) {
    String old = this.vorname;

    this.vorname = vorname;

    // Wir lösen das PropertyChange-Event aus, wenn sich der Wert geändert hat.
    // Damit wird 'vorname' zu einer gebundenen (Databinding) Eigenschaft.
    propertyChangeSupport.firePropertyChange("vorname", old, this.vorname);
  }

  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    String old = this.nachname;
    this.nachname = nachname;

    propertyChangeSupport.firePropertyChange("nachname", old, this.nachname);
  }

  public LocalDate getGeburtsdatum() {
    return geburtsdatum;
  }

  /*
  VetoableChangeSupport in der Praxis deutlich seltener verwendet wird als PropertyChangeSupport.
  Viele Java-Entwickler kommen jahrelang ohne ihn aus.
  Für den Unterricht ist er aber ein schönes Beispiel dafür, wie das Beobachtermuster nicht nur zum Benachrichtigen,
  sondern auch zum Validieren und Kontrollieren von Zustandsänderungen eingesetzt werden kann.
*/
  public void setGeburtsdatum(LocalDate geburtsdatum) throws PropertyVetoException {
    // Wir lösen das VetoableChange-Event aus, wenn sich der Wert ändern soll!
    // Diese Änderung kann abgelehnt werden von den Listener's (Beobachtern).
    // Es kommt also nur zu einer Zustandsänderung des gebundenen Attributs, wenn es nicht abgelehnt wird
    // (wenn kein Veto eingelegt wurde von den Listener's).
    // Damit wird geburtsdatum zu einer eingeschränkten, gebundenen Eigenschaft.
    vetoableChangeSupport.fireVetoableChange("geburtsdatum", this.geburtsdatum, geburtsdatum);

    this.geburtsdatum = geburtsdatum;
  }

  public String[] getHobbies() {
    return hobbies;
  }

  public void setHobbies(String[] hobbies) {
    this.hobbies = hobbies;
  }

  // Observer werden als Listener dem PropertyChangeSupport hinzugefügt:
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.removePropertyChangeListener(listener);
  }

  // Observer werden als Listener dem VetoableChangeSupport hinzugefügt:
  public void addVetoableChangeListener(VetoableChangeListener listener) {
    vetoableChangeSupport.addVetoableChangeListener(listener);
  }

  public void removeVetoableChangeListener(VetoableChangeListener listener) {
    vetoableChangeSupport.removeVetoableChangeListener(listener);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonBean that = (PersonBean) o;
    return Objects.equals(this.vorname, that.vorname) && Objects.equals(this.nachname,
        that.nachname) && Objects.equals(this.geburtsdatum, that.geburtsdatum)
        && Objects.deepEquals(this.hobbies, that.hobbies);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vorname, nachname, geburtsdatum, Arrays.hashCode(hobbies));
  }

  @Override
  public String toString() {
    return "PersonBean{" +
        "vorname='" + vorname + '\'' +
        ", nachname='" + nachname + '\'' +
        ", geburtsdatum=" + geburtsdatum +
        ", hobbies=" + Arrays.toString(hobbies) +
        '}';
  }
}