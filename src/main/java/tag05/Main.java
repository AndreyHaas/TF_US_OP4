package main.java.tag05;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

@FunctionalInterface
interface Rechner {
    int berechne(int ersteZahl, int zweiteZahl);
}

public class Main {
    private static final List<Artikel> artikelListe = new ArrayList<>();

    public static void main(String[] args) {
        artikelListe.add(new Artikel(1, "Teddybär", 19.99, "Steiff"));
        artikelListe.add(new Artikel(2, "Lego Star Wars", 89.99, "LEGO"));
        artikelListe.add(new Artikel(3, "Smartphone X1", 699.99, "TechCorp"));
        artikelListe.add(new Artikel(4, "Gaming Maus", 59.99, "GameGear"));
        artikelListe.add(new Artikel(5, "Mechanische Tastatur", 129.99, "GameGear"));
        artikelListe.add(new Artikel(6, "Laptop UltraBook", 1199.99, "TechCorp"));
        artikelListe.add(new Artikel(7, "4K OLED TV", 1299.99, "VisionElectro"));
        artikelListe.add(new Artikel(8, "Action Kamera Pro", 299.99, "PhotoMaster"));
        artikelListe.add(new Artikel(9, "Wireless Earbuds", 149.99, "TechCorp"));
        artikelListe.add(new Artikel(10, "Stativ Carbon", 129.99, "PhotoMaster"));

        // Eine lokale anonyme innere Klasse in der wir das Rechner Interface implementieren.
        Rechner addierer = new Rechner() {
            @Override
            public int berechne(int ersteZahl, int zweiteZahl) {
                return ersteZahl + zweiteZahl;
            }
        };

        // Viel kuerzer Code durch Lambda:
        // Eine Methodenreferenz ist eine Kurzschreibweise fuer Lambda.
        // Wenn wir mit Lambda Funktionalitaet referenzieren wollen, die
        // schon im Programm existiert (Wir verweisen mittels Methodenreferenz-Syntax an die vorhandene sum Methode, anstatt diese Logik selbst implementieren zu muessen:
        Rechner add = Integer::sum;
        Rechner sub = (a, b) -> a - b;
        Rechner mul = (a, b) -> a * b;
        Rechner div = (a, b) -> a / b;

        System.out.println(add.berechne(10, 7));
        System.out.println(sub.berechne(10, 7));
        System.out.println(mul.berechne(10, 7));
        System.out.println(div.berechne(10, 7));

        // RechnerImp ist vom Typ Rechner, da die Klasse das Interface Rechner implementier:
        Main.berechne(new RechnerImp(), 500, 700);

        // Wir koennen auch die Instanz einer anonymen Klasse die das Rechner Interface implementiert
        // uebergeben. Die anonyme Klasse ist vom Typ Rechner, da sie das RechnerInterface implementiert:
        Main.berechne(new Rechner() {
            @Override
            public int berechne(int ersteZahl, int zweiteZahl) {
                return ersteZahl + zweiteZahl;
            }
        }, 100, 200);

        // Oder wir uebergeben Referenzen auf Lambda Ausdruecke die das Rechnerinterface realisiert
        // haben.
        Main.berechne(add, 2, 3);
        Main.berechne(sub, 5, 2);
        Main.berechne(mul, 3, 3);

        // Wir koennen auch direkt Lambda uebergeben, die Rechner Interface realisieren:
        Main.berechne((a, b) -> a / b, 10, 2);

        // Vorteil der Stream<T> API
        // Warum ist die Stream<T> API so verbreitet?
        // Weil wir in nahezu jedem Programm Daten durchsuchen, filtern, sortieren, umwandeln oder zusammenfassen müssen.
        //Ohne Streams geht das mit Schleifen. Mit Streams ist derselbe Code oft kürzer, lesbarer und kombinierbar.
        // Ohne die Stream<T> API muessten wir viel boilerplate Code schreiben.
        // Dazu ein Beispiel: Wir suchen alle Artikel die teurer als 200 Euro sind:
        List<Artikel> teureArtikel = new ArrayList<>();

        for (Artikel artikel : artikelListe) {
            if (artikel.getPreis() > 200) {
                teureArtikel.add(artikel);
            }
        }

        for (Artikel artikel : teureArtikel) {
            System.out.println(artikel);
        }

        // Mit der Stream API ist der Code viel kuerzer und besser lesbar.
        System.out.println("--------------------Ausgabe gefilterter teurer Artikel mittel Stream API");
        artikelListe.stream().filter(a -> a.getPreis() > 200).forEach(System.out::println);

        // Wir muessen in Java normalerweise keine eigenen funktionalen Interfaces definieren. Java
        // hat fuer alle gaengigen Operationen eingebaute funktionale Interfaces. Uebersicht: https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
        // Predicate ist ein SAM Interface welches eine Bedingung ueberprueft und dann boolean zurueckgibt.
        // Das ist die abstrakte Methodensignatur:
        // boolean test(T t)
        Predicate<String> istLang = text -> text.length() > 5;
        System.out.println(istLang.test("hallo"));
        System.out.println(istLang.test("programmieren"));

        artikelListe.stream().filter(artikel -> artikel.getPreis() > 500).forEach(System.out::println);

        // 2. Function<T,R> -> https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html
        // Represents a function that accepts one argument and produces a result.
        // This is a functional interface whose functional method is apply(Object).
        ToIntFunction<String> laenge = String::length;
        System.out.println(laenge.applyAsInt("Hallo"));

        // Wir mappen Artikel zu Preis
        artikelListe.stream().map(Artikel::getPreis).forEach(System.out::println);
    }

    private static void berechne(Rechner rechner, int ersteZahl, int zweiteZahl) {
        System.out.println(rechner.berechne(ersteZahl, zweiteZahl));
    }
}

class RechnerImp implements Rechner {
    @Override
    public int berechne(int ersteZahl, int zweiteZahl) {
        return ersteZahl + zweiteZahl;
    }

}