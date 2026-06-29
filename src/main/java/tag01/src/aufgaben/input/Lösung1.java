package aufgaben.input;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Lösung1
{

	public static void main(String[] args)
	{
		inputStreamToStringWithFileAsResource();
	}

	public static void inputStreamToStringWithFileAsResource()
	{
		Path p = Paths.get("resources/Aufgabe1_output_Lösung.txt");
		try (InputStream inputWithFile = Files.newInputStream(p))
		{
			byte[] arrayForFile = inputWithFile.readAllBytes();
			System.out.println(arrayForFile.length);
			String dataForFile = new String(arrayForFile, StandardCharsets.UTF_8);
			System.out.println(dataForFile);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
