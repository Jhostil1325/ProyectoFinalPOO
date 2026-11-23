package logico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Altice implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static final float PORCENTAJE_COMISION = 0.05f;
	private static Altice altice = null;
	private static Persona loginuser = null;
	private ArrayList<Persona> misPersonas;
	private ArrayList<Plan> misPlanes;
	private ArrayList<Servicio> misServicios;
	private ArrayList<Producto> misProductos;
	private ArrayList<Pago> misPagos;
	private ArrayList<Contrato> misContratos;

	public Altice()
	{
		super();
		this.misPersonas = new ArrayList<>();
		this.misPlanes = new ArrayList<>();
		this.misServicios = new ArrayList<>();
		this.misProductos = new ArrayList<>();
		this.misPagos = new ArrayList<>();
		this.misContratos = new ArrayList<>();
	}

	public static Altice getInstance()
	{
		if (altice == null)
		{
			altice = new Altice();
		}
		return altice;
	}

	public static void setAltice(Altice temp)
	{
		altice = temp;
	}

	public static Persona getLoginUser()
	{
		return loginuser;
	}

	public static void setLoginUser(Persona user)
	{
		loginuser = user;
	}

	public void regPersona(Persona p)
	{
		misPersonas.add(p);
	}

	public boolean confirmLogin(String username, String password)
	{
		for (int i = 0; i < misPersonas.size(); i++)
		{
			Persona p = misPersonas.get(i);
			if (p.getUsuario() != null && p.getPassword() != null)
			{
				if (p.getUsuario().equals(username) && p.getPassword().equals(password))
				{
					loginuser = p;
					return true;
				}
			}
		}
		return false;
	}

	public boolean existeUsuario(String username)
	{
		if (username == null || username.trim().isEmpty())
		{
			return false;
		}
		for (int i = 0; i < misPersonas.size(); i++)
		{
			Persona p = misPersonas.get(i);
			if (p.getUsuario() != null && p.getUsuario().equalsIgnoreCase(username.trim()))
			{
				return true;
			}
		}
		return false;
	}

	public ArrayList<Persona> getMisPersonas()
	{
		return misPersonas;
	}

	public void setMisPersonas(ArrayList<Persona> misPersonas)
	{
		this.misPersonas = misPersonas;
	}

	public ArrayList<Plan> getMisPlanes()
	{
		return misPlanes;
	}

	public void setMisPlanes(ArrayList<Plan> misPlanes)
	{
		this.misPlanes = misPlanes;
	}

	public ArrayList<Servicio> getMisServicios()
	{
		return misServicios;
	}

	public void setMisServicios(ArrayList<Servicio> misServicios)
	{
		this.misServicios = misServicios;
	}

	public ArrayList<Producto> getMisProductos()
	{
		return misProductos;
	}

	public void setMisProductos(ArrayList<Producto> misProductos)
	{
		this.misProductos = misProductos;
	}

	public ArrayList<Pago> getMisPagos()
	{
		return misPagos;
	}

	public void setMisPagos(ArrayList<Pago> misPagos)
	{
		this.misPagos = misPagos;
	}

	public ArrayList<Contrato> getMisContratos()
	{
		return misContratos;
	}

	public void setMisContratos(ArrayList<Contrato> misContratos)
	{
		this.misContratos = misContratos;
	}

	public boolean existeCedula(String cedula)
	{
		for (int i = 0; i < misPersonas.size(); i++)
		{
			Persona p = misPersonas.get(i);
			if (p.getCedula() != null && p.getCedula().equalsIgnoreCase(cedula))
			{
				return true;
			}
		}
		return false;
	}

	public Cliente buscarClienteCed(String cedula)
	{
		for (int i = 0; i < misPersonas.size(); i++)
		{
			Persona p = misPersonas.get(i);
			if (p instanceof Cliente && p.getCedula().equalsIgnoreCase(cedula.trim()))
			{
				return (Cliente) p;
			}
		}
		return null;
	}

	public String generarIdCliente()
	{
		return "CLI-" + (getClientes().size() + 1);
	}

	public boolean registrarCliente(Cliente cliente)
	{
		if (existeCedula(cliente.getCedula()) || existeUsuario(cliente.getUsuario()))
		{
			return false;
		}
		misPersonas.add(cliente);
		return true;
	}

	public ArrayList<Cliente> getClientes()
	{
		ArrayList<Cliente> clientes = new ArrayList<>();
		for (int i = 0; i < misPersonas.size(); i++)
		{
			Persona p = misPersonas.get(i);
			if (p instanceof Cliente)
			{
				clientes.add((Cliente) p);
			}
		}
		return clientes;
	}

	public String generarIdEmpleado()
	{
		return "EMP-" + (getEmpleados().size() + 1);
	}

	public boolean registrarEmpleado(Empleado empleado)
	{
		if (existeCedula(empleado.getCedula()) || existeUsuario(empleado.getUsuario()))
		{
			return false;
		}
		misPersonas.add(empleado);
		return true;
	}

	public ArrayList<Empleado> getEmpleados()
	{
		ArrayList<Empleado> empleados = new ArrayList<>();
		for (int i = 0; i < misPersonas.size(); i++)
		{
			Persona p = misPersonas.get(i);
			if (p instanceof Empleado)
			{
				empleados.add((Empleado) p);
			}
		}
		return empleados;
	}

	public String generarIdContrato()
	{
		return "CON-" + (misContratos.size() + 1);
	}

	public String generarIdPago()
	{
		return "FAC-" + (misPagos.size() + 1);
	}

	public ArrayList<Plan> obtenerPlanesActivos()
	{
		ArrayList<Plan> activos = new ArrayList<>();
		for (int i = 0; i < misPlanes.size(); i++)
		{
			Plan p = misPlanes.get(i);
			if (p.isEstado())
			{
				activos.add(p);
			}
		}
		return activos;
	}

	public Contrato crearContrato(Cliente cliente, Comercial facilitador, Plan plan, String metodoPago)
	{
		if (cliente.getDeuda() > 0 || cliente.getFacturaPendientes() > 0)
		{
			return null;
		}

		Date hoy = new Date();
		Contrato nuevo = new Contrato(generarIdContrato(), cliente, facilitador, metodoPago, plan, hoy, hoy, true);
		misContratos.add(nuevo);
		cliente.getMisContratos().add(nuevo);
		cliente.setActivo(true);

		float subtotal = plan.getPrecioMensual();
		float itbis = subtotal * 0.18f;
		float total = subtotal + itbis;

		Pago facturaInicial = new Pago(generarIdPago(), cliente, facilitador, metodoPago,
				new ArrayList<DetalleVenta>(), hoy, total, itbis, 0.0f, 0.0f, nuevo);
		facturaInicial.setPagada(false);

		misPagos.add(facturaInicial);
		cliente.getMisPagos().add(facturaInicial);
		cliente.setDeuda(cliente.getDeuda() + total);
		cliente.setFacturaPendientes(cliente.getFacturaPendientes() + 1);

		return nuevo;
	}

	public int generarFacturacionMensual()
	{
		int facturasGeneradas = 0;
		Date hoy = new Date();

		for (int i = 0; i < misContratos.size(); i++)
		{
			Contrato c = misContratos.get(i);
			if (c.isActivo())
			{
				Cliente cli = c.getMiCliente();
				float subtotal = c.getMiPlan().getPrecioMensual();
				float itbis = subtotal * 0.18f;
				float total = subtotal + itbis;

				Pago facturaMes = new Pago(generarIdPago(), cli, c.getFacilitador(), c.getMetodoPago(),
						new ArrayList<DetalleVenta>(), hoy, total, itbis, 0.0f, 0.0f, c);
				facturaMes.setPagada(false);

				misPagos.add(facturaMes);
				cli.getMisPagos().add(facturaMes);

				cli.setDeuda(cli.getDeuda() + total);
				cli.setFacturaPendientes(cli.getFacturaPendientes() + 1);
				facturasGeneradas++;

				if (cli.getFacturaPendientes() > 2)
				{
					cli.setActivo(false);
					for (int j = 0; j < cli.getMisContratos().size(); j++)
					{
						cli.getMisContratos().get(j).setActivo(false);
					}
				}
			}
		}
		return facturasGeneradas;
	}

	public ArrayList<Pago> obtenerFacturasPendientesCliente(Cliente cliente)
	{
		ArrayList<Pago> pendientes = new ArrayList<>();
		for (int i = 0; i < cliente.getMisPagos().size(); i++)
		{
			Pago p = cliente.getMisPagos().get(i);
			if (!p.isPagada())
			{
				pendientes.add(p);
			}
		}
		return pendientes;
	}

	public float procesarCobroFacturas(Cliente cliente, Comercial facilitador, ArrayList<Pago> aPagar,
			String metodoPago, float montoRecibido)
	{
		float totalCobrado = 0.0f;
		for (int i = 0; i < aPagar.size(); i++)
		{
			totalCobrado += aPagar.get(i).getTotalPago();
		}

		if (montoRecibido < totalCobrado)
		{
			return -1.0f;
		}

		float devuelta = montoRecibido - totalCobrado;
		Date hoy = new Date();

		for (int i = 0; i < aPagar.size(); i++)
		{
			Pago p = aPagar.get(i);
			p.setPagada(true);
			p.setFechaPago(hoy);
			p.setMetodoPago(metodoPago);
			if (facilitador != null)
			{
				p.setFacilitador(facilitador);
			}
			p.setMontoRecibido(montoRecibido);
			p.setDevuelta(devuelta);
		}

		float nuevaDeuda = cliente.getDeuda() - totalCobrado;
		if (nuevaDeuda < 0.01f)
		{
			nuevaDeuda = 0.0f;
		}
		cliente.setDeuda(nuevaDeuda);

		int nuevasPendientes = cliente.getFacturaPendientes() - aPagar.size();
		if (nuevasPendientes < 0)
		{
			nuevasPendientes = 0;
		}
		cliente.setFacturaPendientes(nuevasPendientes);

		if (cliente.getFacturaPendientes() <= 2)
		{
			cliente.setActivo(true);
			for (int i = 0; i < cliente.getMisContratos().size(); i++)
			{
				Contrato c = cliente.getMisContratos().get(i);
				c.setActivo(true);
				c.setFechaUltimoPago(hoy);
			}
		}

		if (facilitador != null)
		{
			facilitador.setComisionmensual(calcularComisionMensual(facilitador));
			facilitador.setComisiontotal(facilitador.getComisiontotal() + (totalCobrado * PORCENTAJE_COMISION));
		}

		return devuelta;
	}

	public float calcularComisionMensual(Comercial comercial)
	{
		Calendar hoy = Calendar.getInstance();
		Calendar fecha = Calendar.getInstance();
		float total = 0.0f;
		for (int i = 0; i < misPagos.size(); i++)
		{
			Pago p = misPagos.get(i);
			if (p.isPagada() && p.getFacilitador() == comercial && p.getFechaPago() != null)
			{
				fecha.setTime(p.getFechaPago());
				if (fecha.get(Calendar.MONTH) == hoy.get(Calendar.MONTH)
						&& fecha.get(Calendar.YEAR) == hoy.get(Calendar.YEAR))
				{
					total += p.getTotalPago() * PORCENTAJE_COMISION;
				}
			}
		}
		return total;
	}

	public String generarIdServicio()
	{
		return "SRV-" + (misServicios.size() + 1);
	}

	public String generarIdPlan()
	{
		return "PLN-" + (misPlanes.size() + 1);
	}

	public void registrarServicio(Servicio s)
	{
		misServicios.add(s);
	}

	public void registrarPlan(Plan p)
	{
		misPlanes.add(p);
	}

	public Persona buscarPersonaCed(String cedula)
	{
		for (int i = 0; i < misPersonas.size(); i++)
		{
			Persona p = misPersonas.get(i);
			if (p.getCedula() != null && p.getCedula().equalsIgnoreCase(cedula.trim()))
			{
				return p;
			}
		}
		return null;
	}

	public boolean asignarUsuario(Persona persona, String usuario, String password)
	{
		if (existeUsuario(usuario))
		{
			return false;
		}
		persona.setUsuario(usuario.trim());
		persona.setPassword(password);
		return true;
	}

	public ArrayList<Persona> getPersonasConUsuario()
	{
		ArrayList<Persona> conUsuario = new ArrayList<>();
		for (int i = 0; i < misPersonas.size(); i++)
		{
			Persona p = misPersonas.get(i);
			if (p.getUsuario() != null && !p.getUsuario().trim().isEmpty())
			{
				conUsuario.add(p);
			}
		}
		return conUsuario;
	}

	public String obtenerTipoPersona(Persona p)
	{
		if (p instanceof Cliente)
		{
			return "Cliente";
		}
		if (p instanceof Comercial)
		{
			return "Comercial";
		}
		if (p instanceof Administrativo)
		{
			return "Administrativo";
		}
		return "Persona";
	}
	
	public boolean esAdminBase(Persona persona)
	{
		return persona.getUsuario() != null && persona.getUsuario().equalsIgnoreCase("Admin");
	}

	private boolean cedulaUsadaPorOtro(Persona actual, String cedula)
	{
		for (int i = 0; i < misPersonas.size(); i++)
		{
			Persona p = misPersonas.get(i);
			if (p != actual && p.getCedula() != null && p.getCedula().equalsIgnoreCase(cedula.trim()))
			{
				return true;
			}
		}
		return false;
	}

	public boolean modificarCliente(Cliente cliente, String nombre, String cedula, String direccion,
			String telefono)
	{
		if (cedulaUsadaPorOtro(cliente, cedula))
		{
			return false;
		}
		cliente.setNombre(nombre);
		cliente.setCedula(cedula.trim());
		cliente.setDireccion(direccion);
		cliente.setTelefono(telefono);
		return true;
	}

	public boolean modificarEmpleado(Empleado empleado, String nombre, String cedula, String direccion,
			String telefono, float sueldoBase, String local)
	{
		if (cedulaUsadaPorOtro(empleado, cedula))
		{
			return false;
		}
		empleado.setNombre(nombre);
		empleado.setCedula(cedula.trim());
		empleado.setDireccion(direccion);
		empleado.setTelefono(telefono);
		empleado.setSueldobase(sueldoBase);
		empleado.setMiLocal(local);
		return true;
	}

	public boolean modificarUsuario(Persona persona, String usuario, String password)
	{
		for (int i = 0; i < misPersonas.size(); i++)
		{
			Persona p = misPersonas.get(i);
			if (p != persona && p.getUsuario() != null && p.getUsuario().equalsIgnoreCase(usuario.trim()))
			{
				return false;
			}
		}
		persona.setUsuario(usuario.trim());
		if (password != null && !password.isEmpty())
		{
			persona.setPassword(password);
		}
		return true;
	}

	public void modificarPlan(Plan plan, float precioMensual)
	{
		plan.setPrecioMensual(precioMensual);
	}

	public void modificarContrato(Contrato contrato, Plan plan, String metodoPago, boolean activo)
	{
		contrato.setMiPlan(plan);
		contrato.setMetodoPago(metodoPago);
		contrato.setActivo(activo);
	}
}