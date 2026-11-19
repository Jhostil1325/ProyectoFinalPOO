package logico;

public class Telefonia extends Servicio
{
	private int minutos;
	private boolean llamadasIlimitadas;
	
	public Telefonia(String id, String descripcion, boolean enPlan, int minutos, boolean llamadasIlimitadas)
	{
		super(id, descripcion, enPlan);
		this.minutos = minutos;
		this.llamadasIlimitadas = llamadasIlimitadas;
	}

	public int getMinutos()
	{
		return minutos;
	}

	public void setMinutos(int minutos)
	{
		this.minutos = minutos;
	}

	public boolean isLlamadasIlimitadas()
	{
		return llamadasIlimitadas;
	}

	public void setLlamadasIlimitadas(boolean llamadasIlimitadas)
	{
		this.llamadasIlimitadas = llamadasIlimitadas;
	}
	@Override
	public float calcularPrecioBase() {
	    if (llamadasIlimitadas) {
	        return 850;
	    }
	    return minutos * 1.5f;
	}
}
