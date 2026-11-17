package logico;

import java.util.ArrayList;

public class Cliente extends Persona
{
	private ArrayList<DetalleVenta> carrito;
	private Plan carritoPlan;
	private ArrayList<Contrato> misContratos;
	private Date fechaInactivacion;
	private ArrayList<Pago>misPagos;
	
	public Cliente(String id, String nombre, String cedula, String direccion, String telefono, Usuario miUsuario,
			ArrayList<DetalleVenta> carrito, Plan carritoPlan, ArrayList<Contrato> misContratos, Date fechaInactivacion,
			ArrayList<Pago> misPagos)
	{
		super(id, nombre, cedula, direccion, telefono, miUsuario);
		carrito = new ArrayList<>();
		this.carritoPlan = carritoPlan;
		misContratos = new ArrayList<>();
		this.fechaInactivacion = fechaInactivacion;
		misPagos = new ArrayList<>();
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
	public Date getFechaInactivacion()
	{
		return fechaInactivacion;
	}
	public void setFechaInactivacion(Date fechaInactivacion)
	{
		this.fechaInactivacion = fechaInactivacion;
	}
	public ArrayList<Pago> getMisPagos()
	{
		return misPagos;
	}
	public void setMisPagos(ArrayList<Pago> misPagos)
	{
		this.misPagos = misPagos;
	}
}
