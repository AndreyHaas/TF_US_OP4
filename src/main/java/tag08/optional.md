# NullPointerException mit Optional vermeiden

## 1. Was ist Optional?

`Optional` ist ein Container, der entweder einen Wert enthält oder leer (`empty`) ist. Es zwingt dich, den Fall "kein Wert" zu behandeln.

```java
import java.util.Optional;

// Statt:
User user = getUser();
if (user != null) {
    String name = user.getName();
}

// Mit Optional:
Optional<User> userOpt = getUserOptional();
userOpt.ifPresent(user -> {
    String name = user.getName();
});
```

---

## 2. Die wichtigsten Methoden von Optional

| Methode | Beschreibung |
|---------|--------------|
| `Optional.of(value)` | Erstellt Optional mit Wert (schlägt fehl bei null) |
| `Optional.ofNullable(value)` | Erstellt Optional (erlaubt null → dann empty) |
| `Optional.empty()` | Leeres Optional |
| `isPresent()` | Prüft, ob ein Wert vorhanden ist |
| `ifPresent(consumer)` | Führt Code aus, wenn Wert vorhanden |
| `orElse(default)` | Liefert Wert oder Default |
| `orElseGet(supplier)` | Liefert Wert oder berechneten Default |
| `orElseThrow()` | Liefert Wert oder wirft Exception |
| `map(function)` | Transformiert Wert, falls vorhanden |
| `flatMap(function)` | Wie map, aber für Optional-Rückgaben |
| `filter(predicate)` | Filtert, falls Wert vorhanden |

---

## 3. Grundlegende Beispiele

### Statt null-Checks

```java
// ❌ Ohne Optional (NPE-Gefahr)
public String getCity(User user) {
    if (user != null) {
        Address address = user.getAddress();
        if (address != null) {
            return address.getCity();
        }
    }
    return "Unbekannt";
}

// ✅ Mit Optional
public String getCity(User user) {
    return Optional.ofNullable(user)
            .map(User::getAddress)
            .map(Address::getCity)
            .orElse("Unbekannt");
}
```

### Werte sicher verwenden

```java
// ❌ Gefährlich
String name = user.getName(); // NPE wenn user == null

// ✅ Sicher
Optional<User> userOpt = Optional.ofNullable(user);
String name = userOpt.map(User::getName).orElse("Gast");
```

---

## 4. Aus einem Wert ein Optional machen

```java
// Wenn der Wert definitiv nicht null ist
Optional<String> opt1 = Optional.of("Hallo"); // OK

// Wenn der Wert null sein kann (empfohlen!)
Optional<String> opt2 = Optional.ofNullable(getString()); // Sicher

// Leeres Optional
Optional<String> empty = Optional.empty();
```

---

## 5. Methoden im Detail

### orElse() — Fallback-Wert

```java
String name = Optional.ofNullable(user)
        .map(User::getName)
        .orElse("Unbekannt"); // Immer "Unbekannt", wenn null
```

### orElseGet() — Fallback berechnen

```java
String name = Optional.ofNullable(user)
        .map(User::getName)
        .orElseGet(() -> getUserNameFromDatabase()); // Wird NUR bei null berechnet
```

**Unterschied:** `orElse()` wird IMMER ausgewertet, `orElseGet()` NUR bei null.

```java
// ❌ orElse — immer ausgeführt
String name = Optional.ofNullable(name).orElse(defaultName()); 

// ✅ orElseGet — nur bei null
String name = Optional.ofNullable(name).orElseGet(() -> defaultName());
```

### orElseThrow() — Exception werfen

```java
// Wenn Wert vorhanden sein MUSS
String name = Optional.ofNullable(user)
        .map(User::getName)
        .orElseThrow(() -> new IllegalStateException("User hat keinen Namen!"));
```

---

## 6. Ketten von Optionals

### map() — transformieren

```java
// Länge des Namens
Optional<Integer> length = Optional.ofNullable(user)
        .map(User::getName)
        .map(String::length);
```

### flatMap() — für Optional-Rückgaben

```java
// User → Optional<Address> → Optional<City>
Optional<String> city = Optional.ofNullable(user)
        .flatMap(User::getAddressOptional)
        .map(Address::getCity);
```

### filter() — nur wenn Bedingung passt

```java
// Nur Erwachsene (Alter >= 18)
Optional<User> adult = Optional.ofNullable(user)
        .filter(u -> u.getAge() >= 18);

adult.ifPresent(u -> System.out.println("Erwachsener: " + u.getName()));
```

---

## 7. Praktische Beispiele

### Beispiel 1: JSON-Daten sicher auslesen

