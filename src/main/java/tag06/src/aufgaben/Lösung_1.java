/*
Schreiben Sie eine Klasse "Artikel", die folgende Eigenschaften hat: Nummer, Bezeichnung und Preis. Implementieren Sie diese Klasse als Java Bean, indem Sie private Felder für jede Eigenschaft erstellen, öffentliche Getter- und Setter-Methoden für den Zugriff auf diese Felder bereitstellen und Serializable implementieren.

Schreiben Sie dann ein kleines Programm, das eine Instanz der Artikel-Klasse erstellt und Werte für die Eigenschaften festlegt. Rufen Sie anschließend die Getter-Methoden auf, um sicherzustellen, dass die Werte korrekt gespeichert wurden.

Optional können Sie auch weitere Methoden zur Artikel-Klasse hinzufügen (z.B. toString() oder equals()), um Ihre Kenntnisse über Java Beans zu vertiefen.
 */

package main.java.tag06.src.aufgaben;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

class Artikel implements Serializable
{
    private int nummer;
    private String bezeichnung;
    private BigDecimal preis;

    public int getNummer()
    {
        return nummer;
    }

    public void setNummer(int nummer)
    {
        this.nummer = nummer;
    }

    public String getBezeichnung()
    {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung)
    {
        this.bezeichnung = bezeichnung;
    }

    public BigDecimal getPreis()
    {
        return preis;
    }

    public void setPreis(BigDecimal preis)
    {
        this.preis = preis;
    }

    public Artikel(/* Keine Parameter*/)
    {
        nummer = 0;
        bezeichnung = "Keine Bezeichnung";
        preis = BigDecimal.valueOf(0.0);
    }

    @Override
    public String toString()
    {
        return "Artikel{" +
            "nummer=" + nummer +
            ", bezeichnung='" + bezeichnung + '\'' +
            ", preis=" + preis +
            '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artikel artikel = (Artikel) o;
        return nummer == artikel.nummer && Objects.equals(bezeichnung, artikel.bezeichnung) && Objects.equals(preis, artikel.preis);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(nummer, bezeichnung, preis);
    }
}

public class Lösung_1
{
    public static void main(String[] args)
    {
        Artikel a = new Artikel();
        a.setNummer(1);
        a.setBezeichnung("Schokolade");
        a.setPreis(BigDecimal.valueOf(1.19));

        System.out.println(a.getNummer());
        System.out.println(a.getBezeichnung());
        System.out.println(a.getPreis());
        System.out.println(a);

        // equals testen:
        Artikel b = new Artikel();
        b.setNummer(1);
        b.setBezeichnung("Schokolade");
        b.setPreis(BigDecimal.valueOf(1.19));

        System.out.println(a.equals(b));
        System.out.printf("%x%n", a.hashCode());
        System.out.printf("%x%n", b.hashCode());
    }
}
