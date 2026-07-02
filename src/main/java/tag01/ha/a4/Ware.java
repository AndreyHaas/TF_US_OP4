package main.java.tag01.ha.a4;

public abstract class Ware {

  protected String bezeichnung;
  protected double preis;

  protected Ware(String bezeichnung, double preis) {
    this.bezeichnung = bezeichnung;
    this.preis = preis;
  }

  public String getBezeichnung() {
    return bezeichnung;
  }

  public double getPreis() {
    return preis;
  }

  public void setPreis(double preis) {
    this.preis = preis;
  }

  @Override
  public String toString() {
    return bezeichnung + " (" + String.format("%.2f", preis) + " €)";
  }

  public abstract String toCsvString();
}