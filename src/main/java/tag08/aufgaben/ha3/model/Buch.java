package main.java.tag08.aufgaben.ha3.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@XmlRootElement(name = "Buch")
public class Buch implements Serializable, Comparable<Buch> {

    @Serial
    private static final long serialVersionUID = -5351736580641487909L;

    private int id;
    private String titel;
    private String autor;
    private int jahr;
    private String verlag;

    public Buch() {
    }

    public Buch(int id, String titel, String autor, int jahr, String verlag) {
        this.id = id;
        this.titel = titel;
        this.autor = autor;
        this.jahr = jahr;
        this.verlag = verlag;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    @XmlElement
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @XmlElement
    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    @XmlElement
    public String getVerlag() {
        return verlag;
    }

    public void setVerlag(String verlag) {
        this.verlag = verlag;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Buch buch = (Buch) o;
        return id == buch.id && jahr == buch.jahr && Objects.equals(titel, buch.titel) && Objects.equals(autor, buch.autor) && Objects.equals(verlag, buch.verlag);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + Objects.hashCode(titel);
        result = 31 * result + Objects.hashCode(autor);
        result = 31 * result + jahr;
        result = 31 * result + Objects.hashCode(verlag);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Buch.class.getSimpleName() + "[", "]").add("id=" + id).add("titel='" + titel + "'").add("autor='" + autor + "'").add("jahr=" + jahr).add("verlag='" + verlag + "'").toString();
    }

    @Override
    public int compareTo(Buch other) {
        return Integer.compare(this.id, other.id);
    }
}