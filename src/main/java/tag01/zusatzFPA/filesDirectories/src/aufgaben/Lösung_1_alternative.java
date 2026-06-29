package aufgaben;

import java.io.File;
import java.io.IOException;

public class Lösung_1_alternative
{

    public static void main(String[] args)
    {
        createDirectoryAndFile();
    }

    public static void createDirectoryAndFile()
    {
        String dir = "loesung";
        String file = "Loesung.txt";

        // getName gibt uns den Namen der Klasse mit Packages
        String getName = Lösung_1_alternative.class.getName();
        System.out.println("getName: " + getName);
        // Da Packages nur Ordner sind, können wir daraus den Pfad zu unserer Klasse herleiten.
        // Packages sind mit '.' getrennt, diese müssen wir durch '\' ersetzen.
        String localPath = "src".concat(File.separator).concat(getName).replace('.', File.separatorChar);
        System.out.println("localPath: " + localPath);
        // Nun haben wir einen gültigen Pfad und können diesen in eine File-Instanz packen.
        // Über getAbsolutePath erhalten wir den absoluten Pfad zu unserer Klasse
        String absolutePath = new File(localPath).getAbsolutePath();
        System.out.println("absolutePath: " + absolutePath);

        // Wir wollen aber nicht unsere Klasse, sondern den beinhaltenden Ordner. Diesen erhalten wir über getParent.
        // Wir fügen noch den zu erstellenden Ordner hinzu und haben unser Ziel erreicht.
        File directory = new File(new File(absolutePath).getParent().concat(File.separator).concat(dir));

        if (!directory.exists()) // Wenn der Ordner noch nicht existiert:
        {
            if (directory.mkdir()) // erstellen wir ihn
            {
                System.out.println("Directory erstellt!");
                try
                {
                    // Nun instanziieren wir eine File-Klasse mit der zu erstellenden Datei.
                    File f = new File(directory.getAbsolutePath() + File.separator + file);
                    if (f.createNewFile()) // Wir versuchen abschließend, diese Datei zu erstellen.
                    {
                        System.out.println("Datei erstellt: " + f.getName());
                    }
                    else
                    {
                        System.out.println("Datei konnte nicht erstellt werden.");
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("Directory konnte nicht erstellt werden!");
            }
        }
        else
        {
            System.out.println("Directory existiert bereits");
        }

    }

}
