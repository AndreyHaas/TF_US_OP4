package tag02.ha.a2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import tag02.ha.a2.model.Book;

public class BookReport {

  public static void main() {
    List<Book> books = createBookList();

    System.out.println("=== Alle Bücher ===");
    books.forEach(System.out::println);
    System.out.println();

    // 2. Filtern: Bücher vor 2000
    List<Book> booksBefore2000 = filterBooksBefore2000(books);
    System.out.println("Bücher vor dem Jahr 2000:");
    booksBefore2000.forEach(System.out::println);
    System.out.println();

    // 3. Sortieren: gefilterte Bücher nach Titel
    List<Book> sortedBooks = sortBooksByTitle(booksBefore2000);
    System.out.println("Gefilterte Bücher sortiert nach Titel:");
    sortedBooks.forEach(System.out::println);
    System.out.println();

    // 4. Neue Liste mit Titeln (als Strings)
    List<String> bookTitles = getBookTitles(sortedBooks);
    System.out.println("Titel der Bücher:");
    bookTitles.forEach(title -> System.out.println("  - " + title));
    System.out.println();

    // 5. Durchschnittspreis der gefilterten Bücher
    OptionalDouble avgPrice = calculateAveragePrice(booksBefore2000);
    System.out.println(
        "Durchschnittspreis der Bücher vor 2000: " + (avgPrice.isPresent() ? String.format("%.2f €",
            avgPrice.getAsDouble()) : "Keine Bücher"));
    System.out.println();

    // 6. Teuerstes Buch in der gesamten Kollektion
    Optional<Book> mostExpensiveBook = findMostExpensiveBook(books);
    System.out.println("Teuerstes Buch in der Kollektion:");
    mostExpensiveBook.ifPresent(System.out::println);
    System.out.println();

    // 7. Map: Autor → Liste seiner Bücher
    Map<String, List<Book>> booksByAuthor = groupBooksByAuthor(books);
    System.out.println("Bücher gruppiert nach Autor:");
    booksByAuthor.forEach((author, bookList) -> {
      System.out.println("  " + author + ":");
      bookList.forEach(book -> System.out.println(
          "    - " + book.getTitle() + " (" + book.getYearOfPublication() + ")"));
    });
  }

  private static List<Book> createBookList() {
    return Arrays.asList(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 24.99),
        new Book("1984", "George Orwell", 1949, 19.84),
        new Book("To Kill a Mockingbird", "Harper Lee", 1960, 18.60),
        new Book("The Catcher in the Rye", "J.D. Salinger", 1951, 15.98),
        new Book("Brave New World", "Aldous Huxley", 1932, 12.80),
        new Book("Der Hobbit", "J.R.R.Tolkien", 1937, 10.75),
        new Book("Der Herr der Ringe", "J.R.R.Tolkien", 1954, 22.90),
        new Book("Stolz und Vorurteil", "Jane Austen", 1813, 12.50),
        new Book("Moby Dick", "Herman Melville", 1851, 17.40),
        new Book("Astrophysics for People in a Hurry", "Neil deGrasse Tyson", 2017, 19.50));
  }

  private static List<Book> filterBooksBefore2000(List<Book> books) {
    return books.stream().filter(book -> book.getYearOfPublication() < 2000)
        .collect(Collectors.toList());
  }

  private static List<Book> sortBooksByTitle(List<Book> books) {
    return books.stream().sorted(Comparator.comparing(Book::getTitle)).collect(Collectors.toList());
  }

  private static List<String> getBookTitles(List<Book> books) {
    return books.stream().map(Book::getTitle).collect(Collectors.toList());
  }

  private static OptionalDouble calculateAveragePrice(List<Book> books) {
    return books.stream().mapToDouble(Book::getPrice).average();
  }

  private static Optional<Book> findMostExpensiveBook(List<Book> books) {
    return books.stream().max(Comparator.comparingDouble(Book::getPrice));
  }

  private static Map<String, List<Book>> groupBooksByAuthor(List<Book> books) {
    return books.stream().collect(Collectors.groupingBy(Book::getAuthor));
  }
}