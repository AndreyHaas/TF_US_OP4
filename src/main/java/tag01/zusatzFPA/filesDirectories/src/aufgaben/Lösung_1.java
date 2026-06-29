package aufgaben;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Lösung_1
{

    public static void main(String[] args)
    {
        // Eventuell muss der Pfad angepasst werden!
        createFile("src/aufgaben/loesung/Loesung.txt");
    }

    public static void createFile(String path)
    {
        try
        {
            //File file = new File(path);
            //System.out.println(file.getAbsolutePath());
            Path p = Paths.get(path);
            System.out.println(p.toAbsolutePath());

            if (!Files.exists(p.getParent()))
                Files.createDirectories(p.getParent());

            if (!Files.exists(p))
            {
                Files.createFile(p);
                System.out.println("Datei erstellt: " + p.getFileName());
            }
            else
                System.out.println("Datei existiert bereits.");

            //if (file.createNewFile()) {
            //System.out.println("Datei erstellt: " + file.getName());
            //} else {
            //System.out.println("Datei existiert bereits.");

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}
