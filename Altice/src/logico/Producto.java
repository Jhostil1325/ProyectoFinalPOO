package logico;

import java.io.Serializable;

public class Producto implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String descripcion;
	private String marca;
	private String tipoProducto;
	private int stock;
	private boolean disponible;
	private boolean estado;
	private float precio;
	
	
	public Producto(String id, String descripcion, String marca, String tipoProducto, int stock, boolean disponible,
			boolean estado, float precio) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.marca = marca;
		this.tipoProducto = tipoProducto;
		this.stock = stock;
		this.disponible = disponible;
		this.estado = estado;
		this.precio = precio;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public String getTipoProducto() {
		return tipoProducto;
	}


	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}


	public int getStock() {
		return stock;
	}


	public void setStock(int stock) {
		this.stock = stock;
	}


	public boolean isDisponible() {
		return disponible;
	}


	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}


	public boolean isEstado() {
		return estado;
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}


	public float getPrecio() {
		return precio;
	}


	public void setPrecio(float precio) {
		this.precio = precio;
	}
}
