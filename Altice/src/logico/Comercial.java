package logico;

public class Comercial extends Empleado
{
	private static final long serialVersionUID = 1L;
	private float metaVentas;

	public Comercial(String id, String nombre, String cedula, String direccion, String telefono, String usuario,
			String password, float sueldobase, float comisionmensual, float comisiontotal, String miLocal,
			float metaVentas)
	{
		super(id, nombre, cedula, direccion, telefono, usuario, password, sueldobase, comisionmensual, comisiontotal,
				miLocal);
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