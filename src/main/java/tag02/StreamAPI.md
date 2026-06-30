# Java Stream API – Erklärung mit der Fluss-und-Fischer-Analogie

## 1. Was ist die Stream API?

Die **Stream API** (seit Java 8) ist ein mächtiges Werkzeug, um Datenmengen (wie Listen, Arrays oder Dateien) **deklarativ** zu verarbeiten. 

Das Besondere: Ein Stream ist **keine Datenstruktur**, sondern ein **Konzept** – ein "Fließband", das die Daten aus einer Quelle (z.B. einer Liste) nimmt, sie durch verschiedene Verarbeitungsschritte schickt und am Ende ein Ergebnis liefert.

---

## 2. Die Analogie: Fluss, Fischer und Ausrüstung

Stell dir vor:

- Die **Daten** (z.B. eine Liste von Namen) sind ein **Fluss voller Fische**.
- Der **Stream** selbst ist das **Boot**, das du auf den Fluss setzt.
- Die **Zwischenoperationen** (`.filter()`, `.map()` etc.) sind deine **Angelausrüstung** (Netze, Messer, Sortierkästen).
- Die **Terminaloperation** (`.collect()`, `.forEach()` etc.) ist der Moment, in dem du den **Korb ins Wasser wirfst** und die gefangenen, vorbereiteten Fische herausholst.

**Der entscheidende Punkt:** Der Fluss (die ursprüngliche Datenquelle) wird **nie verändert**. Du arbeitest nur mit einer Kopie / einem Fließband.

---

## 3. Die drei Bausteine eines Streams

### 3.1. Quelle – Der Fluss (Datenquelle)

```java
List<String> fische = Arrays.asList("Hecht", "Barsch", "Aal", "Karpfen", "Hecht");
```

### 3.2. Zwischenoperationen – Die Angelausrüstung

| Operation | Funktion | Analogie |
| :--- | :--- | :--- |
| `.filter()` | Behält nur Elemente, die eine Bedingung erfüllen. | Ein Netz mit einer bestimmten Maschenweite. |
| `.map()` | Wandelt jedes Element um (z.B. in Großbuchstaben). | Ein Filetiermesser, das den Fisch vorbereitet. |
| `.sorted()` | Sortiert die Elemente. | Ein Sortierkasten. |

**Wichtig:** Diese Operationen sind "lazy" (faul). Solange du nicht "erntest", passiert noch gar nichts!

```java
fische.stream()                         // Boot zu Wasser lassen
      .filter(f -> f.startsWith("H"))   // Nur Fische mit "H" behalten
      .map(String::toUpperCase)         // Namen in Großbuchstaben umwandeln
      .sorted();                        // Alphabetisch sortieren
```

### 3.3. Terminaloperation – Der Korb (Ergebnis)

Die **Terminaloperation** startet die Verarbeitung. Sie holt die gefischten, verarbeiteten Elemente aus dem Stream:

```java
List<String> ergebnis = fische.stream()
        .filter(f -> f.startsWith("H"))
        .map(String::toUpperCase)
        .sorted()
        .collect(Collectors.toList());   // Alles in einen neuen Korb (Liste) sammeln

System.out.println(ergebnis); // Ausgabe: [HECHT, HECHT]
```

---

## 4. Vollständiges Beispiel

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamBeispiel {
    public static void main(String[] args) {
        List<Integer> zahlen = Arrays.asList(1, 2, 3, 4, 5, 6);

        // Schritt 1: Stream öffnen
        // Schritt 2: Nur gerade Zahlen filtern
        // Schritt 3: Jede Zahl verdoppeln
        // Schritt 4: In einer neuen Liste sammeln
        List<Integer> ergebnis = zahlen.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * 2)
                .collect(Collectors.toList());

        System.out.println(ergebnis); // [4, 8, 12]
        System.out.println("Original: " + zahlen); // Original bleibt unverändert!
    }
}
```

---

## 5. Vorteile gegenüber der "alten" Schleife

| Kriterium | Klassische Schleife | Stream API |
| :--- | :--- | :--- |
| **Lesbarkeit** | Oft unübersichtlich und verschachtelt. | **Sehr lesbar** – du sagst, was passieren soll. |
| **Änderbarkeit** | Man muss viel Code umschreiben. | Einfach eine neue Operation hinzufügen (`.filter`, `.map`). |
| **Nebenwirkungen** | Man verändert oft versehentlich die Ursprungsliste. | Die Quelle bleibt **immer unverändert**. |

---

## 6. Zusammenfassung

| Begriff | Erklärung |
| :--- | :--- |
| **Quelle** | Die Datenquelle (Liste, Array, Datei). |
| **Stream** | Ein Fließband, das die Daten durchleitet. |
| **Zwischenop.** | Vorbereitende Schritte wie Filtern, Umwandeln, Sortieren. |
| **Terminalop.** | Startet die Verarbeitung und liefert das Ergebnis. |

**Merksatz:** *"Ein Stream ist wie ein Boot auf dem Fluss der Daten. Die Quelle bleibt unberührt – das Boot nimmt nur die Daten mit, die du haben willst."*