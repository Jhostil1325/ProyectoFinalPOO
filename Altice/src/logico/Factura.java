package logico;

import java.util.Date;

public class Factura {

	private String codigoFactura;
	private Date fechaFactura;
	private float montoFactura;
	
	public Factura(String codigoFactura, Date fechaFactura, float montoFactura) {
		super();
		this.codigoFactura = codigoFactura;
		this.fechaFactura = fechaFactura;
		this.montoFactura = montoFactura;
	}

	public String getCodigoFactura() {
		return codigoFactura;
	}

	public void setCodigoFactura(String codigoFactura) {
		this.codigoFactura = codigoFactura;
	}

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public float getMontoFactura() {
		return montoFactura;
	}

	public void setMontoFactura(float montoFactura) {
		this.montoFactura = montoFactura;
	}

}
