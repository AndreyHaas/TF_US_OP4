package aufgaben.input.lösung_3v2;

import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Eintrag
{
    public static final List<Eintrag> einträge = new ArrayList<>();

    private long timestamp;
    private String username;
    private InetAddress ipAddress;
    private String url;
    private boolean granted;


    public Eintrag(long timestamp, String username, InetAddress ipAddress, String url, boolean granted)
    {
        this.timestamp = timestamp;
        this.username = username;
        this.ipAddress = ipAddress;
        this.url = url;
        this.granted = granted;

        einträge.add(this);
    }

    // Username, IP, Zugriff
    // Beispiel:
    // Max Mustermann,192.168.178.42,granted
    public String toShortCsv()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(username).append(",").append(ipAddress.getHostAddress()).append(",");

        // ternary operator ( ?: )
        String grantedString = granted ? "granted" : "denied"; // Wenn 'granted' true ist, weise der Variable "granted" zu, sonst weise ihr "denied" zu.
        sb.append(grantedString);

        return sb.toString();
    }

    // Timestamp(humanreadable), Username, IP, URL, Zugriff
    // Beispiel:
    // 2022-08-22T08:22:57,Erika Mustermann,192.168.178.47,youtube.de,denied
    public String toLongCsv()
    {
        StringBuilder sb = new StringBuilder();
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
        sb.append(time.toString().replace('T', ' ')).append(",").append(username).append(",")
            .append(ipAddress.getHostAddress()).append(",").append(url).append(",");

        String grantedString = granted ? "granted" : "denied";
        sb.append(grantedString);

        return sb.toString();
    }

    @Override
    public String toString()
    {
        return "Eintrag{" +
            "timestamp=" + timestamp +
            ", username='" + username + '\'' +
            ", ipAddress=" + ipAddress.getHostAddress() +
            ", url='" + url + '\'' +
            ", granted=" + granted +
            '}';
    }
}
