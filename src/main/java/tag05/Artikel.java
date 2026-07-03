package main.java.tag05;

public class Artikel {
    private final int artikelNummer;
    private final String bezeichnung;
    private final double preis;
    private final String hersteller;

    public Artikel(int artikelNummer, String bezeichnung, double preis, String hersteller) {
        this.artikelNummer = artikelNummer;
        this.bezeichnung = bezeichnung;
        this.preis = preis;
        this.hersteller = hersteller;
    }

    public int getArtikelNummer() {
        return artikelNummer;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public double getPreis() {
        return preis;
    }

    public String getHersteller() {
        return hersteller;
    }

    @Override
    public String toString() {
        return "Artikel{" + "artikelNummer=" + artikelNummer + ", bezeichnung='" + bezeichnung + '\'' + ", hersteller='" + hersteller + '\'' + ", preis=" + preis + '}';
    }
}
