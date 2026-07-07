package main.java.tag07.src.aufgaben.lösung_3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Buch implements Serializable
{
    public static final List<Buch> buchListe = new ArrayList<>();

    private String id, title, description, currency;
    private int price;
    private String date;

    public Buch(String id, String title, String description, String date, int price, String currency)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.price = price;
        this.currency = currency;

        buchListe.add(this);
    }

    public Buch()
    {

    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
}
