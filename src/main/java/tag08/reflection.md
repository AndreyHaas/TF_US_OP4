# Java Reflection (Reflexion) — für Anfänger

## 1. Was ist Reflection?

**Reflection** ist ein Mechanismus in Java, der es einem Programm erlaubt, **sich selbst zu untersuchen**. 

Mit Reflection kann man:

- Den Namen einer Klasse, ihre Methoden und Felder **während der Laufzeit** herausfinden
- Objekte erstellen, auch wenn ihr Typ beim Schreiben des Codes nicht bekannt ist
- Methoden aufrufen und Felder ändern, **auch wenn sie `private` sind**
- Annotations (Metadaten) von Klassen, Methoden und Feldern auslesen

Einfach ausgedrückt: **Reflection ist eine Möglichkeit, Code zu schreiben, der andere Objekte "untersuchen" und "verändern" kann, ohne deren genauen Typ zur Compile-Zeit zu kennen.**

---

## 2. Wozu braucht man Reflection?

Reflection wird für **flexible und universelle** Programme benötigt:

| Anwendung | Beispiel |
|-----------|----------|
| **Serialisierung** (z.B. JSON) | Jackson, Gson finden automatisch alle Felder eines Objekts und wandeln sie in JSON um |
| **Testframeworks** | JUnit findet alle Methoden, die mit `@Test` annotiert sind, und führt sie aus |
| **Frameworks** | Spring erstellt Objekte und verbindet sie miteinander, ohne dass der Programmierer `new` schreiben muss |
| **Annotationsverarbeitung** | Die IDE liest eure Annotationen und gibt Hinweise |
| **Debugging** | Man kann in private Felder schauen, um zu verstehen, warum etwas kaputt ist |

Ohne Reflection müsste jede Bibliothek bereits beim Kompilieren alle eure Klassen kennen. Mit Reflection kann sie einfach "reingehen" und mit **jedem** Objekt arbeiten.

---

## 3. Die Basis von Reflection — die Klasse `Class`

Jedes Objekt in Java hat die Methode `getClass()`, die ein Objekt vom Typ `Class` zurückgibt.

```java
User user = new User("Alice", 25);
Class<?> clazz = user.getClass();   // Holt das Class-Objekt von User
```

Oder direkt:

```java
Class<User> clazz = User.class;     // Zweite Möglichkeit
```

Aus dem `Class`-Objekt kann man alles bekommen:

- `clazz.getName()` — vollständiger Klassenname (mit Package)
- `clazz.getSimpleName()` — nur der Klassenname (ohne Package)
- `clazz.getDeclaredMethods()` — alle Methoden (auch private)
- `clazz.getDeclaredFields()` — alle Felder (auch private)
- `clazz.getDeclaredConstructors()` — alle Konstruktoren
- `clazz.getAnnotations()` — alle Annotationen der Klasse

---

## 4. Arbeiten mit Methoden

### Alle Methoden einer Klasse ausgeben

```java
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionMethods {
    public static void main(String[] args) {
        Class<?> clazz = User.class;

        // getDeclaredMethods() — ALLE Methoden (inklusive private)
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            String modifier = Modifier.toString(method.getModifiers());
            String returnType = method.getReturnType().getSimpleName();
            String name = method.getName();
            Class<?>[] params = method.getParameterTypes();

            System.out.println(modifier + " " + returnType + " " + name + "(" + params.length + " Parameter)");
        }
    }
}
```

### Unterschied zwischen `getMethods()` und `getDeclaredMethods()`

| Methode | Liefert |
|---------|---------|
| `getMethods()` | Nur **öffentliche** Methoden (auch geerbte von Object) |
| `getDeclaredMethods()` | **ALLE** Methoden der Klasse (private, protected, package-private), aber **nicht geerbte** |

---

## 5. Arbeiten mit Feldern

### Alle Felder einer Klasse ausgeben

```java
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionFields {
    public static void main(String[] args) {
        Class<?> clazz = User.class;

        // getDeclaredFields() — ALLE Felder (inklusive private)
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String modifier = Modifier.toString(field.getModifiers());
            String type = field.getType().getSimpleName();
            String name = field.getName();

            System.out.println(modifier + " " + type + " " + name);
        }
    }
}
```

---

## 6. Private Methoden aufrufen

