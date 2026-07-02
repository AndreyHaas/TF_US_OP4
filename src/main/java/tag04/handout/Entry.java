package main.java.tag04.handout;

import java.time.LocalDate;

public class Entry
{
	private String id;
	private String title;
	private String description;
	private LocalDate date;
	private int balance;
	private String currency;

    public String getId()
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

	public int getBalance()
	{
		return balance;
	}

    public String getCurrency()
    {
        return currency;
    }

	public Entry(String id, String title, String description, LocalDate date, int balance, String currency)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.date = date;
		this.balance = balance;
		this.currency = currency;
	}

	@Override
	public String toString()
	{
		return "Entry{" +
		"id='" + id + '\'' +
		", title='" + title + '\'' +
		", description='" + description + '\'' +
		", date=" + date +
		", balance=" + balance +
		", currency='" + currency + '\'' +
		'}';
	}
}
