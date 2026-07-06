package main.java.tag06.src.aufgaben.lösung_2v2;

import java.io.Serializable;

public class Person implements Serializable
{
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Person(String name)
	{
		this.name = name;
	}

	public Person()
	{
	}
}