```java
import java.lang.reflect.Method;

public class CallPrivateMethod {
    public static void main(String[] args) throws Exception {
        User user = new User("Alice", 25);

        // Private Methode holen
        Method method = User.class.getDeclaredMethod("geheimnis", String.class);

        // Zugriff erlauben (wichtig!)
        method.setAccessible(true);

        // Methode aufrufen
        String result = (String) method.invoke(user, "Hallo");

        System.out.println(result);
    }
}
```

**Wichtig:** `setAccessible(true)` macht private, protected und package-private zugänglich!

---

## 7. Private Felder ändern

```java
import java.lang.reflect.Field;

public class EditPrivateField {
    public static void main(String[] args) throws Exception {
        User user = new User("Alice", 25);

        // Feld holen
        Field field = User.class.getDeclaredField("name");

        // Zugriff erlauben
        field.setAccessible(true);

        // Alten Wert auslesen
        String oldName = (String) field.get(user);
        System.out.println("Alter Name: " + oldName);

        // Neuen Wert setzen
        field.set(user, "Bob");

        // Prüfen
        System.out.println("Neuer Name: " + user.getName());
    }
}
```

### Felder verschiedener Typen setzen

```java
// Für int
field.setInt(obj, 42);

// Für boolean
field.setBoolean(obj, true);

// Für double
field.setDouble(obj, 3.14);

// Für alle anderen Typen
field.set(obj, wert);
```

---

## 8. Objekte mit Reflection erstellen

```java
import java.lang.reflect.Constructor;

public class CreateObject {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = User.class;

        // Standard-Konstruktor (ohne Parameter)
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        User user1 = (User) constructor.newInstance();

        // Konstruktor mit Parametern
        Constructor<?> constructorWithParams = clazz.getDeclaredConstructor(String.class, int.class);
        User user2 = (User) constructorWithParams.newInstance("Alice", 25);

        System.out.println(user1);
        System.out.println(user2);
    }
}
```

---

## 9. Nützliche Hilfsmethode — alle Methoden ausgeben (inklusive geerbter)

```java
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionHelper {

    // Alle Methoden inklusive geerbter (aber nicht Object)
    public static List<Method> getAllMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>();

        while (clazz != null && clazz != Object.class) {
            for (Method method : clazz.getDeclaredMethods()) {
                methods.add(method);
            }
            clazz = clazz.getSuperclass(); // zur Elternklasse gehen
        }

        return methods;
    }

    public static void main(String[] args) {
        List<Method> allMethods = getAllMethods(User.class);

        for (Method method : allMethods) {
            System.out.println(method);
        }
    }
}
```

---

## 10. Wichtige Hinweise

| Punkt | Erklärung |
|-------|-----------|
| `setAccessible(true)` | **Macht private zugänglich.** Ohne das geht nichts! |
| Performance | Reflection ist **langsamer** als normaler Aufruf. Nicht in Schleifen verwenden! |
| Security | In manchen Umgebungen (z.B. Android, SecurityManager) kann Reflection eingeschränkt sein. |
| Kapselung | Reflection **bricht die Kapselung**. Nur verwenden, wenn es wirklich nötig ist (z.B. für Frameworks, Tests). |

---

## 11. Wann NIEMALS Reflection verwenden

- **Im produktiven Code** für einfache Dinge (einfach Getter/Setter verwenden)
- **In engen Schleifen** (Performance leidet)
- **Wenn es anders geht** (Reflection ist schwerer zu warten und zu debuggen)

---

## 12. Reflection in der Praxis

| Bibliothek / Tool | Verwendet Reflection für... |
|-------------------|------------------------------|
| **Jackson** | Serialisierung von Java-Objekten nach JSON |
| **JUnit** | Finden und Ausführen von Test-Methoden |
| **Spring** | Dependency Injection, Erstellen von Beans |
| **Hibernate** | Mapping von Java-Objekten auf Datenbanktabellen |
| **Mockito** | Erstellen von Mocks für Tests |

---

## Zusammenfassung

✅ **Reflection = Programm kann sich selbst untersuchen**  
✅ Ermöglicht **flexible Frameworks** und **Bibliotheken**  
✅ Kann **private** Felder und Methoden ändern/aufrufen  
✅ **Langsam** — daher sparsam verwenden  
✅ **Mächtig** — aber mit Vorsicht genießen