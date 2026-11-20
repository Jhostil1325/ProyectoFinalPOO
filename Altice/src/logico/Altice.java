package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class Altice implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static Altice altice = null;
	private static Persona loginuser = null;
	private ArrayList<Persona> misPersonas;
	private ArrayList<Plan> misPlanes;
	private ArrayList<Servicio> misServicios;
	private ArrayList<Producto> misProductos;
	private ArrayList<Pago> misPagos;
	private ArrayList<Contrato> misContratos;
	
	public Altice() {
		super();
		this.misPersonas = new ArrayList<>();
		this.misPlanes = new ArrayList<>();
		this.misServicios = new ArrayList<>();
		this.misProductos = new ArrayList<>();
		this.misPagos = new ArrayList<>();
		this.misContratos = new ArrayList<>();
	}

	
	 public static Altice getInstance() {
		 if(altice == null) {
			 altice = new Altice();
		 }
		 return altice;
	 }
	 
	 public static void setAltice(Altice temp) {
			altice = temp;
		}

		public static Persona getLoginUser() {
			return loginuser;
		}

		public static void setLoginUser(Persona user) {
			loginuser = user;
		}

		public void regPersona(Persona p) {
			misPersonas.add(p);
		}

		public boolean confirmLogin(String username, String password) {
			for (Persona p : misPersonas) {
				if (p.getMiUsuario() != null && p.getMiUsuario().isEstado()) {
					if (p.getMiUsuario().getNombre().equals(username) && 
					    p.getMiUsuario().getContrasena().equals(password)) {
						loginuser = p;
						return true;
					}
				}
			}
			return false;
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
