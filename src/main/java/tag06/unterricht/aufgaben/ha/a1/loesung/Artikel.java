package main.java.tag06.unterricht.aufgaben.ha.a1.loesung;

import java.io.Serial;
import java.io.Serializable;
import java.util.StringJoiner;

public class Artikel implements Serializable {

  @Serial
  private static final long serialVersionUID = -7205895614776881038L;

  private long nummer;
  private String bezeichnung;
  private double preis;

  public Artikel() {}

  public Artikel(long nummer, String bezeichnung, double preis) {
    this.nummer = nummer;
    this.bezeichnung = bezeichnung;
    this.preis = preis;
  }

  public long getNummer() {
    return nummer;
  }

  public void setNummer(long nummer) {
    this.nummer = nummer;
  }

  public String getBezeichnung() {
    return bezeichnung;
  }

  public void setBezeichnung(String bezeichnung) {
    this.bezeichnung = bezeichnung;
  }

  public double getPreis() {
    return preis;
  }

  public void setPreis(double preis) {
    this.preis = preis;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Artikel.class.getSimpleName() + "[", "]")
        .add("nummer=" + nummer)
        .add("bezeichnung='" + bezeichnung + "'")
        .add("preis=" + preis)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Artikel artikel = (Artikel) o;
    return nummer == artikel.nummer && Double.compare(preis, artikel.preis) == 0
        && bezeichnung.equals(artikel.bezeichnung);
  }

  @Override
  public int hashCode() {
    int result = Long.hashCode(nummer);
    result = 31 * result + bezeichnung.hashCode();
    result = 31 * result + Double.hashCode(preis);
    return result;
  }
}