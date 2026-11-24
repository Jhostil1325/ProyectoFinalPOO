package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Cliente;
import logico.Comercial;
import logico.DetalleVenta;
import logico.Producto;

public class VentaProducto extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTextField txtCedula;
	private JTextField txtCliente;
	private JComboBox<String> cbxProducto;
	private JSpinner spnCantidad;
	private JTable table;
	private DefaultTableModel model;
	private JComboBox<String> cbxMetodo;
	private JTextField txtSubtotal;
	private JTextField txtItbis;
	private JTextField txtTotal;
	private JTextField txtRecibido;
	private JTextField txtDevuelta;

	private Cliente clienteActual = null;
	private ArrayList<Producto> disponibles = new ArrayList<>();
	private ArrayList<DetalleVenta> carrito = new ArrayList<>();
	private float totalPagar = 0.0f;

	public VentaProducto()
	{
		setTitle("Venta de Productos y Equipos");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 730, 575);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
		JLabel lblHeader = new JLabel("ALTICE | PUNTO DE VENTA DE EQUIPOS Y ACCESORIOS");
		lblHeader.setForeground(new Color(0, 168, 232));
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelHeader.add(lblHeader);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(24, 24, 24));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblCedula = new JLabel("C\u00E9dula Cliente:");
		lblCedula.setForeground(Color.WHITE);
		lblCedula.setBounds(25, 18, 120, 14);
		panel.add(lblCedula);

		txtCedula = new JTextField();
		txtCedula.setBounds(25, 38, 180, 26);
		panel.add(txtCedula);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(new Color(0, 136, 255));
		btnBuscar.setForeground(Color.WHITE);
		btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBuscar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buscarCliente();
			}
		});
		btnBuscar.setBounds(215, 38, 90, 26);
		panel.add(btnBuscar);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setForeground(Color.WHITE);
		lblCliente.setBounds(325, 18, 100, 14);
		panel.add(lblCliente);

		txtCliente = new JTextField();
		txtCliente.setEditable(false);
		txtCliente.setBackground(new Color(40, 40, 40));
		txtCliente.setForeground(new Color(0, 168, 232));
		txtCliente.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtCliente.setBounds(325, 38, 360, 26);
		panel.add(txtCliente);

		JLabel lblProd = new JLabel("Producto Disponible:");
		lblProd.setForeground(Color.WHITE);
		lblProd.setBounds(25, 78, 160, 14);
		panel.add(lblProd);

		cbxProducto = new JComboBox<String>();
		cbxProducto.setBounds(25, 98, 360, 26);
		panel.add(cbxProducto);

		JLabel lblCant = new JLabel("Cant.:");
		lblCant.setForeground(Color.WHITE);
		lblCant.setBounds(400, 78, 60, 14);
		panel.add(lblCant);

		spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		spnCantidad.setBounds(400, 98, 70, 26);
		panel.add(spnCantidad);

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBackground(new Color(0, 168, 232));
		btnAgregar.setForeground(Color.WHITE);
		btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAgregar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				agregarAlCarrito();
			}
		});
		btnAgregar.setBounds(485, 98, 95, 26);
		panel.add(btnAgregar);

		JButton btnQuitar = new JButton("Quitar");
		btnQuitar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				quitarDelCarrito();
			}
		});
		btnQuitar.setBounds(590, 98, 95, 26);
		panel.add(btnQuitar);

		String[] columnas = { "ID", "Producto", "Marca", "Precio Unit.", "Cantidad", "Subtotal" };
		model = new DefaultTableModel(columnas, 0)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};

		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setBackground(new Color(30, 30, 30));
		table.getTableHeader().setForeground(Color.WHITE);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(25, 138, 660, 185);
		panel.add(scroll);

		JLabel lblSub = new JLabel("Subtotal:");
		lblSub.setForeground(Color.WHITE);
		lblSub.setBounds(25, 338, 100, 14);
		panel.add(lblSub);

		txtSubtotal = new JTextField("RD$ 0.00");
		txtSubtotal.setEditable(false);
		txtSubtotal.setBounds(25, 358, 145, 26);
		panel.add(txtSubtotal);

		JLabel lblItbis = new JLabel("ITBIS (18%):");
		lblItbis.setForeground(Color.WHITE);
		lblItbis.setBounds(190, 338, 100, 14);
		panel.add(lblItbis);

		txtItbis = new JTextField("RD$ 0.00");
		txtItbis.setEditable(false);
		txtItbis.setBounds(190, 358, 145, 26);
		panel.add(txtItbis);

		JLabel lblTotal = new JLabel("Total a Pagar:");
		lblTotal.setForeground(new Color(0, 168, 232));
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTotal.setBounds(355, 338, 120, 14);
		panel.add(lblTotal);

		txtTotal = new JTextField("RD$ 0.00");
		txtTotal.setEditable(false);
		txtTotal.setBackground(new Color(40, 40, 40));
		txtTotal.setForeground(new Color(0, 168, 232));
		txtTotal.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtTotal.setBounds(355, 358, 155, 26);
		panel.add(txtTotal);

		JLabel lblMetodo = new JLabel("M\u00E9todo de Pago:");
		lblMetodo.setForeground(Color.WHITE);
		lblMetodo.setBounds(530, 338, 120, 14);
		panel.add(lblMetodo);

		cbxMetodo = new JComboBox<String>();
		cbxMetodo.addItem("Efectivo");
		cbxMetodo.addItem("Tarjeta");
		cbxMetodo.addItem("Transferencia");
		cbxMetodo.setBounds(530, 358, 155, 26);
		panel.add(cbxMetodo);

		JLabel lblRecibido = new JLabel("Monto Recibido:");
		lblRecibido.setForeground(Color.WHITE);
		lblRecibido.setBounds(25, 398, 130, 14);
		panel.add(lblRecibido);

		txtRecibido = new JTextField();
		txtRecibido.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				calcularDevuelta();
			}
		});
		txtRecibido.setBounds(25, 418, 160, 26);
		panel.add(txtRecibido);

		JLabel lblDevuelta = new JLabel("Devuelta:");
		lblDevuelta.setForeground(Color.WHITE);
		lblDevuelta.setBounds(205, 398, 100, 14);
		panel.add(lblDevuelta);

		txtDevuelta = new JTextField();
		txtDevuelta.setEditable(false);
		txtDevuelta.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtDevuelta.setBounds(205, 418, 160, 26);
		panel.add(txtDevuelta);

		JButton btnProcesar = new JButton("Facturar Venta");
		btnProcesar.setBackground(new Color(0, 136, 255));
		btnProcesar.setForeground(Color.WHITE);
		btnProcesar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnProcesar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				procesarVenta();
			}
		});
		btnProcesar.setBounds(415, 415, 145, 30);
		panel.add(btnProcesar);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnCerrar.setBounds(575, 415, 110, 30);
		panel.add(btnCerrar);

		cargarComboProductos();
	}

	private void cargarComboProductos()
	{
		cbxProducto.removeAllItems();
		disponibles = Altice.getInstance().obtenerProductosDisponibles();
		for (int i = 0; i < disponibles.size(); i++)
		{
			Producto p = disponibles.get(i);
			cbxProducto.addItem(p.getDescripcion() + " (" + p.getMarca() + ") - RD$ "
					+ String.format("%.2f", p.getPrecio()) + " [Stock: " + p.getStock() + "]");
		}
	}

	private void buscarCliente()
	{
		String cedula = txtCedula.getText().trim();
		if (cedula.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Ingrese la c\u00E9dula del cliente.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		clienteActual = Altice.getInstance().buscarClienteCed(cedula);
		if (clienteActual == null)
		{
			txtCliente.setText("");
			JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			txtCliente.setText(clienteActual.getNombre());
		}
	}

	private void agregarAlCarrito()
	{
		int index = cbxProducto.getSelectedIndex();
		if (index < 0 || index >= disponibles.size())
		{
			return;
		}

		Producto seleccionado = disponibles.get(index);
		int cantidad = Integer.parseInt(spnCantidad.getValue().toString());

		int enCarrito = 0;
		DetalleVenta existente = null;
		for (DetalleVenta d : carrito)
		{
			if (d.getMiProducto() == seleccionado)
			{
				enCarrito = d.getCantidad();
				existente = d;
				break;
			}
		}

		if (enCarrito + cantidad > seleccionado.getStock())
		{
			JOptionPane.showMessageDialog(this, "Stock insuficiente. Disponible: " + seleccionado.getStock(),
					"Inventario", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (existente != null)
		{
			existente.setCantidad(existente.getCantidad() + cantidad);
		}
		else
		{
			carrito.add(new DetalleVenta(seleccionado, cantidad));
		}

		actualizarTablaCarrito();
	}

	private void quitarDelCarrito()
	{
		int row = table.getSelectedRow();
		if (row >= 0)
		{
			carrito.remove(row);
			actualizarTablaCarrito();
		}
	}

	private void actualizarTablaCarrito()
	{
		model.setRowCount(0);
		float subtotal = 0.0f;

		for (DetalleVenta d : carrito)
		{
			Producto p = d.getMiProducto();
			float sub = p.getPrecio() * d.getCantidad();
			subtotal += sub;

			Object[] fila = new Object[6];
			fila[0] = p.getId();
			fila[1] = p.getDescripcion();
			fila[2] = p.getMarca();
			fila[3] = String.format("RD$ %.2f", p.getPrecio());
			fila[4] = d.getCantidad();
			fila[5] = String.format("RD$ %.2f", sub);
			model.addRow(fila);
		}

		float itbis = subtotal * 0.18f;
		totalPagar = subtotal + itbis;

		txtSubtotal.setText(String.format("RD$ %.2f", subtotal));
		txtItbis.setText(String.format("RD$ %.2f", itbis));
		txtTotal.setText(String.format("RD$ %.2f", totalPagar));
		txtDevuelta.setText("");
	}

	private void calcularDevuelta()
	{
		try
		{
			float recibido = Float.parseFloat(txtRecibido.getText().trim());
			if (recibido < totalPagar)
			{
				txtDevuelta.setText("Insuficiente");
			}
			else
			{
				txtDevuelta.setText(String.format("RD$ %.2f", recibido - totalPagar));
			}
		}
		catch (NumberFormatException ex)
		{
			txtDevuelta.setText("Inv\u00E1lido");
		}
	}

	private void procesarVenta()
	{
		if (clienteActual == null)
		{
			JOptionPane.showMessageDialog(this, "Debe buscar y seleccionar un cliente primero.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (carrito.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Agregue al menos un producto al carrito.", "Carrito vac\u00EDo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		float recibido = 0.0f;
		try
		{
			recibido = Float.parseFloat(txtRecibido.getText().trim());
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(this, "Ingrese un monto recibido v\u00E1lido.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (recibido < totalPagar)
		{
			JOptionPane.showMessageDialog(this, "El monto recibido es menor al total.", "Pago insuficiente",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Comercial facilitador = (Altice.getLoginUser() instanceof Comercial) ? (Comercial) Altice.getLoginUser() : null;
		String metodo = cbxMetodo.getSelectedItem().toString();

		float devuelta = Altice.getInstance().procesarVentaProductos(clienteActual, facilitador, carrito, metodo,
				recibido);
		if (devuelta >= 0.0f)
		{
			txtDevuelta.setText(String.format("RD$ %.2f", devuelta));
			JOptionPane.showMessageDialog(this,
					String.format("Venta facturada con \u00E9xito.\nDevuelta: RD$ %.2f", devuelta), "Venta",
					JOptionPane.INFORMATION_MESSAGE);
			carrito.clear();
			actualizarTablaCarrito();
			cargarComboProductos();
			txtRecibido.setText("");
		}
	}
}