package main.java.tag04.ha.a1;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "waren")
public class WarenListe {
  private List<Ware> waren = new ArrayList<>();

  @XmlElement(name = "speise", type = Speise.class)
  public List<Ware> getWaren() { return waren; }
  public void setWaren(List<Ware> waren) { this.waren = waren; }
}