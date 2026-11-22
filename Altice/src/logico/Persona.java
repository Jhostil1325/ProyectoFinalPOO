package logico;

import java.io.Serializable;

public class Persona implements Serializable
{
	private static final long serialVersionUID = 1L;
	protected String id;
	protected String nombre;
	protected String cedula;
	protected String direccion;
	protected String telefono;
	protected String usuario;
	protected String password;

	public Persona(String id, String nombre, String cedula, String direccion, String telefono, String usuario,
			String password)
	{
		super();
		this.id = id;
		this.nombre = nombre;
		this.cedula = cedula;
		this.direccion = direccion;
		this.telefono = telefono;
		this.usuario = usuario;
		this.password = password;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String getCedula()
	{
		return cedula;
	}

	public void setCedula(String cedula)
	{
		this.cedula = cedula;
	}

	public String getDireccion()
	{
		return direccion;
	}

	public void setDireccion(String direccion)
	{
		this.direccion = direccion;
	}

	public String getTelefono()
	{
		return telefono;
	}

	public void setTelefono(String telefono)
	{
		this.telefono = telefono;
	}

	public String getUsuario()
	{
		return usuario;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
}