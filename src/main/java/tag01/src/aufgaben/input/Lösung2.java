package tag01.src.aufgaben.input;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Lösung2
{

    public static void main(String[] args)
    {
        try
        {
            inputStreamToStringWithScannerWithDelimiter();
            ausgabeSatzzeichen();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void inputStreamToStringWithScannerWithDelimiter() throws IOException
    {
        StringBuilder text = new StringBuilder();

        try (InputStream inputWithFile = Files.newInputStream(Paths.get("resources/Aufgabe1_output_Lösung.txt"));
             Scanner scanner = new Scanner(inputWithFile, StandardCharsets.UTF_8.name())) // Ältere Java Versionen brauchen hier das .name()
        {
            // \w steht für Alphanumerische Character.
            // Dadurch werden alle Buchstaben als Delimiter interpretiert und nicht eingelesen.
            Pattern pattern = Pattern.compile("(\\w)");
            scanner.useDelimiter(pattern);
            while (scanner.hasNext())
            {
                text.append(scanner.next().trim()); // trim() entfernt die Leerzeichen und Zeilenumbrüche.
            }
        }
        System.out.println(text);
    }

    public static void ausgabeSatzzeichen() throws IOException
	{
        String text = Files.readString(Paths.get("resources/Aufgabe1_output_Lösung.txt"));
        char[] chars = text.toCharArray();
		for (char aChar : chars)
			if (!Character.isAlphabetic(aChar) && !Character.isSpaceChar(aChar))
				System.out.print(aChar);
    }
}
