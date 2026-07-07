package main.java.tag07.src.aufgaben.lösung_1;

import java.io.File;
import tools.jackson.databind.ObjectMapper;


public class Lösung {

  private static final String FILENAME = "resources/loesung.json";

  public static void main(String[] args) {
    serialisieren(erstelleAuto());
    Auto a = deserialisieren();
    System.out.println(a);
  }


  private static void serialisieren(Auto auto) {
    ObjectMapper mapper = new ObjectMapper();

    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILENAME), auto);

      String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(auto);
      System.out.println(jsonInString);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static Auto deserialisieren() {

    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.readValue(new File(FILENAME), Auto.class);

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private static Auto erstelleAuto() {
    Auto a = new Auto();
    a.setFarbe("Schwarz");
    a.setGeschwindigkeit(150);
    a.setMarke("Audi");
    return a;
  }
}

class Auto {

  private String marke;
  private int geschwindigkeit;
  private String farbe;

  public String getMarke() {
    return marke;
  }

  public void setMarke(String marke) {
    this.marke = marke;
  }

  public int getGeschwindigkeit() {
    return geschwindigkeit;
  }

  public void setGeschwindigkeit(int geschwindigkeit) {
    this.geschwindigkeit = geschwindigkeit;
  }

  public String getFarbe() {
    return farbe;
  }

  public void setFarbe(String farbe) {
    this.farbe = farbe;
  }

  public Auto() {
  }

  @Override
  public String toString() {
    return "Auto [marke=" + marke + ", geschwindigkeit=" + geschwindigkeit + ", Farbe=" + farbe
        + "]";
  }

}