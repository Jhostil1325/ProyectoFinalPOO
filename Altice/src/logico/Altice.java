package logico;

import java.util.ArrayList;

public class Altice {

	private ArrayList<Persona> misPersonas;
	private ArrayList<Plan> misPlanes;
	private ArrayList<Servicio> misServicios;
	private ArrayList<Producto> misProductos;
	private ArrayList<Pago> misPagos;
	private ArrayList<Contrato> misContratos;
	
	public Altice(ArrayList<Persona> misPersonas, ArrayList<Plan> misPlanes,
			ArrayList<Servicio> misServicios, ArrayList<Producto> misProductos, ArrayList<Pago> misPagos,
			ArrayList<Contrato> misContratos) {
		super();
		this.misPersonas = new ArrayList<>();
		this.misPlanes = new ArrayList<>();
		this.misServicios = new ArrayList<>();
		this.misProductos = new ArrayList<>();
		this.misPagos = new ArrayList<>();
		this.misContratos = new ArrayList<>();
	}

	public ArrayList<Persona> getMisPersonas() {
		return misPersonas;
	}

	public void setMisPersonas(ArrayList<Persona> misPersonas) {
		this.misPersonas = misPersonas;
	}

	public ArrayList<Plan> getMisPlanes() {
		return misPlanes;
	}

	public void setMisPlanes(ArrayList<Plan> misPlanes) {
		this.misPlanes = misPlanes;
	}

	public ArrayList<Servicio> getMisServicios() {
		return misServicios;
	}

	public void setMisServicios(ArrayList<Servicio> misServicios) {
		this.misServicios = misServicios;
	}

	public ArrayList<Producto> getMisProductos() {
		return misProductos;
	}

	public void setMisProductos(ArrayList<Producto> misProductos) {
		this.misProductos = misProductos;
	}

	public ArrayList<Pago> getMisPagos() {
		return misPagos;
	}

	public void setMisPagos(ArrayList<Pago> misPagos) {
		this.misPagos = misPagos;
	}

	public ArrayList<Contrato> getMisContratos() {
		return misContratos;
	}

	public void setMisContratos(ArrayList<Contrato> misContratos) {
		this.misContratos = misContratos;
	}
	

}
