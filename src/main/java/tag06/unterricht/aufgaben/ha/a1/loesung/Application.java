package main.java.tag06.unterricht.aufgaben.ha.a1.loesung;

public class Application {

  public static void main() {
    Artikel artikel = new Artikel();
    artikel.setNummer(1001);
    artikel.setBezeichnung("Laptop Dell XPS");
    artikel.setPreis(1299.99);

    Artikel artikel1 = new Artikel(1002, "Maus Logitech MX", 79.99);

    System.out.println("=== Artikel 1 ===");
    System.out.println("Nummer: " + artikel.getNummer());
    System.out.println("Bezeichnung: " + artikel.getBezeichnung());
    System.out.println("Preis: " + artikel.getPreis() + " €");

    System.out.println(System.lineSeparator() + "=== Artikel 2 ===");
    System.out.println("Nummer: " + artikel1.getNummer());
    System.out.println("Bezeichnung: " + artikel1.getBezeichnung());
    System.out.println("Preis: " + artikel1.getPreis() + " €");

    System.out.println(System.lineSeparator() + "=== toString() ===");
    System.out.println(artikel);
    System.out.println(artikel1);

    Artikel artikel3 = new Artikel(1001, "Laptop Dell XPS", 1299.99);
    System.out.println(System.lineSeparator() + "=== equals() Test ===");
    System.out.println("artikel.equals(artikel3): " + artikel.equals(artikel3)); // true
    System.out.println("artikel.equals(artikel1): " + artikel.equals(artikel1)); // false
  }
}