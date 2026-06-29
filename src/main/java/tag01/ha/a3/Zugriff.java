package tag01.ha.a3;

public enum Zugriff {
  GRANTED,
  DENIED;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}