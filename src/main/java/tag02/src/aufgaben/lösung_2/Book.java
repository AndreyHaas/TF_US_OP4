package tag02.src.aufgaben.lösung_2;

import java.util.*;
import java.util.stream.Collectors;

public class Book
{
    private static final List<Book> books = new ArrayList<>();

    private final String title;
    private final String author;
    private final int yearOfPublication;
    private double price;

    public Book(String title, String author, int yearOfPublication, double price)
    {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.price = price;

        books.add(this);
    }

    // 1. Erstellen Sie eine Liste von Büchern.
    public static List<Book> getBooks()
    {
        return new ArrayList<>(books);
    }

    // 2. Filtern Sie alle Bücher heraus, die vor dem Jahr 2000 veröffentlicht wurden.
    public static List<Book> getBooksBeforeYear(List<Book> bookList, int year)
    {
        return bookList.stream().filter(b -> b.yearOfPublication < year).toList(); // Ab Java 16 mit toList(), sonst mit Collectors.toList()
    }

    // 3. Sortieren Sie die gefilterten Bücher nach ihrem Titel in aufsteigender Reihenfolge.
    public static List<Book> getBooksSortedByTitle(List<Book> bookList)
    {
        return bookList.stream().sorted(Comparator.comparing(b -> b.title)).toList();
    }

    // 4. Erstellen Sie eine neue Liste mit den Titeln dieser Bücher (als Strings).
    public static List<String> getBookTitles(List<Book> bookList)
    {
        return bookList.stream().map(b -> b.title).toList();
    }

    // 5. Berechnen Sie den Durchschnittspreis der gefilterten Liste von Büchern.
    public static double getAveragePrice(List<Book> bookList)
    {
        return bookList.stream().collect(Collectors.averagingDouble(b -> b.price));
    }

    // 6. Finden Sie das teuerste Buch in der gesamten Kollektion.
    public static Book getMostExpensiveBook(List<Book> bookList)
    {
        Optional<Book> optional = bookList.stream().max(Comparator.comparingDouble(o -> o.price));
        return optional.orElse(null);
    }

    // 7. Erzeugen Sie eine Map, wobei jeder Autor mit einer Liste seiner Bücher assoziiert ist.
    public static Map<String, List<Book>> getAuthorMap(List<Book> bookList)
    {
        return bookList.stream().collect(Collectors.groupingBy(b -> b.author));
    }

    @Override
    public String toString()
    {
        return "Book{" +
            "title='" + title + '\'' +
            ", author='" + author + '\'' +
            ", yearOfPublication=" + yearOfPublication +
            ", price=" + price +
            '}';
    }


}
