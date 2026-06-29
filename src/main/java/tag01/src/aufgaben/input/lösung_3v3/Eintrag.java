package aufgaben.input.lösung_3v3;

/*
Es wird der Timestamp(UNIX), Username, IP, URL sowie ob der Zugriff gewährt oder verweigert wurde gespeichert.

Beispiel:
1661156269 Max Mustermann 192.168.178.42 google.de granted
1661156577 Erika Mustermann 192.168.178.47 youtube.de denied
 */

import java.net.InetAddress;
import java.net.URI;
import java.time.Instant;

public class Eintrag
{
	Instant timestamp;
	String username;
	InetAddress ip;
	URI url;
	Zugriff zugriff;

	public Eintrag(Instant timestamp, String username, InetAddress ip, URI url, Zugriff zugriff)
	{
		this.timestamp = timestamp;
		this.username = username;
		this.ip = ip;
		this.url = url;
		this.zugriff = zugriff;
	}

	@Override
	public String toString()
	{
		return "Eintrag{" +
		"timestamp=" + timestamp +
		", username='" + username + '\'' +
		", ip=" + ip +
		", url=" + url +
		", zugriff=" + zugriff +
		'}';
	}

	/*
	Die Datei soll eingelesen und als neue Datei mit folgenden Werten als short.csv (delimiter ,) ausgeben werden:
	Username, IP, Zugriff
	Beispiel:
	Max Mustermann,192.168.178.42,granted
	 */
	public String toShortCSV()
	{
		return username + "," + ip.getHostAddress() + "," + zugriff.toString().toLowerCase();
	}

	/*
	und als long.csv (delimiter ,):
	Timestamp(humanreadable), Username, IP, URL, Zugriff
	Beispiel:
	2022-08-22T08:22:57,Erika Mustermann,192.168.178.47,youtube.de,denied
	 */
	public String toLongCSV()
	{
		return timestamp + "," + username + "," + ip.getHostAddress() + "," + url + "," + zugriff.toString().toLowerCase();
	}
}

