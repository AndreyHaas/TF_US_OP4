# JavaBeans – Von einfach zu komplex

---

## 📌 Was sind JavaBeans?

**JavaBeans** sind eine **Konvention** für wiederverwendbare Java-Klassen. Sie sind keine Bibliothek oder ein Framework, sondern ein **Standard**, der sicherstellt, dass Klassen bestimmten Regeln folgen – damit sie in Tools wie GUI-Buildern, Frameworks (Spring, JPA) und anderen Umgebungen leicht verwendet werden können.

---

## 📖 Die 5 Goldenen Regeln eines JavaBeans

| Regel | Beschreibung |
|-------|--------------|
| **1. Standardkonstruktor** | Ein **parameterloser** `public` Konstruktor |
| **2. Private Attribute** | Alle Attribute sind `private` |
| **3. Getter / Setter** | Öffentliche Getter- und Setter-Methoden nach dem **JavaBeans-Namensmuster** |
| **4. Serialisierbar** | Implementiert `Serializable` (optional, aber oft erwartet) |
| **5. Keine öffentlichen Felder** | Es gibt keine `public`-Felder – nur über Getter/Setter zugänglich |

---

## 🧱 Level 1: Die Basis – Ein einfaches JavaBean

```java
package com.example.beans;

import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private int age;

    // 1. Standardkonstruktor (parameterlos)
    public Person() {
    }

    // 2. Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

### 🧪 Verwendung

```java
Person p = new Person();
p.setName("Anna");
p.setAge(30);

System.out.println(p.getName()); // Anna
System.out.println(p.getAge());  // 30
```

---

## 🔬 Das JavaBeans-Namensmuster

| Feld | Getter | Setter |
|------|--------|--------|
| `String name` | `getName()` | `setName(String name)` |
| `int age` | `getAge()` | `setAge(int age)` |
| `boolean active` | **`isActive()`** | `setActive(boolean active)` |
| `List<String> items` | `getItems()` | `setItems(List<String> items)` |

**Wichtig bei `boolean`:** Statt `getActive()` heißt es **`isActive()`**!

---

## 📦 Level 2: Vollständiges JavaBean mit allen Konventionen

```java
package com.example.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Mitarbeiter implements Serializable {

    private Long id;
    private String vorname;
    private String nachname;
    private String email;
    private boolean aktiv;
    private List<String> abteilungen;

    // Standardkonstruktor
    public Mitarbeiter() {
    }

    // Optionaler Konstruktor mit allen Feldern (kein Standard, aber praktisch)
    public Mitarbeiter(Long id, String vorname, String nachname, String email, boolean aktiv, List<String> abteilungen) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.aktiv = aktiv;
        this.abteilungen = abteilungen;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // boolean → isAktiv() (nicht getAktiv())
    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public List<String> getAbteilungen() {
        return abteilungen;
    }

    public void setAbteilungen(List<String> abteilungen) {
        this.abteilungen = abteilungen;
    }

    // equals, hashCode, toString (empfohlen)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mitarbeiter that = (Mitarbeiter) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Mitarbeiter{" +
                "id=" + id +
                ", vorname='" + vorname + '\'' +
                ", nachname='" + nachname + '\'' +
                ", email='" + email + '\'' +
                ", aktiv=" + aktiv +
                ", abteilungen=" + abteilungen +
                '}';
    }
}
```

---

## 🧊 Level 3: JavaBean mit `@Serial` und `serialVersionUID`

Für die Serialisierung sollte ein JavaBean eine `serialVersionUID` enthalten – besonders wichtig bei verteilten Systemen oder Caching.

```java
import java.io.Serial;
import java.io.Serializable;

public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private double price;

    public Product() {
    }

    // Getter & Setter ...
}
```

---

## 🔥 Level 4: JavaBean mit Bean-Validierung (JSR 380)

JavaBeans werden oft mit **Annotations** validiert – z. B. in Spring oder Jakarta EE.

```java
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

public class User implements Serializable {

    @NotNull
    private Long id;

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @Min(18)
    private int age;

    public User() {
    }

    // Getter & Setter ...
}
```

---

## 📊 Level 5: JavaBean als DTO (Data Transfer Object)

In der Praxis werden JavaBeans oft als **DTOs** verwendet – z. B. für REST-APIs.

```java
@JsonIgnoreProperties(ignoreUnknown = true) // Jackson
public class CustomerDTO implements Serializable {

    private Long customerId;
    private String fullName;
    private String email;

    public CustomerDTO() {
    }

    // Getter & Setter ...
}
```

---

## 🧪 Code-Beispiel: Nutzung aller Konzepte

```java
public class Main {
    public static void main(String[] args) {
        // 1. Standard-Instanz
        Mitarbeiter m = new Mitarbeiter();
        m.setId(1L);
        m.setVorname("Max");
        m.setNachname("Mustermann");
        m.setEmail("max@example.com");
        m.setAktiv(true);
        m.setAbteilungen(List.of("IT", "HR"));

        // 2. Ausgabe
        System.out.println(m);

        // 3. Serialisierung (Beispiel)
        try {
            byte[] data = serialize(m);
            Mitarbeiter restored = deserialize(data);
            System.out.println("Restored: " + restored);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] serialize(Serializable obj) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            return bos.toByteArray();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T deserialize(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (T) ois.readObject();
        }
    }
}
```

---

## ✅ Zusammenfassung – JavaBeans im Überblick

| Feature | Beschreibung |
|---------|--------------|
| **Standardkonstruktor** | `public` und parameterlos |
| **Private Felder** | Keine öffentlichen Attribute |
| **Getter / Setter** | Nach JavaBeans-Namensmuster (`get`, `set`, `is`) |
| **Serializable** | Optional, aber oft erwartet |
| **toString / equals / hashCode** | Empfohlen für Logging und Collections |
| **Validierung** | Mit Annotations (z. B. `@NotBlank`) |
| **DTO** | Häufige Verwendung in Spring, JPA, JSON |

---

## 💡 Merksatz

> **Ein JavaBean ist eine Klasse mit:**
> - parameterlosem Konstruktor
> - privaten Feldern
> - Gettern & Settern
> - und optional `Serializable`