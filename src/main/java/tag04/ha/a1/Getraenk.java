package main.java.tag04.ha.a1;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "getraenk")
public class Getraenk extends Ware {
  private int fuellmenge;

  public Getraenk() {}

  public Getraenk(String bezeichnung, double preis, int fuellmenge) {
    super(bezeichnung, preis);
    this.fuellmenge = fuellmenge;
  }

  @XmlElement(name = "fuellmenge")
  public int getFuellmenge() { return fuellmenge; }
  public void setFuellmenge(int fuellmenge) { this.fuellmenge = fuellmenge; }

  @Override
  public String toString() {
    return super.toString() + " (" + fuellmenge + " ml)";
  }
}