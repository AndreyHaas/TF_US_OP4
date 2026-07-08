package main.java.tag08.aufgaben.ha1.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class Auto implements Serializable {

    @Serial
    private static final long serialVersionUID = 5359555621613172140L;

    private String farbe;
    private int geschwindigkeit;
    private String marke;

    public Auto() {
    }

    public Auto(String farbe, int geschwindigkeit, String marke) {
        this.farbe = farbe;
        this.geschwindigkeit = geschwindigkeit;
        this.marke = marke;
    }

    public String getFarbe() {
        return farbe;
    }

    public int getGeschwindigkeit() {
        return geschwindigkeit;
    }

    public String getMarke() {
        return marke;
    }

    public void setFarbe(String farbe) {
        this.farbe = farbe;
    }

    public void setGeschwindigkeit(int geschwindigkeit) {
        this.geschwindigkeit = geschwindigkeit;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Auto auto = (Auto) o;
        return geschwindigkeit == auto.geschwindigkeit && Objects.equals(farbe, auto.farbe) && Objects.equals(marke, auto.marke);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(farbe);
        result = 31 * result + geschwindigkeit;
        result = 31 * result + Objects.hashCode(marke);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Auto.class.getSimpleName() + "[", "]")
                .add("farbe='" + farbe + "'")
                .add("geschwindigkeit=" + geschwindigkeit)
                .add("marke='" + marke + "'")
                .toString();
    }
}