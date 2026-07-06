package main.java.tag06.unterricht.aufgaben.ha.a2.loesung;

import java.io.Serial;
import java.io.Serializable;

public class Person implements Serializable {

  @Serial
  private static final long serialVersionUID = 1908154446958319775L;

  private String vorname;
  private String nachname;

  public Person() {}

  public Person(String vorname, String nachname) {
    this.vorname = vorname;
    this.nachname = nachname;
  }

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Person person = (Person) o;
    return vorname.equals(person.vorname) && nachname.equals(person.nachname);
  }

  @Override
  public int hashCode() {
    int result = vorname.hashCode();
    result = 31 * result + nachname.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return vorname + " " + nachname;
  }
}