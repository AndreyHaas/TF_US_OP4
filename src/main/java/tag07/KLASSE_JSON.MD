# JSON & Jackson – Ein Leitfaden für den Unterricht

---

## 1. Was ist JSON?

**JSON** (JavaScript Object Notation) ist ein **textbasiertes, menschenlesbares Format** zum Speichern und Übertragen von Daten. Es ist sprachunabhängig und wird in fast allen modernen Programmiersprachen unterstützt.

### 1.1 JSON-Struktur

- Daten werden in **Schlüssel-Wert-Paaren** geschrieben.
- Schlüssel müssen in **doppelten Anführungszeichen** stehen.
- Werte können sein:
  - Strings (in `""`)
  - Zahlen (ohne Anführungszeichen)
  - Boolesche Werte (`true` / `false`)
  - Arrays (in `[]`)
  - Andere JSON-Objekte (in `{}`)
  - `null`

### 1.2 Beispiel

```json
{
  "name": "Creator4983",
  "age": 33,
  "messages": [
    "Greetings, programs.",
    "On the other side..."
  ]
}
```

> 💡 **Merke:** JSON ist **kein Java-Code**, sondern ein reines Textformat.

---

## 2. Jackson – JSON in Java

**Jackson** ist die bekannteste Java-Bibliothek, um Java-Objekte in JSON zu verwandeln (Serialisierung) und JSON zurück in Java-Objekte (Deserialisierung).

### 2.1 Wichtige Klassen

| Klasse | Aufgabe |
|--------|---------|
| `ObjectMapper` | Hauptklasse – liest und schreibt JSON |
| `TypeReference` | Hilfsklasse für generische Typen (z. B. `List<User>`) |

---

## 3. Einrichtung in IntelliJ IDEA

### 3.1 Via `pom.xml` (empfohlen für Projekte)

```xml
<dependency>
    <groupId>tools.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>3.1.3</version>
</dependency>
```

### 3.2 Via IntelliJ (ohne `pom.xml`)

| Schritt | Aktion |
|---------|--------|
| 1 | Öffne das **Maven-Fenster** (rechts: `Maven` oder `View → Tool Windows → Maven`). |
| 2 | Klicke auf das **`+`**‑Symbol (**Add Dependency**). |
| 3 | Gib die Koordinaten ein: <br> `tools.jackson.core` (GroupId) und `jackson-databind` (ArtifactId). |
| 4 | Wähle die Version `3.1.3` – klicke **OK**. |
| 5 | Die Bibliothek wird automatisch geladen und in den Classpath eingebunden. |

**Prüfung:** Erscheint beim Schreiben von `ObjectMapper` eine rote Welle? Dann ist die Dependency nicht korrekt eingebunden.

---

## 4. Jackson in Aktion – Der ObjectMapper

### 4.1 Java-Objekt → JSON (Serialisierung)

```java
ObjectMapper mapper = new ObjectMapper();

User user = new User("Creator4983", 33, List.of("Hallo", "Welt"));

// In Datei schreiben (mit Einrückungen)
mapper.writerWithDefaultPrettyPrinter()
      .writeValue(new File("user.json"), user);

// Als String ausgeben
String json = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(user);
System.out.println(json);
```

### 4.2 JSON → Java-Objekt (Deserialisierung)

```java
ObjectMapper mapper = new ObjectMapper();

User user = mapper.readValue(new File("user.json"), User.class);
System.out.println(user.getName());
```

### 4.3 Liste → JSON

```java
List<User> users = Arrays.asList(user1, user2);
mapper.writerWithDefaultPrettyPrinter()
      .writeValue(new File("users.json"), users);
```

### 4.4 JSON → Liste

```java
// Variante 1 – mit TypeReference
List<User> users = mapper.readValue(
    new File("users.json"),
    new TypeReference<ArrayList<User>>() {}
);

// Variante 2 – über Array
User[] array = mapper.readValue(new File("users.json"), User[].class);
List<User> userList = Arrays.asList(array);
```

---

## 5. Voraussetzungen in der Java-Klasse

Damit Jackson korrekt arbeiten kann, muss die Klasse folgende Bedingungen erfüllen:

| Bedingung | Beispiel |
|-----------|----------|
| **Standardkonstruktor** (parameterlos) | `public User() {}` |
| **Getter & Setter** für alle Felder | `getName()`, `setName()` |
| **Private Felder** | `private String name;` |

> ⚠️ **Ohne Standardkonstruktor oder Getter/Setter kann Jackson keine Objekte erzeugen oder befüllen!**

---

## 6. Wichtige Hilfsmittel für die Praxis

| Tool | Zweck |
|------|-------|
| **[JSON Editor Online][JSON Editor Online]** | JSON online validieren, formatieren und bearbeiten |
| **[W3Schools JSON Tutorial][W3Schools JSON]** | Einführung in die JSON-Syntax |
| **[Jackson-Dokumentation][Jackson Docs]** | Offizielle Referenz für alle Jackson-Features |

[JSON Editor Online]: https://jsoneditoronline.org
[W3Schools JSON]: https://www.w3schools.com/js/js_json_intro.asp
[Jackson Docs]: https://www.javadoc.io/doc/tools.jackson.core/jackson-databind/latest/tools.jackson.databind/tools/jackson/databind/ObjectMapper.html

---

## 7. Häufige Fehler und Lösungen

| Fehler | Ursache | Lösung |
|--------|---------|--------|
| `InvalidDefinitionException` | Kein Standardkonstruktor oder Getter/Setter fehlen | Konstruktor + Getter/Setter hinzufügen |
| `UnrecognizedPropertyException` | JSON-Feld existiert nicht in der Java-Klasse | `@JsonIgnoreProperties(ignoreUnknown = true)` verwenden |
| `JsonParseException` | JSON-Syntaxfehler (z. B. fehlende Anführungszeichen) | Mit JSON Editor Online prüfen und reparieren |

---

## 8. Zusammenfassung – Die wichtigsten Konzepte

| Konzept | Bedeutung |
|---------|-----------|
| **JSON** | Textformat für strukturierte Daten |
| **Jackson** | Java-Bibliothek zur JSON-Verarbeitung |
| **ObjectMapper** | Das Herz von Jackson – konvertiert Java ↔ JSON |
| **Serialisierung** | Java-Objekt → JSON (Schreiben) |
| **Deserialisierung** | JSON → Java-Objekt (Lesen) |