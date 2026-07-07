package main.java.tag07.src.aufgaben.lösung_2v2;

/*
{
  "name": "Jenny Azad",
  "age": 30,
  "address": {
    "street": "Musterstraße",
    "number": 123,
    "city": "Musterstadt"
  },
  "hobbies": ["lesen", "schwimmen", "reisen"]
}
 */
public class Person
{
	private String name;
	private int age;
	private Address address;
	private String[] hobbies;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public Address getAddress()
	{
		return address;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}

	public String[] getHobbies()
	{
		return hobbies;
	}

	public void setHobbies(String[] hobbies)
	{
		this.hobbies = hobbies;
	}

	public Person(String name, int age, Address address, String[] hobbies)
	{
		this.name = name;
		this.age = age;
		this.address = address;
		this.hobbies = hobbies;
	}

	public Person()
	{
	}
}
