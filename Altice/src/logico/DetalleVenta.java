package logico;

public class DetalleVenta {

	private Producto miProducto;
	private int calidad;
	
	public DetalleVenta(Producto miProducto, int calidad) {
		super();
		this.miProducto = miProducto;
		this.calidad = calidad;
	}

	public Producto getMiProducto() {
		return miProducto;
	}

	public void setMiProducto(Producto miProducto) {
		this.miProducto = miProducto;
	}

	public int getCalidad() {
		return calidad;
	}

	public void setCalidad(int calidad) {
		this.calidad = calidad;
	}

}