```java
public class UserService {
    private Map<String, User> users = new HashMap<>();
    
    // ❌ Unsicher
    public User getUser(String id) {
        return users.get(id); // Kann null sein!
    }
    
    // ✅ Sicher
    public Optional<User> findUser(String id) {
        return Optional.ofNullable(users.get(id));
    }
}

// Verwendung
Optional<User> user = userService.findUser("123");
user.ifPresent(u -> System.out.println("Gefunden: " + u.getName()));
```

### Beispiel 2: Datenbankabfrage mit Optional

```java
public interface UserRepository {
    Optional<User> findById(Long id); // Methodensignatur sagt: "Kann null sein!"
}

// Verwendung
User user = userRepository.findById(1L)
        .orElseThrow(() -> new UserNotFoundException("User nicht gefunden"));
```

### Beispiel 3: Verschachtelte Objekte

```java
// ❌ Ohne Optional
public String getStreet(User user) {
    if (user != null) {
        Address address = user.getAddress();
        if (address != null) {
            Street street = address.getStreet();
            if (street != null) {
                return street.getName();
            }
        }
    }
    return "Keine Straße";
}

// ✅ Mit Optional
public String getStreet(User user) {
    return Optional.ofNullable(user)
            .map(User::getAddress)
            .map(Address::getStreet)
            .map(Street::getName)
            .orElse("Keine Straße");
}
```

### Beispiel 4: Collection sicher durchsuchen

```java
// ❌ NPE-Gefahr
List<User> users = getUsers();
User first = users.get(0); // NPE wenn users == null

// ✅ Sicher
List<User> users = getUsers();
User first = Optional.ofNullable(users)
        .filter(list -> !list.isEmpty())
        .map(list -> list.get(0))
        .orElse(null); // null vermeiden! → lieber orElseThrow()
```

---

## 8. Optional als Rückgabetyp in eurer App

### Methode, die Optional zurückgibt

```java
public Optional<User> findByName(String name) {
    // Gefunden → Optional.of(user)
    // Nicht gefunden → Optional.empty()
    return Optional.ofNullable(userMap.get(name));
}

// Verwendung
findByName("Max")
    .ifPresentOrElse(
        user -> System.out.println("User: " + user),
        () -> System.out.println("Nicht gefunden!")
    );
```

---

## 9. Häufige Fehler

### ❌ Optional.of() mit null verwenden

```java
Optional.of(null); // 💥 NullPointerException!
```

**Lösung:** Immer `Optional.ofNullable()` verwenden, wenn null möglich ist.

### ❌ Optional.get() ohne Prüfung

```java
Optional<String> opt = Optional.empty();
String value = opt.get(); // 💥 NoSuchElementException!
```

**Lösung:** `orElse()`, `orElseGet()`, `orElseThrow()` oder `ifPresent()` verwenden.

### ❌ Optional als Feld oder Parameter

```java
public class User {
    // ❌ Nicht machen!
    private Optional<String> name; 
    
    // ❌ Nicht machen!
    public void setName(Optional<String> name) { ... }
}
```

**Besser:** Felder und Parameter sind normal, **Rückgabetypen** sind Optional!

---

## 10. Best Practices

| Regel | Grund |
|-------|-------|
| ✅ Verwende `Optional.ofNullable()` | Sicher bei möglichem null |
| ✅ Verwende `orElseGet()` statt `orElse()` | Performance bei teuren Berechnungen |
| ✅ Optional **nur als Rückgabetyp** | Nicht für Felder/Parameter |
| ❌ Kein `Optional.get()` ohne `isPresent()` | Kann Exception werfen |
| ❌ Kein `Optional.of(null)` | Wirft NPE |
| ❌ Kein `Optional` in Collections | `List<Optional<T>>` ist unschön |

---

## 11. Zusammenfassung

```java
// 1. Erstellen
Optional.ofNullable(user)           // Kann null sein
Optional.of(user)                   // Muss nicht-null sein
Optional.empty()                    // Leer

// 2. Prüfen und ausführen
opt.isPresent()                     // Ist Wert da?
opt.ifPresent(u -> ...)            // Wenn da, mach was
opt.ifPresentOrElse(u -> ..., () -> ...) // Wenn da, sonst...

// 3. Werte holen
opt.orElse(default)                 // Fallback-Wert
opt.orElseGet(() -> ...)           // Fallback berechnen
opt.orElseThrow(() -> ...)         // Exception werfen

// 4. Transformieren
opt.map(User::getName)              // Name extrahieren
opt.flatMap(user -> user.getAddressOpt()) // Optional zurück

// 5. Filtern
opt.filter(u -> u.getAge() >= 18)   // Nur Erwachsene
```

---

**Merke:** `Optional` macht deinen Code **sicherer** und **lesbarer**, weil es den Programmierer **zwingt**, den "kein Wert"-Fall zu behandeln.