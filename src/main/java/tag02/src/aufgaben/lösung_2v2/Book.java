package tag02.src.aufgaben.lösung_2v2;

//Sie haben die Aufgabe, einen Bericht über Bücher aus einer Bibliothek zu erstellen. Jedes Buch wird durch ein 'Book' Objekt repräsentiert, welches folgende Eigenschaften hat:


public class Book {
    private String title;
    private String author;
    private int yearOfPublication;
    private double price;

	public String getTitle()
	{
		return title;
	}

	public String getAuthor()
	{
		return author;
	}

	public int getYearOfPublication()
	{
		return yearOfPublication;
	}

	public double getPrice()
	{
		return price;
	}

	public Book(String title, String author, int yearOfPublication, double price)
	{
		this.title = title;
		this.author = author;
		this.yearOfPublication = yearOfPublication;
		this.price = price;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("Book{");
		sb.append("title='").append(title).append('\'');
		sb.append(", author='").append(author).append('\'');
		sb.append(", yearOfPublication=").append(yearOfPublication);
		sb.append(", price=").append(price);
		sb.append('}');
		return sb.toString();
	}
}
