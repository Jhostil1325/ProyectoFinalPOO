package logico;

import java.util.ArrayList;
import java.util.Date;

public class Pago {

	private String id;
	private Cliente miCliente;
	private Comercial facilitador;
	private Local miLocal;
	private String metodoPago;
	private ArrayList<DetalleVenta> detalles;
	private Date fechaPago;
	private float totalPago;
	
	public Pago(String id, Cliente miCliente, Comercial facilitador, Local miLocal, String metodoPago,
			ArrayList<DetalleVenta> detalles, Date fechaPago, float totalPago) {
		super();
		this.id = id;
		this.miCliente = miCliente;
		this.facilitador = facilitador;
		this.miLocal = miLocal;
		this.metodoPago = metodoPago;
		this.detalles = new ArrayList<>();
		this.fechaPago = fechaPago;
		this.totalPago = totalPago;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Cliente getMiCliente() {
		return miCliente;
	}

	public void setMiCliente(Cliente miCliente) {
		this.miCliente = miCliente;
	}

	public Comercial getFacilitador() {
		return facilitador;
	}

	public void setFacilitador(Comercial facilitador) {
		this.facilitador = facilitador;
	}

	public Local getMiLocal() {
		return miLocal;
	}

	public void setMiLocal(Local miLocal) {
		this.miLocal = miLocal;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public ArrayList<DetalleVenta> getDetalles() {
		return detalles;
	}

	public void setDetalles(ArrayList<DetalleVenta> detalles) {
		this.detalles = detalles;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public float getTotalPago() {
		return totalPago;
	}

	public void setTotalPago(float totalPago) {
		this.totalPago = totalPago;
	}
	
	
	

}
