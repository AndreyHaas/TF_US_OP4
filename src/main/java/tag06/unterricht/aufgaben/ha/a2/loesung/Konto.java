package main.java.tag06.unterricht.aufgaben.ha.a2.loesung;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class Konto implements Serializable {

  @Serial
  private static final long serialVersionUID = 6771687556107149782L;

  private int kontonummer;
  private double kontostand;
  private Person inhaber;

  private final PropertyChangeSupport propertyChangeSupport =
      new PropertyChangeSupport(this);

  public Konto() {}

  public Konto(int kontonummer, double kontostand, Person inhaber) {
    this.kontonummer = kontonummer;
    this.kontostand = kontostand;
    this.inhaber = inhaber;
  }

  public int getKontonummer() {
    return kontonummer;
  }

  public void setKontonummer(int kontonummer) {
    this.kontonummer = kontonummer;
  }

  public double getKontostand() {
    return kontostand;
  }

  public void setKontostand(double kontostand) {
    double alterWert = this.kontostand;
    this.kontostand = kontostand;

    propertyChangeSupport.firePropertyChange("kontostand", alterWert, kontostand);
  }

  public Person getInhaber() {
    return inhaber;
  }

  public void setInhaber(Person inhaber) {
    this.inhaber = inhaber;
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.addPropertyChangeListener(listener);
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    propertyChangeSupport.removePropertyChangeListener(listener);
  }

  public static void transfer(Konto quelle, Konto ziel, double betrag) {
    if (betrag <= 0) {
      System.err.println("Betrag muss positiv sein!");
      return;
    }

    if (quelle.getKontostand() < betrag) {
      System.err.println("Nicht genügend Guthaben auf Konto " + quelle.getKontonummer() + "!");
      return;
    }

    if (quelle == ziel) {
      System.err.println("Quelle und Ziel dürfen nicht identisch sein!");
      return;
    }

    // Geld abbuchen
    quelle.setKontostand(quelle.getKontostand() - betrag);

    // Geld gutschreiben
    ziel.setKontostand(ziel.getKontostand() + betrag);

    System.out.printf("Überweisung von %.2f € von Konto %d auf Konto %d erfolgreich!%n",
        betrag, quelle.getKontonummer(), ziel.getKontonummer());
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Konto konto = (Konto) o;
    return kontonummer == konto.kontonummer
        && Double.compare(kontostand, konto.kontostand) == 0 && Objects.equals(
        inhaber, konto.inhaber) && propertyChangeSupport.equals(konto.propertyChangeSupport);
  }

  @Override
  public int hashCode() {
    int result = kontonummer;
    result = 31 * result + Double.hashCode(kontostand);
    result = 31 * result + Objects.hashCode(inhaber);
    result = 31 * result + propertyChangeSupport.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Konto.class.getSimpleName() + "[", "]")
        .add("kontonummer=" + kontonummer)
        .add("kontostand=" + kontostand)
        .add("inhaber=" + inhaber)
        .toString();
  }
}