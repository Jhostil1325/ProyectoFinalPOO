package logico;

public class Television extends Servicio
{
	private int cantCanales;
	private String Calidad;
	
	public Television(String id, String descripcion, boolean enPlan, int cantCanales, String calidad)
	{
		super(id, descripcion, enPlan);
		this.cantCanales = cantCanales;
		Calidad = calidad;
	}

	public int getCantCanales()
	{
		return cantCanales;
	}

	public void setCantCanales(int cantCanales)
	{
		this.cantCanales = cantCanales;
	}

	public String getCalidad()
	{
		return Calidad;
	}

	public void setCalidad(String calidad)
	{
		Calidad = calidad;
	}
	
}
