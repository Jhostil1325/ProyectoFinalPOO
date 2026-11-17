package logico;

public class Local {

	private String id;
	private String nombre;
	private String ciudad;
	private String direccion;
	private boolean activo;
	
	public Local(String id, String nombre, String ciudad, String direccion, boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.activo = activo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	

}
