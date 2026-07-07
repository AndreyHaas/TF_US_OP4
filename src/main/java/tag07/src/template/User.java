package main.java.tag07.src.template;

import java.util.List;

/*
 * Dieses POJO (Plain Old Java Object) dient als Datenmodell.
 *
 * Jackson wandelt Objekte dieser Klasse automatisch
 * in JSON um (Serialisierung) und wieder zurück
 * in Java-Objekte (De-Serialisierung).
 */
 
 /*
 
/*
 * POJO = Plain Old Java Object
 *
 * Diese Klasse stellt unser Datenmodell dar.
 * Sie enthält ausschließlich Attribute sowie Getter, Setter und Konstruktoren.
 *
 * Jackson verwendet Objekte dieser Klasse, um:
 *
 * - Java-Objekte -> JSON umzuwandeln (Serialisierung)
 * - JSON -> Java-Objekte umzuwandeln (De-Serialisierung)
 *
 * Ein Objekt dieser Klasse entspricht später einem JSON-Objekt.
 */

public class User {

  /*
   * Diese Attribute bilden den Zustand unseres Objektes.
   * Beim Serialisieren erzeugt Jackson daraus JSON-Schlüssel.
   *
   * Beispiel:
   *
   * {
   *   "name": "...",
   *   "age": ...,
   *   "messages": [...]
   * }
   */
  private String name;
  private int age;
  private List<String> messages;

  /*
   * Getter liefern die Werte der Attribute zurück.
   *
   * Beim Serialisieren sucht Jackson automatisch nach öffentlichen
   * Getter-Methoden und verwendet deren Rückgabewerte.
   *
   * Beispiel:
   *
   * getName()
   *      ↓
   * "name": "Max"
   */

  // Moderne Jackson-Versionen können auch mit Annotationen (@JsonCreator, @JsonProperty) oder direkt über Felder arbeiten.
  public String getName() {
    return name;
  }

  /*
   * Setter werden beim De-Serialisieren verwendet.
   *
   * Jackson liest zunächst die JSON-Datei und erzeugt anschließend
   * ein neues User-Objekt.
   *
   * Danach werden die Werte über die Setter in das Objekt geschrieben.
   *
   * Beispiel:
   *
   * JSON:
   * "name":"Max"
   *
   * führt intern ungefähr zu:
   *
   * user.setName("Max");
   */
  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public List<String> getMessages() {
    return messages;
  }

  public void setMessages(List<String> messages) {
    this.messages = messages;
  }

  /*
   * Dieser Konstruktor wird verwendet,
   * wenn wir selbst ein User-Objekt erzeugen möchten.
   */
  public User(String name, int age, List<String> messages) {
    this.name = name;
    this.age = age;
    this.messages = messages;
  }

  /*
   * Jackson benötigt beim De-Serialisieren einen parameterlosen
   * Konstruktor.
   *
   * Intern passiert vereinfacht ungefähr Folgendes:
   *
   * User user = new User();
   * user.setName(...);
   * user.setAge(...);
   * user.setMessages(...);
   *
   * Ohne diesen Konstruktor kann Jackson das Objekt
   * nicht erzeugen.
   */
  // Wichtig! Zum De-Serialisieren ist ein parameterloser Konstruktor erforderlich!
  public User() {
  }

  /*
   * toString() dient ausschließlich der Konsolenausgabe.
   *
   * Dadurch können wir leicht überprüfen,
   * ob das Objekt korrekt serialisiert bzw.
   * de-serialisiert wurde.
   */
  @Override
  public String toString() {
    return "User{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", messages=" + messages +
        '}';
  }
}
