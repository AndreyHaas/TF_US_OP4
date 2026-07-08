package main.java.tag07.src.template;

// Ein JSON Tutorial
// https://www.w3schools.com/js/js_json_intro.asp
// Ein Online Editor zum Erstellen und Bearbeiten von JSON-Dokumenten
// https://jsoneditoronline.org/

/*
 * JSON (JavaScript Object Notation) ist ein einfaches Datenaustauschformat, das häufig in Web-Anwendungen verwendet wird.
 * Es ist sowohl leicht zu lesen/schreiben als auch sprachunabhängig.
 * Ein JSON-Wert kann ein anderes JSON-Objekt, ein Array, eine Zahl, eine Zeichenfolge,
 * ein boolescher Wert (true/false) oder null sein.
 *
 * Die Arbeit mit JSON-Daten in Java kann einfach sein, aber – wie fast alles
 * in Java – gibt es viele Optionen und Bibliotheken, aus denen wir wählen
 * können.
 */

// Jackson ist eine Mehrzweck-Java-Bibliothek zur Verarbeitung von JSON-Daten.
// Download hier: https://github.com/FasterXML/jackson
// Oder hier: https://mvnrepository.com/artifact/tools.jackson.core/jackson-databind/3.1.0
// Oder Import über Maven: tools.jackson.core:jackson-databind:3.1.3

// Importieren von Bibliotheken, z.B. den MySQL Connector:
// https://www.jetbrains.com/help/idea/library.html

/*
/*
 * Serialisiert ein Java-Objekt in eine JSON-Datei.
 *
 * Serialisierung bedeutet:
 *
 * Java-Objekt
 *        ↓
 *      Jackson
 *        ↓
 * JSON-Datei
 */

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonSerialisierung {

  public static void main() {
    List<String> messages = new ArrayList<>();
    messages.add("On the other side of the screen, it all looks so easy.");
    messages.add("Greetings, programs.");
    User user = new User("Creator4983", 33, messages);
    System.out.println(user);
    javaObjectToJson(user, "resources/tron.json");

    user = null;
    user = jsonToJavaObject("resources/tron.json");
    System.out.println(user);

    List<User> users = new ArrayList<>();
    users.add(user);

    User user2 = new User("Alan-One", 30, null);
    users.add(user2);

    listToJson(users, "resources/users.json");

    users = jsonToList("resources/users.json");
    System.out.println(users);
  }


  private static void javaObjectToJson(User user, String pfad) {
    // Doku für ObjectMapper: https://www.javadoc.io/doc/tools.jackson.core/jackson-databind/latest/tools.jackson.databind/tools/jackson/databind/ObjectMapper.html

    /*
     * ObjectMapper ist die zentrale Klasse der Jackson-Bibliothek.
     *
     * Er übernimmt nahezu alle JSON-Aufgaben:
     *
     * - Java -> JSON
     * - JSON -> Java
     * - Dateien lesen
     * - Dateien schreiben
     * - JSON-Strings erzeugen
     * - JSON-Strings einlesen
     */
    ObjectMapper mapper = new ObjectMapper();

    try {
      // Der Mapper verwendet "Reflection", um aus einem angegebenen Objekt alle Attribute zu finden, damit diese als JSON geschrieben werden können.
      // Über Reflection: https://www.baeldung.com/java-reflection

      /*
       * Demonstration von Reflection.
       *
       * Jackson verwendet intern Reflection,
       * um die Struktur eines Objektes zur Laufzeit
       * zu untersuchen.
       *
       * Reflection ermöglicht unter anderem:
       *
       * - Klassen untersuchen
       * - Attribute finden
       * - Methoden finden
       * - Methoden dynamisch aufrufen
       *
       * Diese drei Zeilen dienen nur als Beispiel.
       * Jackson erledigt dies intern automatisch.
       */
      // Beispiel für Reflection:
      Method[] methods = user.getClass().getMethods();
      System.out.println(
          Arrays.toString(methods)); // Die Reihenfolge der Methoden ist mehr oder weniger zufällig.
      System.out.println(user.getClass().getMethod("getName")
          .invoke(user)); // mit 'invoke()' kann eine Methode aufgerufen werden.
      System.out.println(
          Arrays.toString(user.getClass().getFields())); // Nur öffentliche Felder sichtbar.

      // 'writeValue' als direkter Aufruf schreibt alles in eine einzige Zeile.
      //mapper.writeValue(new File(pfad), user);
      // Der ObjectMapper sucht nach allen Gettern des Objektes und schreibt die Rückgaben der Methoden in die Datei.

      /*
       * writeValue(...)
       * schreibt das Objekt direkt in eine Datei.
       *
       * writerWithDefaultPrettyPrinter()
       * formatiert die JSON-Datei zusätzlich mit
       * Einrückungen und Zeilenumbrüchen.
       *
       * Das Ergebnis ist für Menschen wesentlich besser lesbar.
       */
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(pfad), user);

      String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
      System.out.println(json);
    } catch (JacksonException | NoSuchMethodException | IllegalAccessException |
             InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  /*
   * De-Serialisiert eine JSON-Datei.
   *
   * De-Serialisierung bedeutet:
   *
   * JSON-Datei
   *        ↓
   *     Jackson
   *        ↓
   * Java-Objekt
   */
  private static User jsonToJavaObject(String pfad) {
    ObjectMapper mapper = new ObjectMapper();

    try {
      // JSON aus einer Datei in ein Java Objekt umwandeln:
      // Dazu muss der Datentyp des gewünschten Objektes angegeben werden (hier: User.class)
      /*
       * readValue(...)
       * liest eine JSON-Datei ein und erzeugt daraus
       * ein Objekt des angegebenen Typs.
       *
       * User.class beschreibt den gewünschten Zieltyp.
       */
      User user = mapper.readValue(new File(pfad), User.class);

            /*String s = """
                       {
                         "name" : "Creator4983",
                         "age" : 33,
                         "messages" : [ "On the other side of the screen, it all looks so easy.", "Greetings, programs." ]
                       }
                       """;

            // Objekte können vom Mapper auch aus JSON-Strings erzeugt werden.
            user = mapper.readValue(s, User.class);*/

      return user;
    } catch (JacksonException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static void listToJson(List<User> users, String pfad) {
    ObjectMapper mapper = new ObjectMapper();
    // Ab Jackson 3.0 sind die Exceptions unchecked und ein Try-Catch ist nicht mehr zwingend erforderlich.
    mapper.writerWithDefaultPrettyPrinter().writeValue(new File(pfad), users);
  }

  private static List<User> jsonToList(String pfad) {
    ObjectMapper mapper = new ObjectMapper();
    // Wir können direkt in eine ArrayList de-serialisieren. Dazu verwenden wir TypeReference.
    //return mapper.readValue(new File(pfad), new TypeReference<ArrayList<User>>() {});

    // Oder wir de-serialisieren einfach in ein Array und verpacken das anschließend wieder in eine Liste.
    return Arrays.asList(mapper.readValue(new File(pfad), User[].class));
  }
}