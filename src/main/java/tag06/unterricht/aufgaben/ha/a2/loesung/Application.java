package main.java.tag06.unterricht.aufgaben.ha.a2.loesung;

import java.beans.PropertyChangeListener;
import org.jetbrains.annotations.NotNull;

public class Application {

  public static void main() {

    Person person1 = new Person("Andreas", "Haas");
    Person person2 = new Person("Max", "Mustermann");

    Konto konto1 = new Konto(1, 2000.00, person1);
    Konto konto2 = new Konto(2, 500.00, person2);

    createKontoListener("kontostand", konto1);
    createKontoListener("kontostand", konto2);

    System.out.println("=== Vor der Überweisung ===");
    System.out.println(konto1);
    System.out.println(konto2);
    System.out.println();

    System.out.println("=== Überweisung ===");
    Konto.transfer(konto1, konto2, 250.00);
    System.out.println();

    System.out.println("=== Nach der Überweisung ===");
    System.out.println(konto1);
    System.out.println(konto2);
    System.out.println();

    System.out.println("=== Fehlerfälle ===");
    Konto.transfer(konto1, konto2, 10000.00); // Nicht genug Geld
    Konto.transfer(konto1, konto1, 50.00);    // Quelle = Ziel
    Konto.transfer(konto1, konto2, -10.00);   // Negativer Betrag
  }

  private static @NotNull PropertyChangeListener createKontoListener(String attribut, Konto konto) {
    return event -> {
      if (attribut.equals(event.getPropertyName())) {
        System.out.printf("Konto %d: " + attribut + " ändert sich von %.2f € auf %.2f €" + System.lineSeparator(),
            konto.getKontonummer(), event.getOldValue(), event.getNewValue());
      }
    };
  }
}