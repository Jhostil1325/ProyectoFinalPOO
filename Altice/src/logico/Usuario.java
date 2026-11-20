package logico;

import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private String contrasena;
	private String correo;
	private boolean estado;
	private Date creacion;
	
	public Usuario(String id, String nombre, String contrasena, String correo, boolean estado, Date creacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.correo = correo;
		this.estado = estado;
		this.creacion = creacion;
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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Date getCreacion() {
		return creacion;
	}

	public void setCreacion(Date creacion) {
		this.creacion = creacion;
	}
	

	
}
