package logico;

import java.io.Serializable;

public abstract class Servicio implements Serializable
{
	private static final long serialVersionUID = 1L;
	protected String id;
	protected String descripcion;
	protected boolean enPlan;
	
	public Servicio(String id, String descripcion, boolean enPlan)
	{
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.enPlan = enPlan;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public boolean isEnPlan()
	{
		return enPlan;
	}

	public void setEnPlan(boolean enPlan)
	{
		this.enPlan = enPlan;
	}
	
	public abstract float calcularPrecioBase();
}
