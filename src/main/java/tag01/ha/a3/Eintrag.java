package tag01.ha.a3;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public record Eintrag(long timestamp, String username, String ip, String url, Zugriff zugriff) {

  public String toShortCsv() {
    return String.join(",", username, ip, zugriff.toString());
  }

  public String toLongCsv() {
    return String.join(",", formatTimestamp(timestamp), username, ip, url, zugriff.toString());
  }

  private static String formatTimestamp(long unixTimestamp) {
    LocalDateTime dateTime = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(unixTimestamp),
        ZoneId.systemDefault()
    );

    return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  public static Eintrag parseFromLine(String line) {
    String regex = "^(\\d+)\\s+(.+?)\\s+(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})\\s+(\\S+)\\s+(granted|denied)$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(line);

    if (!matcher.matches()) {
      throw new IllegalArgumentException("Ungültiges Zeilenformat: " + line);
    }

    long timestamp = Long.parseLong(matcher.group(1));
    String username = matcher.group(2);
    String ip = matcher.group(3);
    String url = matcher.group(4);
    Zugriff zugriff = Zugriff.valueOf(matcher.group(5).toUpperCase());

    return new Eintrag(timestamp, username, ip, url, zugriff);
  }

  @Override
  public @NotNull String toString() {
    return toLongCsv();
  }
}