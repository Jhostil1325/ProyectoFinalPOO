package logico;

public class Internet extends Servicio
{
	private int velocidad;
	private String tipoConexion;
	
	public Internet(String id, String descripcion, boolean enPlan, int velocidad, String tipoConexion)
	{
		super(id, descripcion, enPlan);
		this.velocidad = velocidad;
		this.tipoConexion = tipoConexion;
	}

	public int getVelocidad()
	{
		return velocidad;
	}

	public void setVelocidad(int velocidad)
	{
		this.velocidad = velocidad;
	}

	public String getTipoConexion()
	{
		return tipoConexion;
	}

	public void setTipoConexion(String tipoConexion)
	{
		this.tipoConexion = tipoConexion;
	}
	
	@Override
	public float calcularPrecioBase() {
	    float precio = velocidad * 15;
	    if (tipoConexion != null && tipoConexion.equalsIgnoreCase("Fibra Optica")) {
	        precio += 500.0f;
	    }
	    return precio;
	}
}
