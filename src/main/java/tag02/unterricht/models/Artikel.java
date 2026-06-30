package tag02.unterricht.models;

import java.util.Objects;

public class Artikel {

  private final int nummer;
  private final String bezeichnung;
  private final double preis;
  private final String hersteller;

  public Artikel(int nummer, String bezeichnung, double preis, String hersteller) {
    this.nummer = nummer;
    this.bezeichnung = bezeichnung;
    this.preis = preis;
    this.hersteller = hersteller;
  }

  public int getNummer() {
    return nummer;
  }

  public String getBezeichnung() {
    return bezeichnung;
  }

  public double getPreis() {
    return preis;
  }

  public String getHersteller() {
    return hersteller;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Artikel artikel = (Artikel) o;
    return nummer == artikel.nummer && Double.compare(preis, artikel.preis) == 0
        && Objects.equals(bezeichnung, artikel.bezeichnung) && Objects.equals(
        hersteller, artikel.hersteller);
  }

  @Override
  public int hashCode() {
    int result = nummer;
    result = 31 * result + Objects.hashCode(bezeichnung);
    result = 31 * result + Double.hashCode(preis);
    result = 31 * result + Objects.hashCode(hersteller);
    return result;
  }

  @Override
  public String toString() {
    return "Artikel{" + "nummer=" + nummer
        + ", bezeichnung='" + bezeichnung + '\''
        + ", preis=" + preis
        + ", hersteller='" + hersteller + '\''
        + '}';
  }
}