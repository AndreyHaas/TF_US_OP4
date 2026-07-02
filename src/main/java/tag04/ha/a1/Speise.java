package main.java.tag04.ha.a1;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "speise")
public class Speise extends Ware {
  private List<String> zutaten = new ArrayList<>();

  public Speise() {}

  public Speise(String bezeichnung, double preis, List<String> zutaten) {
    super(bezeichnung, preis);
    this.zutaten = zutaten;
  }

  @XmlElementWrapper(name = "zutaten")
  @XmlElement(name = "zutat")
  public List<String> getZutaten() { return zutaten; }
  public void setZutaten(List<String> zutaten) { this.zutaten = zutaten; }

  @Override
  public String toString() {
    return super.toString() + " – Zutaten: " + String.join(", ", zutaten);
  }
}