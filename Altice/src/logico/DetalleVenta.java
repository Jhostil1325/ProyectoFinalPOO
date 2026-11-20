package logico;

import java.io.Serializable;

public class DetalleVenta implements Serializable{
	private static final long serialVersionUID = 1L;
	private Producto miProducto;
	private int cantidad;
	
	public DetalleVenta(Producto miProducto, int cantidad) {
		super();
		this.miProducto = miProducto;
		this.cantidad = cantidad;
	}

	public Producto getMiProducto() {
		return miProducto;
	}

	public void setMiProducto(Producto miProducto) {
		this.miProducto = miProducto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
