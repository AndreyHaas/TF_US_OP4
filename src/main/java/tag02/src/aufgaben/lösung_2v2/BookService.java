package main.java.tag02.src.aufgaben.lösung_2v2;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import tag02.src.aufgaben.lösung_2v2.Book;

public class BookService
{
	//Ihre Aufgabe besteht darin, verschiedene Berichte unter Verwendung des Stream-Interfaces zu generieren.

	//2. Filtern Sie alle Bücher heraus, die vor dem Jahr 2000 veröffentlicht wurden.
	public static List<Book> getBooksBeforeYear(List<Book> bookList, int year)
	{
		return bookList.stream()
			.filter(book -> book.getYearOfPublication() < year)
			.collect(Collectors.toList());
	}

	//3. Sortieren Sie die gefilterten Bücher nach ihrem Titel in aufsteigender Reihenfolge.
	public static List<Book> getBooksSortedByTitle(List<Book> bookList)
	{
		return bookList.stream()
			.sorted((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()))
			.collect(Collectors.toList());
	}

	//4. Erstellen Sie eine neue Liste mit den Titeln dieser Bücher (als Strings).
	public static List<String> getTitles(List<Book> bookList)
	{
		return bookList.stream()
			.map(Book::getTitle)
			.collect(Collectors.toList());
	}

	//5. Berechnen Sie den Durchschnittspreis der gefilterten Liste von Büchern.
	public static double getAveragePrice(List<Book> bookList)
	{
		return bookList.stream()
			.mapToDouble(Book::getPrice)
			.average()
			.orElse(-1);
	}

	//6. Finden Sie das teuerste Buch in der gesamten Kollektion.
	public static Book getMostExpensiveBook(List<Book> bookList)
    {
         Book expenBook = bookList.stream()
            .max(Comparator.comparingDouble(Book::getPrice))
            .orElse(null);
         return expenBook;
    }

	public static double getMaxValue(List<Book> bookList)
    {
        return  bookList.stream()
                .mapToDouble(Book::getPrice)
                .max().orElse(-1);
    }

	//7. Erzeugen Sie eine Map, wobei jeder Autor mit einer Liste seiner Bücher assoziiert ist.
	public static Map<String, List<Book>> getAuthors(List<Book> bookList)
	{
		return bookList.stream().collect(Collectors.groupingBy(Book::getAuthor));
	}


}
