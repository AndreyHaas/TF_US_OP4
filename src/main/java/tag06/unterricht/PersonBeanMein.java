package main.java.tag06.unterricht;


import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

public class PersonBeanMein implements Serializable {

  @Serial
  private static final long serialVersionUID = 4492369394594274020L;

  @NotNull
  private String vorname;

  @NotNull
  private String nachname;

  @NotNull
  private LocalDate date;

  @NotNull
  private String email;

  public PersonBeanMein() {
  }

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PersonBeanMein that = (PersonBeanMein) o;
    return Objects.equals(vorname, that.vorname) && Objects.equals(nachname,
        that.nachname) && Objects.equals(date, that.date) && Objects.equals(email,
        that.email);
  }

  @Override
  public int hashCode() {
    int result = Objects.hashCode(vorname);
    result = 31 * result + Objects.hashCode(nachname);
    result = 31 * result + Objects.hashCode(date);
    result = 31 * result + Objects.hashCode(email);
    return result;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", PersonBeanMein.class.getSimpleName() + "[", "]")
        .add("vorname='" + vorname + "'")
        .add("nachname='" + nachname + "'")
        .add("date=" + date)
        .add("email='" + email + "'")
        .toString();
  }
}