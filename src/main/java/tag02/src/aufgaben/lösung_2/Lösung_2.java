package tag02.src.aufgaben.lösung_2;

import java.util.List;
import java.util.Map;

public class Lösung_2
{
    public static void main(String[] args)
    {
        createBooks();

        System.out.println("All books");
        List<Book> books = Book.getBooks();
        books.forEach(System.out::println);
        System.out.println();

        System.out.println("Most Expensive");
        Book book = Book.getMostExpensiveBook(books);
        System.out.println(book);
        System.out.println();

        System.out.println("Before 1950");
        books = Book.getBooksBeforeYear(books, 1950);
        books.forEach(System.out::println);
        System.out.println();

        System.out.println("Sorted");
        books = Book.getBooksSortedByTitle(books);
        books.forEach(System.out::println);
        System.out.println();

        System.out.println("Titles");
        List<String> titles = Book.getBookTitles(books);
        titles.forEach(System.out::println);
        System.out.println();

        System.out.println("Average price");
        double price = Book.getAveragePrice(books);
        System.out.println(price);
        System.out.println();

        System.out.println("By Authors");
        Map<String, List<Book>> authorMap = Book.getAuthorMap(Book.getBooks());
        authorMap.forEach((s, bookList) -> System.out.println(bookList));

    }

    private static void createBooks()
    {
        new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 24.99);
        new Book("1984", "George Orwell", 1949, 19.84);
        new Book("To Kill a Mockingbird", "Harper Lee", 1960, 18.60);
        new Book("The Catcher in the Rye", "J.D. Salinger", 1951, 15.98);
        new Book("Brave New World", "Aldous Huxley", 1932, 12.80);
        new Book("Der Hobbit", "J.R.R.Tolkien", 1937, 10.75);
        new Book("Der Herr der Ringe", "J.R.R.Tolkien", 1954, 22.90);
        new Book("Stolz und Vorurteil" ,"Jane Austen", 1813, 12.50);
        new Book("Moby Dick", "Herman Melville", 1851, 17.40);
        new Book("Astrophysics for People in a Hurry", "Neil deGrasse Tyson", 2017, 19.50);
    }
}
