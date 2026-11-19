package logico;

public class Empleado extends Persona
{
	protected float sueldobase;
	protected float comisionmensual;
	protected float comisiontotal;
	protected String miLocal;
	
	public Empleado(String id, String nombre, String cedula, String direccion, String telefono, Usuario miUsuario,
			float sueldobase, float comisionmensual, float comisiontotal, String miLocal)
	{
		super(id, nombre, cedula, direccion, telefono, miUsuario);
		this.sueldobase = sueldobase;
		this.comisionmensual = comisionmensual;
		this.comisiontotal = comisiontotal;
		this.miLocal = miLocal;
	}

	public float getSueldobase()
	{
		return sueldobase;
	}

	public void setSueldobase(float sueldobase)
	{
		this.sueldobase = sueldobase;
	}

	public float getComisionmensual()
	{
		return comisionmensual;
	}

	public void setComisionmensual(float comisionmensual)
	{
		this.comisionmensual = comisionmensual;
	}

	public float getComisiontotal()
	{
		return comisiontotal;
	}

	public void setComisiontotal(float comisiontotal)
	{
		this.comisiontotal = comisiontotal;
	}

	public String getMiLocal()
	{
		return miLocal;
	}

	public void setMiLocal(String miLocal)
	{
		this.miLocal = miLocal;
	}
	


	
	
}
