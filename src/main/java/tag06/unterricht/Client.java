package main.java.tag06.unterricht;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import main.java.tag06.unterricht.aufgaben.PersonBean;

public class Client implements PropertyChangeListener, VetoableChangeListener {

  public static void main() {
    Client client = new Client();

    String[] hobbies = new String[]{"Schach Spielen", "Schreiben", "Schlafen"};

    // Unser Subjekt im Beobachtermuster:
    PersonBean person = new PersonBean("Dimitriv", "Kasparov", LocalDate.of(1960, 5, 12), hobbies);

    person.addPropertyChangeListener(client);
    person.addVetoableChangeListener(client);

    System.out.println(person);

    person.setVorname("Garri");

    try {
      person.setGeburtsdatum(LocalDate.of(1700, 1, 1));
    } catch (PropertyVetoException e) {
      System.out.println(e.getMessage());
    }

    System.out.println(person);

    // Anderes Thema: Serialisierung. JavaBean implementiert serializable Interface und kann
    // daher serialisiert werden. Das bedeutet das Objekt mit seinen Zustaenden wird in einen
    // Bytestrom umgewandelt und kann dann ueber z.B. ein Netzwerk verschickt werden.
    // Den umgekehrten Prozess um aus einem Bytestrom wieder ein Objekt zu machen nennt man
    // dementsprechend Deserialisierung.

    /* Man speichert also nicht den Java-Code, sondern den aktuellen Zustand des Objekts.
     */
    // Serialisierung:
    // https://de.wikipedia.org/wiki/Serialisierung
    System.out.println("\nSerialisierung:");

    try (FileOutputStream fileOutputStream = new FileOutputStream("person.dat");
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream))
    {
      outputStream.writeObject(person);
    }catch (IOException ex) {
      ex.printStackTrace();
    }

    // De-Serialization
    person = null;

    try (FileInputStream inputStream = new FileInputStream("person.dat");
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {

      Object o = objectInputStream.readObject();

      // ab Java 14
      if (o instanceof PersonBean personBean) {
        person = personBean;
      }

    } catch (IOException | ClassNotFoundException ex) {
      System.err.println("Fehler beim Laden der Person: " + ex.getMessage());
      ex.printStackTrace();
    }

    System.out.println(person);
  }

  // Listener-Methode für PropertyChangeSupport:
  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    System.out.println(evt.getPropertyName() + " hat sich geaendert.");
    System.out.println(evt);
  }

  // Listener-Methode fuer VetoableChangesupport.
  @Override
  public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
    System.out.println(evt.getPropertyName() + " soll sich aendern!");
    System.out.println(evt);

    // Das Geburtsdatum einer Person ändert sich normalerweise nicht. Wird das trotzdem gemacht, werfen wir eine Exception!
    // (Natürlich lässt sich sowas auch über den Setter direkt lösen. Das hier ist nur ein einfaches Beispiel.)
    if (evt.getOldValue() != null) {
      throw new PropertyVetoException("Alter Wert fuer " + evt.getPropertyName()
          + " ist nicht null \nund darf sich nicht aendern!", evt);
    }
  }
}