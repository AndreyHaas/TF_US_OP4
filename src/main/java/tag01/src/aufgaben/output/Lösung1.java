package aufgaben.output;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Lösung1
{

    public static void main(String[] args)
    {
        String data = "あは幸Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt\r\n"
            + "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco\r\n"
            + "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in\r\n"
            + "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat\r\n"
            + "non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        try
        {
            Path path = Paths.get("resources/Aufgabe1_output_Lösung.txt");
            outputStreamWriter(path, data);
            fileOutputStreamJederZweiteBuchstabe(path, data);
        }
        catch (IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // 1
    public static void outputStreamWriter(Path path, String data) throws IOException
    {
        try (OutputStream out = Files.newOutputStream(path);
             Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8))
        {
            writer.write(data);
        }
    }

    // 2
    public static void fileOutputStreamJederZweiteBuchstabe(Path path, String data) throws IOException
    {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
        {
            char[] arr = data.toCharArray();
            for (int i = 0; i < data.length(); )
            {
                if (Character.isAlphabetic(arr[i]))
                {
                    writer.write(arr[i]);
                    i = i + 2;
                }
                else
                    i++;
            }
        }
    }
}
