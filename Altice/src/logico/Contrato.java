package logico;

import java.io.Serializable;
import java.util.Date;

public class Contrato implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private Cliente miCliente;
	private Comercial facilitador;
	private String metodoPago;
	private Plan miPlan;
	private Date fechaUltimoPago;
	private Date fechaIni;
	private boolean activo;
	
	
	public Contrato(String id, Cliente miCliente, Comercial facilitador, String metodoPago, Plan miPlan, 
			Date fechaUltimoPago, Date fechaIni, boolean activo) {
		super();
		this.id = id;
		this.miCliente = miCliente;
		this.facilitador = facilitador;
		this.metodoPago = metodoPago;
		this.miPlan = miPlan;
		this.fechaUltimoPago = fechaUltimoPago;
		this.fechaIni = fechaIni;
		this.activo = activo;
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


	public Plan getMiPlan() {
		return miPlan;
	}


	public void setMiPlan(Plan miPlan) {
		this.miPlan = miPlan;
	}


	public Date getFechaUltimoPago() {
		return fechaUltimoPago;
	}


	public void setFechaUltimoPago(Date fechaUltimoPago) {
		this.fechaUltimoPago = fechaUltimoPago;
	}


	public Date getFechaIni() {
		return fechaIni;
	}


	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}


	public boolean isActivo() {
		return activo;
	}


	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	

}
