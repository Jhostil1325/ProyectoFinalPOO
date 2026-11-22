package logico;

public class Administrativo extends Empleado
{
	private static final long serialVersionUID = 1L;
	private String cargo;

	public Administrativo(String id, String nombre, String cedula, String direccion, String telefono, String usuario,
			String password, float sueldobase, float comisionmensual, float comisiontotal, String miLocal,
			String cargo)
	{
		super(id, nombre, cedula, direccion, telefono, usuario, password, sueldobase, comisionmensual, comisiontotal,
				miLocal);
		this.cargo = cargo;
	}

	public String getCargo()
	{
		return cargo;
	}

	public void setCargo(String cargo)
	{
		this.cargo = cargo;
	}
}