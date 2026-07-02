package main.java.tag01.ha.a4;

import java.util.List;

public class Speise extends Ware {
  private final List<String> zutaten;

  public Speise(String bezeichnung, double preis, List<String> zutaten) {
    super(bezeichnung, preis);
    this.zutaten = List.copyOf(zutaten);
  }

  public List<String> getZutaten() {
    return zutaten;
  }

  @Override
  public String toString() {
    return super.toString() + " – Zutaten: " + String.join(", ", zutaten);
  }

  @Override
  public String toCsvString() {
    return bezeichnung + "," + preis + "," + String.join(",", zutaten);
  }
}