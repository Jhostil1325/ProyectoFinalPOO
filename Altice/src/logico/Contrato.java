package logico;

import java.util.Date;

public class Contrato {

	private String id;
	private Cliente miCliente;
	private Comercial facilitador;
	private Trabajador tecnico;
	private Local miLocal;
	private String metodoPago;
	private Plan miPlan;
	private Date fechaUltimoPago;
	private Date fechaIni;
	private boolean activo;
	
	
	public Contrato(String id, Cliente miCliente, Comercial facilitador, Trabajador tecnico, Local miLocal,
			String metodoPago, Plan miPlan, Date fechaUltimoPago, Date fechaIni, boolean activo) {
		super();
		this.id = id;
		this.miCliente = miCliente;
		this.facilitador = facilitador;
		this.tecnico = tecnico;
		this.miLocal = miLocal;
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


	public Trabajador getTecnico() {
		return tecnico;
	}


	public void setTecnico(Trabajador tecnico) {
		this.tecnico = tecnico;
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
