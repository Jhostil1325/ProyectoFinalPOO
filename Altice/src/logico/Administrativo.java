package logico;

public class Administrativo extends Empleado
{
	private String cargo;

	public Administrativo(String id, String nombre, String cedula, String direccion, String telefono, Usuario miUsuario,
			float sueldobase, float comisionmensual, float comisiontotal, Local miLocal, String cargo)
	{
		super(id, nombre, cedula, direccion, telefono, miUsuario, sueldobase, comisionmensual, comisiontotal, miLocal);
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
