package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Cliente;
import logico.Comercial;
import logico.Pago;

public class Facturacion extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTextField txtCedula;
	private JTextField txtCliente;
	private JTextField txtEstado;
	private JTextField txtDeudaTotal;
	private JTable table;
	private DefaultTableModel model;
	private JRadioButton rdbtnMasAntigua;
	private JRadioButton rdbtnTodas;
	private JComboBox<String> cbxMetodoPago;
	private JTextField txtTotalPagar;
	private JTextField txtMontoRecibido;
	private JTextField txtDevuelta;
	private JButton btnProcesar;

	private Cliente clienteActual = null;
	private ArrayList<Pago> facturasPendientes = new ArrayList<>();
	private float totalCalculado = 0.0f;

	public Facturacion()
	{
		setTitle("Facturaci\u00F3n");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 689, 520);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblCedula = new JLabel("C\u00E9dula del Cliente:");
		lblCedula.setBounds(25, 20, 140, 14);
		panel.add(lblCedula);

		txtCedula = new JTextField();
		txtCedula.setBounds(25, 40, 210, 25);
		panel.add(txtCedula);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buscarCliente();
			}
		});
		btnBuscar.setBounds(245, 40, 90, 25);
		panel.add(btnBuscar);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(25, 80, 100, 14);
		panel.add(lblCliente);

		txtCliente = new JTextField();
		txtCliente.setEditable(false);
		txtCliente.setForeground(Color.BLACK);
		txtCliente.setBounds(25, 100, 270, 25);
		panel.add(txtCliente);

		JLabel lblEstado = new JLabel("Estado Servicio:");
		lblEstado.setBounds(310, 80, 120, 14);
		panel.add(lblEstado);

		txtEstado = new JTextField();
		txtEstado.setEditable(false);
		txtEstado.setForeground(Color.BLACK);
		txtEstado.setBounds(310, 100, 150, 25);
		panel.add(txtEstado);

		JLabel lblDeuda = new JLabel("Deuda Acumulada:");
		lblDeuda.setBounds(475, 80, 130, 14);
		panel.add(lblDeuda);

		txtDeudaTotal = new JTextField();
		txtDeudaTotal.setEditable(false);
		txtDeudaTotal.setForeground(Color.BLACK);
		txtDeudaTotal.setBounds(475, 100, 170, 25);
		panel.add(txtDeudaTotal);

		String[] columnas = { "ID Factura", "Fecha Emisi\u00F3n", "Contrato / Plan", "ITBIS (18%)", "Total" };
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
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(25, 140, 620, 165);
		panel.add(scroll);

		rdbtnMasAntigua = new JRadioButton("Pagar factura m\u00E1s antigua");
		rdbtnMasAntigua.setSelected(true);
		rdbtnMasAntigua.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				calcularMontoAPagar();
			}
		});
		rdbtnMasAntigua.setBounds(25, 315, 240, 23);
		panel.add(rdbtnMasAntigua);

		rdbtnTodas = new JRadioButton("Pagar todas las facturas pendientes");
		rdbtnTodas.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				calcularMontoAPagar();
			}
		});
		rdbtnTodas.setBounds(280, 315, 250, 23);
		panel.add(rdbtnTodas);

		ButtonGroup grupo = new ButtonGroup();
		grupo.add(rdbtnMasAntigua);
		grupo.add(rdbtnTodas);

		JLabel lblMetodo = new JLabel("M\u00E9todo de Pago:");
		lblMetodo.setBounds(25, 355, 120, 14);
		panel.add(lblMetodo);

		cbxMetodoPago = new JComboBox<String>();
		cbxMetodoPago.addItem("Efectivo");
		cbxMetodoPago.addItem("Tarjeta");
		cbxMetodoPago.addItem("Transferencia");
		cbxMetodoPago.setBounds(25, 375, 140, 25);
		panel.add(cbxMetodoPago);

		JLabel lblTotalPagar = new JLabel("Total a Pagar:");
		lblTotalPagar.setBounds(180, 355, 120, 14);
		panel.add(lblTotalPagar);

		txtTotalPagar = new JTextField();
		txtTotalPagar.setEditable(false);
		txtTotalPagar.setForeground(Color.BLACK);
		txtTotalPagar.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtTotalPagar.setBounds(180, 375, 140, 25);
		panel.add(txtTotalPagar);

		JLabel lblRecibido = new JLabel("Monto Recibido:");
		lblRecibido.setBounds(335, 355, 120, 14);
		panel.add(lblRecibido);

		txtMontoRecibido = new JTextField();
		txtMontoRecibido.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				calcularDevuelta();
			}
		});
		txtMontoRecibido.setBounds(335, 375, 145, 25);
		panel.add(txtMontoRecibido);

		JLabel lblDevuelta = new JLabel("Devuelta:");
		lblDevuelta.setBounds(495, 355, 120, 14);
		panel.add(lblDevuelta);

		txtDevuelta = new JTextField();
		txtDevuelta.setEditable(false);
		txtDevuelta.setForeground(Color.BLACK);
		txtDevuelta.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtDevuelta.setBounds(495, 375, 150, 25);
		panel.add(txtDevuelta);

		btnProcesar = new JButton("Procesar Pago");
		btnProcesar.setEnabled(false);
		btnProcesar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				procesarPago();
			}
		});
		btnProcesar.setBounds(200, 435, 130, 30);
		panel.add(btnProcesar);

		JButton btnCancelar = new JButton("Cerrar");
		btnCancelar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnCancelar.setBounds(345, 435, 120, 30);
		panel.add(btnCancelar);
	}

	private void buscarCliente()
	{
		String cedula = txtCedula.getText().trim();
		if (cedula.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Debe ingresar una c\u00E9dula.", "Campos vac\u00EDos",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		clienteActual = Altice.getInstance().buscarClienteCed(cedula);
		if (clienteActual == null)
		{
			limpiarCamposCliente();
			JOptionPane.showMessageDialog(this, "No existe un cliente con esa c\u00E9dula.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		cargarDatosCliente();
	}

	private void cargarDatosCliente()
	{
		txtCliente.setText(clienteActual.getNombre());
		txtEstado.setText(clienteActual.isActivo() ? "Activo" : "Suspendido");
		txtDeudaTotal.setText(String.format("RD$ %.2f", clienteActual.getDeuda()));

		model.setRowCount(0);
		facturasPendientes = Altice.getInstance().obtenerFacturasPendientesCliente(clienteActual);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		for (int i = 0; i < facturasPendientes.size(); i++)
		{
			Pago p = facturasPendientes.get(i);
			Object[] fila = new Object[5];
			fila[0] = p.getId();
			fila[1] = sdf.format(p.getFechaPago());
			if (p.getMiContrato() != null)
			{
				fila[2] = p.getMiContrato().getId() + " (" + p.getMiContrato().getMiPlan().getId() + ")";
			}
			else
			{
				fila[2] = "Venta / Servicio";
			}
			fila[3] = String.format("RD$ %.2f", p.getItbis());
			fila[4] = String.format("RD$ %.2f", p.getTotalPago());
			model.addRow(fila);
		}

		calcularMontoAPagar();
	}

	private void calcularMontoAPagar()
	{
		totalCalculado = 0.0f;
		txtMontoRecibido.setText("");
		txtDevuelta.setText("");

		if (facturasPendientes.isEmpty())
		{
			txtTotalPagar.setText("RD$ 0.00");
			btnProcesar.setEnabled(false);
			return;
		}

		if (rdbtnMasAntigua.isSelected())
		{
			totalCalculado = facturasPendientes.get(0).getTotalPago();
		}
		else
		{
			for (int i = 0; i < facturasPendientes.size(); i++)
			{
				totalCalculado += facturasPendientes.get(i).getTotalPago();
			}
		}

		txtTotalPagar.setText(String.format("RD$ %.2f", totalCalculado));
		btnProcesar.setEnabled(true);
	}

	private void calcularDevuelta()
	{
		String texto = txtMontoRecibido.getText().trim();
		if (texto.isEmpty())
		{
			txtDevuelta.setText("");
			return;
		}

		try
		{
			float recibido = Float.parseFloat(texto);
			if (recibido < totalCalculado)
			{
				txtDevuelta.setText("Monto insuficiente");
			}
			else
			{
				txtDevuelta.setText(String.format("RD$ %.2f", recibido - totalCalculado));
			}
		}
		catch (NumberFormatException ex)
		{
			txtDevuelta.setText("Inv\u00E1lido");
		}
	}

	private void procesarPago()
	{
		if (clienteActual == null || facturasPendientes.isEmpty())
		{
			return;
		}

		float montoRecibido = 0.0f;
		try
		{
			montoRecibido = Float.parseFloat(txtMontoRecibido.getText().trim());
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(this, "Ingrese un monto recibido v\u00E1lido.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (montoRecibido < totalCalculado)
		{
			JOptionPane.showMessageDialog(this, "El monto recibido es menor al total a pagar.", "Pago insuficiente",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		ArrayList<Pago> aPagar = new ArrayList<>();
		if (rdbtnMasAntigua.isSelected())
		{
			aPagar.add(facturasPendientes.get(0));
		}
		else
		{
			for (int i = 0; i < facturasPendientes.size(); i++)
			{
				aPagar.add(facturasPendientes.get(i));
			}
		}

		String metodo = cbxMetodoPago.getSelectedItem().toString();
		Comercial facilitador = null;
		if (Altice.getLoginUser() instanceof Comercial)
		{
			facilitador = (Comercial) Altice.getLoginUser();
		}

		float devuelta = Altice.getInstance().procesarCobroFacturas(clienteActual, facilitador, aPagar, metodo,
				montoRecibido);
		if (devuelta >= 0.0f)
		{
			txtDevuelta.setText(String.format("RD$ %.2f", devuelta));
			JOptionPane.showMessageDialog(this,
					String.format("Pago realizado con \u00E9xito.\nDevuelta: RD$ %.2f", devuelta), "Caja",
					JOptionPane.INFORMATION_MESSAGE);
			cargarDatosCliente();
		}
	}

	private void limpiarCamposCliente()
	{
		txtCliente.setText("");
		txtEstado.setText("");
		txtDeudaTotal.setText("");
		txtTotalPagar.setText("");
		txtMontoRecibido.setText("");
		txtDevuelta.setText("");
		model.setRowCount(0);
		facturasPendientes.clear();
		btnProcesar.setEnabled(false);
	}
}