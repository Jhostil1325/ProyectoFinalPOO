package logico;

import java.util.ArrayList;
import java.util.Date;

public class Cliente extends Persona
{
	private static final long serialVersionUID = 1L;
	private ArrayList<DetalleVenta> carrito;
	private Plan carritoPlan;
	private ArrayList<Contrato> misContratos;
	private ArrayList<Pago>misPagos;
	private float deuda;
	private int facturaPendientes;
	private boolean activo;
	
	public Cliente(String id, String nombre, String cedula, String direccion, String telefono, Usuario miUsuario,
			ArrayList<DetalleVenta> carrito, Plan carritoPlan, ArrayList<Contrato> misContratos,
			ArrayList<Pago> misPagos, float deuda, int facturaPendientes)
	{
		super(id, nombre, cedula, direccion, telefono, miUsuario);
		this.carrito = new ArrayList<>();
		this.carritoPlan = carritoPlan;
		this.misContratos = new ArrayList<>();
		this.misPagos = new ArrayList<>();
		this.deuda = deuda;
		this.facturaPendientes = facturaPendientes;
		this.activo = true;
	}
	
	public ArrayList<DetalleVenta> getCarrito()
	{
		return carrito;
	}
	public void setCarrito(ArrayList<DetalleVenta> carrito)
	{
		this.carrito = carrito;
	}
	public Plan getCarritoPlan()
	{
		return carritoPlan;
	}
	public void setCarritoPlan(Plan carritoPlan)
	{
		this.carritoPlan = carritoPlan;
	}
	public ArrayList<Contrato> getMisContratos()
	{
		return misContratos;
	}
	public void setMisContratos(ArrayList<Contrato> misContratos)
	{
		this.misContratos = misContratos;
	}
	public ArrayList<Pago> getMisPagos()
	{
		return misPagos;
	}
	public void setMisPagos(ArrayList<Pago> misPagos)
	{
		this.misPagos = misPagos;
	}

	public float getDeuda() {
		return deuda;
	}

	public void setDeuda(float deuda) {
		deuda = deuda;
	}

	public int getFacturaPendientes() {
		return facturaPendientes;
	}

	public void setFacturaPendientes(int facturaPendientes) {
		this.facturaPendientes = facturaPendientes;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
