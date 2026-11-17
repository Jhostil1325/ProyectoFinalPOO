package logico;

public class Comercial extends Empleado
{
	private float metaVentas;

	public Comercial(String id, String nombre, String cedula, String direccion, String telefono, Usuario miUsuario,
			float sueldobase, float comisionmensual, float comisiontotal, Local miLocal, float metaVentas)
	{
		super(id, nombre, cedula, direccion, telefono, miUsuario, sueldobase, comisionmensual, comisiontotal, miLocal);
		this.metaVentas = metaVentas;
	}

	public float getMetaVentas()
	{
		return metaVentas;
	}

	public void setMetaVentas(float metaVentas)
	{
		this.metaVentas = metaVentas;
	}
	
}
