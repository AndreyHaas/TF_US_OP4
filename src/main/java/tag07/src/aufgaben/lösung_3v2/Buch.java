package main.java.tag07.src.aufgaben.lösung_3v2;

import java.io.Serializable;
import java.time.LocalDate;

public class Buch implements Serializable
{
	private int id;
	private String title;
	private String description;
	private LocalDate date;
	private double price;
	private String currency;

	public int getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}

	public String getDescription()
	{
		return description;
	}

	public LocalDate getDate()
	{
		return date;
	}

	public double getPrice()
	{
		return price;
	}

	public String getCurrency()
	{
		return currency;
	}

	public Buch(int id, String title, String description, LocalDate date, double price, String currency)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.date = date;
		this.price = price;
		this.currency = currency;
	}

	@Override
	public String toString()
	{
		return "Buch{" +
		"id=" + id +
		", title='" + title + '\'' +
		", description='" + description + '\'' +
		", date=" + date +
		", price=" + price +
		'}';
	}

}
