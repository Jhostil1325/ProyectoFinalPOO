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
	
	@Override
	public float calcularPrecioBase() {
	    float precio = cantCanales * 10;
	    if (Calidad != null && Calidad.equalsIgnoreCase("4K")) {
	        precio += 400;
	    } else if (Calidad != null && Calidad.equalsIgnoreCase("HD")) {
	        precio += 200;
	    }
	    return precio;
	}
	
	
}
