package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Administrativo;
import logico.Altice;
import logico.Cliente;
import logico.Comercial;
import logico.Persona;

public class Principal extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Principal frame = new Principal();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public Principal()
	{
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				guardarDatos();
			}
		});
		setTitle("Sistema de Gesti\u00F3n Altice");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Comercial");
		mnNewMenu.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnNewMenu);

		JMenu mnGestionClientes = new JMenu("Gesti\u00F3n de Clientes");
		mnNewMenu.add(mnGestionClientes);

		JMenuItem mntmRegistrarCliente = new JMenuItem("Registrar Cliente");
		mntmRegistrarCliente.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				RegCliente regCliente = new RegCliente();
				regCliente.setVisible(true);
			}
		});
		mnGestionClientes.add(mntmRegistrarCliente);

		JMenuItem mntmListarCliente = new JMenuItem("Listar Clientes");
		mntmListarCliente.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ListCliente listCliente = new ListCliente();
				listCliente.setVisible(true);
			}
		});
		mnGestionClientes.add(mntmListarCliente);

		JMenu mnGestionContratos = new JMenu("Gesti\u00F3n de Contrato");
		mnNewMenu.add(mnGestionContratos);

		JMenuItem mntmRegistrarContrato = new JMenuItem("Registrar Contratos");
		mntmRegistrarContrato.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				RegContrato regContrato = new RegContrato();
				regContrato.setVisible(true);
			}
		});
		mnGestionContratos.add(mntmRegistrarContrato);

		JMenuItem mntmListarContrato = new JMenuItem("Listar Contrato");
		mntmListarContrato.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ListContrato listContrato = new ListContrato();
				listContrato.setVisible(true);
			}
		});
		mnGestionContratos.add(mntmListarContrato);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Facturaci\u00F3n");
		mntmNewMenuItem_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Facturacion facturacion = new Facturacion();
				facturacion.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Venta de Productos");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentaProducto venta = new VentaProducto();
				venta.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Generar Factura del Mes");
		mntmNewMenuItem_3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int generadas = Altice.getInstance().generarFacturacionMensual();
				JOptionPane.showMessageDialog(null,
						"Facturaci\u00F3n mensual generada. Facturas emitidas: " + generadas, "Facturaci\u00F3n",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_3);

		JMenu mnNewMenu_1 = new JMenu("Administraci\u00F3n");
		mnNewMenu_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnNewMenu_1);
		
		JMenu mnNewMenu_6 = new JMenu("Gesti\u00F3n de Personal");
		mnNewMenu_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnNewMenu_1.add(mnNewMenu_6);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Registrar Empleado");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegEmpleado regEmpleado = new RegEmpleado();
				regEmpleado.setVisible(true);
			}
		});
		mnNewMenu_6.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Listar Empleados");
		mntmNewMenuItem_5.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ListEmpleado listEmpleado = new ListEmpleado();
				listEmpleado.setVisible(true);
			}
		});
		mnNewMenu_6.add(mntmNewMenuItem_5);

		JMenu mnGestionUsuarios = new JMenu("Gesti\u00F3n de Usuarios");
		mnNewMenu_1.add(mnGestionUsuarios);

		JMenuItem mntmCrearUsuario = new JMenuItem("Crear Usuario");
		mntmCrearUsuario.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				RegUsuario regUsuario = new RegUsuario();
				regUsuario.setVisible(true);
			}
		});
		mnGestionUsuarios.add(mntmCrearUsuario);

		JMenuItem mntmListarUsuario = new JMenuItem("Listar Usuarios");
		mntmListarUsuario.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ListUsuario listUsuario = new ListUsuario();
				listUsuario.setVisible(true);
			}
		});
		mnGestionUsuarios.add(mntmListarUsuario);

		JMenu mnNewMenu_3 = new JMenu("Gesti\u00F3n de Planes y Servicios");
		mnNewMenu_1.add(mnNewMenu_3);

		JMenu mnNewMenu_4 = new JMenu("Planes");
		mnNewMenu_3.add(mnNewMenu_4);

		JMenuItem mntmNewMenuItem = new JMenuItem("Registrar Plan");
		mntmNewMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				RegPlan regPlan = new RegPlan();
				regPlan.setVisible(true);
			}
		});
		mnNewMenu_4.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Listar Planes");
		mntmNewMenuItem_6.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ListPlan listPlan = new ListPlan();
				listPlan.setVisible(true);
			}
		});
		mnNewMenu_4.add(mntmNewMenuItem_6);

		JMenu mnNewMenu_5 = new JMenu("Servicios");
		mnNewMenu_3.add(mnNewMenu_5);

		JMenuItem mntmNewMenuItem_13 = new JMenuItem("Registrar Servicio");
		mntmNewMenuItem_13.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				RegServicio regServicio = new RegServicio();
				regServicio.setVisible(true);
			}
		});
		mnNewMenu_5.add(mntmNewMenuItem_13);

		JMenuItem mntmNewMenuItem_14 = new JMenuItem("Listar Servicios");
		mntmNewMenuItem_14.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ListServicio listServicio = new ListServicio();
				listServicio.setVisible(true);
			}
		});
		mnNewMenu_5.add(mntmNewMenuItem_14);

		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Inventario de Productos");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ListProducto inv = new ListProducto();
				inv.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_7);

		JMenu mnNewMenu_2 = new JMenu("Reportes");
		mnNewMenu_2.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Ventas y Facturaci\u00F3n por Per\u00EDodo");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReporteVentasPeriodo rep = new ReporteVentasPeriodo();
				rep.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_8);

		JMenuItem mntmNewMenuItem_9 = new JMenuItem("Ventas y Contratos por Local");
		mntmNewMenuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReporteVentasLocal rep = new ReporteVentasLocal();
				rep.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_9);

		JMenuItem mntmNewMenuItem_10 = new JMenuItem("Clientes con Deuda");
		mntmNewMenuItem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReporteClientesDeuda rep = new ReporteClientesDeuda();
				rep.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_10);

		JMenuItem mntmNewMenuItem_11 = new JMenuItem("Empleados y Comisiones");
		mntmNewMenuItem_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReporteEmpleadosComisiones rep = new ReporteEmpleadosComisiones();
				rep.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_11);

		JMenuItem mntmNewMenuItem_12 = new JMenuItem("Inventario y Planes Activos");
		mntmNewMenuItem_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReporteInventarioPlanes rep = new ReporteInventarioPlanes();
				rep.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_12);

		JMenu mnCliente = new JMenu("Cliente");
		mnCliente.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnCliente);

		JMenuItem mntmMisContratos = new JMenuItem("Mis Contratos");
		mntmMisContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MisContratos mc = new MisContratos();
				mc.setVisible(true);
			}
		});
		mnCliente.add(mntmMisContratos);

		JMenuItem mntmMisFacturas = new JMenuItem("Mis Facturas");
		mntmMisFacturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MisFacturas mf = new MisFacturas();
				mf.setVisible(true);
			}
		});
		mnCliente.add(mntmMisFacturas);

		menuBar.add(Box.createHorizontalGlue());

		JMenu mnSesion = new JMenu("Sesi\u00F3n");
		mnSesion.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnSesion);

		JMenuItem mntmCerrarSesion = new JMenuItem("Cerrar Sesi\u00F3n");
		mntmCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 15));
		mntmCerrarSesion.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				guardarDatos();
				Altice.setLoginUser(null);
				Login login = new Login();
				dispose();
				login.setVisible(true);
			}
		});
		mnSesion.add(mntmCerrarSesion);

		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.setFont(new Font("Segoe UI", Font.BOLD, 15));
		mntmSalir.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int opcion = JOptionPane.showConfirmDialog(null, "\u00BFEst\u00E1 seguro que desea salir del sistema?",
						"Salir", JOptionPane.YES_NO_OPTION);
				if (opcion == JOptionPane.YES_OPTION)
				{
					guardarDatos();
					System.exit(0);
				}
			}
		});
		mnSesion.add(mntmSalir);

		Persona logueado = Altice.getLoginUser();
		if (logueado != null)
		{
			if (logueado instanceof Administrativo)
			{
				mnNewMenu.setEnabled(true);
				mnNewMenu_1.setEnabled(true);
				mnNewMenu_2.setEnabled(true);
				mnCliente.setEnabled(true);
			}
			else if (logueado instanceof Comercial)
			{
				mnNewMenu.setEnabled(true);
				mnNewMenu_1.setEnabled(false);
				mnNewMenu_2.setEnabled(false);
				mnCliente.setEnabled(false);
			}
			else if (logueado instanceof Cliente)
			{
				mnNewMenu.setEnabled(false);
				mnNewMenu_1.setEnabled(false);
				mnNewMenu_2.setEnabled(false);
				mnCliente.setEnabled(true);
			}
		}

		contentPane = new JPanel();
		contentPane.setBackground(new Color(18, 18, 18));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panelCentro = new JPanel();
		panelCentro.setBackground(new Color(18, 18, 18));
		panelCentro.setLayout(null);
		contentPane.add(panelCentro, BorderLayout.CENTER);

		JLabel lblLogo = new JLabel("altice");
		lblLogo.setForeground(new Color(0, 168, 232));
		lblLogo.setFont(new Font("Tahoma", Font.BOLD, 64));
		lblLogo.setBounds(520, 210, 250, 75);
		panelCentro.add(lblLogo);

		JLabel lblSub = new JLabel("SISTEMA INTEGRAL DE GESTI\u00D3N COMERCIAL Y TELECOMUNICACIONES");
		lblSub.setForeground(Color.WHITE);
		lblSub.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSub.setBounds(345, 295, 620, 25);
		panelCentro.add(lblSub);

		String nombreLog = (logueado != null) ? logueado.getNombre() : "Administrador";
		String rolLog = (logueado != null) ? Altice.getInstance().obtenerTipoPersona(logueado) : "Admin";
		JLabel lblBienvenida = new JLabel("Sesi\u00F3n activa: " + nombreLog + "  |  Rol: " + rolLog);
		lblBienvenida.setForeground(new Color(180, 180, 180));
		lblBienvenida.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBienvenida.setBounds(440, 335, 450, 25);
		panelCentro.add(lblBienvenida);
	}

	private void guardarDatos()
	{
		FileOutputStream archivo;
		ObjectOutputStream salida;
		try
		{
			archivo = new FileOutputStream("altice.dat");
			salida = new ObjectOutputStream(archivo);
			salida.writeObject(Altice.getInstance());
			salida.close();
			archivo.close();
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}
}