package main.java.tag01.ha.a4;

public class Getraenk extends Ware {
  private final int fuellmengeMl;

  public Getraenk(String bezeichnung, double preis, int fuellmengeMl) {
    super(bezeichnung, preis);
    this.fuellmengeMl = fuellmengeMl;
  }

  public int getFuellmengeMl() {
    return fuellmengeMl;
  }

  @Override
  public String toString() {
    return super.toString() + " (" + fuellmengeMl + " ml)";
  }

  @Override
  public String toCsvString() {
    return bezeichnung + "," + preis + "," + fuellmengeMl;
  }
}