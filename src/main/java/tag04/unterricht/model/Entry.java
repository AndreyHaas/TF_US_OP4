package main.java.tag04.unterricht.model;

import java.time.LocalDate;
import java.util.StringJoiner;

public class Entry {

  private final String id;
  private final String title;
  private final String description;
  private final LocalDate date;
  private final int balance;
  private final String currency;

  public Entry(String id, String title, String description, LocalDate date, int balance,
      String currency) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.date = date;
    this.balance = balance;
    this.currency = currency;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getDate() {
    return date;
  }

  public String getCurrency() {
    return currency;
  }

  public int getBalance() {
    return balance;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Entry.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("title='" + title + "'")
        .add("description='" + description + "'")
        .add("date=" + date)
        .add("balance=" + balance)
        .add("currency='" + currency + "'")
        .toString();
  }
}