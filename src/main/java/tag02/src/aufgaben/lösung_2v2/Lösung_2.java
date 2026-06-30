package tag02.src.aufgaben.lösung_2v2;

/*
Aufgabenstellung: (Mit Hilfe von ChatGPT erstellt)

Implementieren Sie für jeden Schritt entsprechende Methoden unter Verwendung des Stream-Interfaces.

Lösungshinweise:

Für diese Aufgaben sollten Ihnen folgende Stream-Operationen bekannt sein:
- 'filter()'
- 'sorted()'
- 'map()'
- 'collect()'
- 'Collectors.groupingBy()'
- 'average()'
- '{min|max}(Comparator.comparing())'

Hier ein Beispiel für Punkt 2:
List<Book> books = // Initialisiere deine Buchliste
List<Book> booksBefore2000 = books.stream()
      .filter(book -> book.getYearOfPublication() < 2000)
      .collect(Collectors.toList());

 */

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Lösung_2
{
	public static void main(String[] args)
	{
		List<Book> bookList = getBookList();
		bookList.forEach(System.out::println);
		System.out.println();

		System.out.println("Bücher vor 2000");
		List<Book> booksBefore2000 = BookService.getBooksBeforeYear(bookList, 2000);
		booksBefore2000.forEach(System.out::println);
		System.out.println();

		System.out.println("Bücher nach Titel sortiert");
		List<Book> booksSorted = BookService.getBooksSortedByTitle(booksBefore2000);
		booksSorted.forEach(System.out::println);
		System.out.println();

		System.out.println("Nur die Titel");
		List<String> bookTitles = BookService.getTitles(booksSorted);
		bookTitles.forEach(System.out::println);
		System.out.println();

		System.out.println("Durchschnittspreis");
		double averagePrice = BookService.getAveragePrice(booksSorted);
		String priceFormatted = NumberFormat.getCurrencyInstance().format(averagePrice);
		//DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
		//format.applyPattern("#.00");
		//String priceFormatted = format.format(averagePrice);
		System.out.println(priceFormatted);
		System.out.printf(Locale.ENGLISH, "%.2f\n", averagePrice);
		System.out.println();

		System.out.println("Teuerstes Buch");
		Book book = BookService.getMostExpensiveBook(bookList);
		System.out.println(book);
		System.out.println(BookService.getMaxValue(bookList));
		System.out.println();

		System.out.println("Gruppiert nach Author");
		Map<String, List<Book>> authorMap = BookService.getAuthors(bookList);
		for (Map.Entry<String, List<Book>> keyValue : authorMap.entrySet())
		{
			System.out.println(keyValue);
		}
	}

	//1. Erstellen Sie eine Liste von Büchern.
	private static List<Book> getBookList()
	{
		List<Book> bookList = new ArrayList<>();

		//Beispieldaten (von ChatGPT generiert):
		bookList.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 24.99));
		bookList.add(new Book("1984", "George Orwell", 1949, 19.84));
		bookList.add(new Book("To Kill a Mockingbird", "Harper Lee", 1960, 18.60));
		bookList.add(new Book("The Catcher in the Rye", "J.D. Salinger", 1951, 15.98));
		bookList.add(new Book("Brave New World", "Aldous Huxley", 1932, 12.80));
		bookList.add(new Book("Der Hobbit", "J.R.R.Tolkien", 1937, 10.75));
		bookList.add(new Book("Der Herr der Ringe", "J.R.R.Tolkien", 1954, 22.90));
		bookList.add(new Book("Stolz und Vorurteil" ,"Jane Austen", 1813, 12.50));
		bookList.add(new Book("Moby Dick", "Herman Melville", 1851, 17.40));
		bookList.add(new Book("Astrophysics for People in a Hurry", "Neil deGrasse Tyson", 2017, 19.50));

		return bookList;
	}
}
