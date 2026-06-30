package tag02.ha.a2.model;

public class Book {

  private String title;
  private String author;
  private int yearOfPublication;
  private double price;

  public Book(String title, String author, int yearOfPublication, double price) {
    this.title = title;
    this.author = author;
    this.yearOfPublication = yearOfPublication;
    this.price = price;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public int getYearOfPublication() {
    return yearOfPublication;
  }

  public double getPrice() {
    return price;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setYearOfPublication(int yearOfPublication) {
    this.yearOfPublication = yearOfPublication;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return String.format("%s (%d) - %.2f €", title, yearOfPublication, price);
  }
}