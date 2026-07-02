package main.java.tag04.ha.a1;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"bezeichnung", "preis"})
@XmlSeeAlso({Speise.class, Getraenk.class})
public abstract class Ware {
  private String bezeichnung;
  private double preis;

  protected Ware() {} // Für JAXB

  protected Ware(String bezeichnung, double preis) {
    this.bezeichnung = bezeichnung;
    this.preis = preis;
  }

  @XmlElement
  public String getBezeichnung() { return bezeichnung; }
  public void setBezeichnung(String bezeichnung) { this.bezeichnung = bezeichnung; }

  @XmlElement
  public double getPreis() { return preis; }
  public void setPreis(double preis) { this.preis = preis; }

  @Override
  public String toString() {
    return bezeichnung + " (" + String.format("%.2f", preis) + " €)";
  }
}