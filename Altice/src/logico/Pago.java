package logico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Pago implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private Cliente miCliente;
	private Comercial facilitador;
	private String metodoPago;
	private ArrayList<DetalleVenta> detalles;
	private Date fechaPago;
	private float totalPago;
	private float itbis;
	private float montoRecibido;
	private float devuelta;
	private boolean pagada;
	
	public Pago(String id, Cliente miCliente, Comercial facilitador, String metodoPago,
			ArrayList<DetalleVenta> detalles, Date fechaPago, float totalPago, float itbis, float montoRecibido, float devuelta) {
		super();
		this.id = id;
		this.miCliente = miCliente;
		this.facilitador = facilitador;
		this.metodoPago = metodoPago;
		this.detalles = new ArrayList<>();
		this.fechaPago = fechaPago;
		this.totalPago = totalPago;
		this.itbis = itbis;
		this.montoRecibido = montoRecibido;
		this.devuelta = devuelta;
		this.pagada = false;
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

	public float getItbis() {
		return itbis;
	}

	public void setItbis(float itbis) {
		this.itbis = itbis;
	}

	public float getMontoRecibido() {
		return montoRecibido;
	}

	public void setMontoRecibido(float montoRecibido) {
		this.montoRecibido = montoRecibido;
	}

	public float getDevuelta() {
		return devuelta;
	}

	public void setDevuelta(float devuelta) {
		this.devuelta = devuelta;
	}

	public boolean isPagada() {
		return pagada;
	}

	public void setPagada(boolean pagada) {
		this.pagada = pagada;
	}
	
	
	

}
