package tag02.ha.a3.model;

import java.time.LocalDate;

public class ArbeitszeitEintrag {

  private final int mitarbeiterId;
  private final String name;
  private final LocalDate datum;
  private final double arbeitsstunden;

  public ArbeitszeitEintrag(int mitarbeiterId, String name, LocalDate datum,
      double arbeitsstunden) {
    this.mitarbeiterId = mitarbeiterId;
    this.name = name;
    this.datum = datum;
    this.arbeitsstunden = arbeitsstunden;
  }

  public int getMitarbeiterId() {
    return mitarbeiterId;
  }

  public String getName() {
    return name;
  }

  public LocalDate getDatum() {
    return datum;
  }

  public double getArbeitsstunden() {
    return arbeitsstunden;
  }

  @Override
  public String toString() {
    return String.format("%d;%s;%s;%.1f", mitarbeiterId, name, datum, arbeitsstunden);
  }
}