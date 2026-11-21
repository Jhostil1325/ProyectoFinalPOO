package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.Altice;

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
		});
		setTitle("Sistema de Gesti\u00F3n Altice");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Comercial");
		menuBar.add(mnNewMenu);

		// Gestion de Clientes ahora es un submenu con dos opciones
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

		JMenuItem mntmListarCliente = new JMenuItem("Listar Cliente");
		mntmListarCliente.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ListCliente listCliente = new ListCliente();
				listCliente.setVisible(true);
			}
		});
		mnGestionClientes.add(mntmListarCliente);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Facturaci\u00F3n");
		mnNewMenu.add(mntmNewMenuItem_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Venta de Productos");
		mnNewMenu.add(mntmNewMenuItem_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Generar Facturaci\u00F3n del Mes");
		mnNewMenu.add(mntmNewMenuItem_3);

		JMenu mnNewMenu_1 = new JMenu("Administraci\u00F3n");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Gesti\u00F3n de Personal");
		mnNewMenu_1.add(mntmNewMenuItem_4);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Registrar Usuario");
		mnNewMenu_1.add(mntmNewMenuItem_5);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Gesti\u00F3n de Planes y Servicios");
		mnNewMenu_1.add(mntmNewMenuItem_6);

		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Inventario de Productos");
		mnNewMenu_1.add(mntmNewMenuItem_7);

		JMenu mnNewMenu_2 = new JMenu("Reportes");
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Ventas y Facturaci\u00F3n por Per\u00EDodo");
		mnNewMenu_2.add(mntmNewMenuItem_8);

		JMenuItem mntmNewMenuItem_9 = new JMenuItem("Ventas y Contratos por Local");
		mnNewMenu_2.add(mntmNewMenuItem_9);

		JMenuItem mntmNewMenuItem_10 = new JMenuItem("Clientes con Deuda");
		mnNewMenu_2.add(mntmNewMenuItem_10);

		JMenuItem mntmNewMenuItem_11 = new JMenuItem("Empleados y Comisiones");
		mnNewMenu_2.add(mntmNewMenuItem_11);

		JMenuItem mntmNewMenuItem_12 = new JMenuItem("Inventario y Planes Activos");
		mnNewMenu_2.add(mntmNewMenuItem_12);

		if (Altice.getLoginUser() != null && Altice.getLoginUser().getMiUsuario() != null)
		{
			if (!Altice.getLoginUser().getMiUsuario().getTipo().equalsIgnoreCase("Administrador"))
			{
				mnNewMenu_1.setEnabled(false);
			}
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
}