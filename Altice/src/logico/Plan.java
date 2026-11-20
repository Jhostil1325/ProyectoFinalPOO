package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class Plan implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private float precioMensual;
	private boolean estado;
	private ArrayList<Servicio> misServicios;
	
	public Plan(String id, float precioMensual, boolean estado) {
		super();
		this.id = id;
		this.precioMensual = precioMensual;
		this.estado = estado;
		this.misServicios = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getPrecioMensual() {
		return precioMensual;
	}

	public void setPrecioMensual(float precioMensual) {
		this.precioMensual = precioMensual;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public ArrayList<Servicio> getMisServicios() {
		return misServicios;
	}

	public void setMisServicios(ArrayList<Servicio> misServicios) {
		this.misServicios = misServicios;
	}
	
	
	

}
