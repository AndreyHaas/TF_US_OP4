package tag02.src.aufgaben.lösung_3;

import java.time.LocalDate;

public class ArbeitszeitEintrag
{
	private int mitarbeiterId;
	private String name;
	private LocalDate datum;
	private double arbeitsstunden;

	public ArbeitszeitEintrag(int mitarbeiterId, String name, LocalDate datum, double arbeitsstunden)
	{
		this.mitarbeiterId = mitarbeiterId;
		this.name = name;
		this.datum = datum;
		this.arbeitsstunden = arbeitsstunden;
	}

	public int getMitarbeiterId()
	{
		return mitarbeiterId;
	}

	public String getName()
	{
		return name;
	}

	public LocalDate getDatum()
	{
		return datum;
	}

	public double getArbeitsstunden()
	{
		return arbeitsstunden;
	}

	@Override
	public String toString()
	{
		return "ArbeitszeitEintrag{" +
		"mitarbeiterId=" + mitarbeiterId +
		", name='" + name + '\'' +
		", datum=" + datum +
		", arbeitsstunden=" + arbeitsstunden +
		'}';
	}
}
