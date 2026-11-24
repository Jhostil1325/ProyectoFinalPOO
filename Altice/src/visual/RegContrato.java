package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logico.Altice;
import logico.Cliente;
import logico.Comercial;
import logico.Contrato;
import logico.Plan;
import javax.swing.border.TitledBorder;
import java.awt.Font;

public class RegContrato extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTextField txtId;
	private JTextField txtCedula;
	private JTextField txtCliente;
	private JTextField txtDeuda;
	private JComboBox<String> cbxPlan;
	private JComboBox<String> cbxMetodoPago;
	private JTextField txtTotal;
	private JButton btnRegistrar;
	private Cliente clienteActual = null;
	private ArrayList<Plan> planesActivos = new ArrayList<>();

	public RegContrato()
	{
		setTitle("Registrar Contrato");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 428, 458);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 49, 422, 377);
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setLayout(null);
		getContentPane().add(panel);

		JLabel lblId = new JLabel("ID Contrato:");
		lblId.setBounds(30, 23, 100, 14);
		panel.add(lblId);

		txtId = new JTextField();
		txtId.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		txtId.setEditable(false);
		txtId.setForeground(new Color(0, 168, 232));
		txtId.setText(Altice.getInstance().generarIdContrato());
		txtId.setBounds(30, 40, 340, 25);
		panel.add(txtId);

		JLabel lblCedula = new JLabel("C\u00E9dula del Cliente:");
		lblCedula.setBounds(30, 78, 150, 14);
		panel.add(lblCedula);

		txtCedula = new JTextField();
		txtCedula.setBounds(28, 95, 225, 25);
		panel.add(txtCedula);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buscarCliente();
			}
		});
		btnBuscar.setBounds(285, 95, 85, 25);
		panel.add(btnBuscar);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(30, 133, 100, 14);
		panel.add(lblCliente);

		txtCliente = new JTextField();
		txtCliente.setEditable(false);
		txtCliente.setForeground(Color.BLACK);
		txtCliente.setBounds(30, 152, 200, 25);
		panel.add(txtCliente);

		JLabel lblDeuda = new JLabel("Deuda:");
		lblDeuda.setBounds(260, 133, 100, 14);
		panel.add(lblDeuda);

		txtDeuda = new JTextField();
		txtDeuda.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtDeuda.setEditable(false);
		txtDeuda.setForeground(Color.BLACK);
		txtDeuda.setBounds(260, 152, 110, 25);
		panel.add(txtDeuda);

		JLabel lblPlan = new JLabel("Plan:");
		lblPlan.setBounds(30, 190, 100, 14);
		panel.add(lblPlan);

		cbxPlan = new JComboBox<String>();
		cbxPlan.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				calcularTotal();
			}
		});
		cbxPlan.setBounds(28, 209, 342, 25);
		panel.add(cbxPlan);

		JLabel lblMetodo = new JLabel("M\u00E9todo de Pago:");
		lblMetodo.setBounds(30, 257, 150, 14);
		panel.add(lblMetodo);

		cbxMetodoPago = new JComboBox<String>();
		cbxMetodoPago.addItem("Efectivo");
		cbxMetodoPago.addItem("Tarjeta");
		cbxMetodoPago.addItem("Transferencia");
		cbxMetodoPago.setBounds(30, 284, 150, 25);
		panel.add(cbxMetodoPago);

		JLabel lblTotal = new JLabel("Total Mensual (+ ITBIS):");
		lblTotal.setBounds(207, 257, 176, 14);
		panel.add(lblTotal);

		txtTotal = new JTextField();
		txtTotal.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtTotal.setEditable(false);
		txtTotal.setForeground(Color.BLACK);
		txtTotal.setBounds(204, 284, 166, 25);
		panel.add(txtTotal);

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRegistrar.setEnabled(false);
		btnRegistrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				registrar();
			}
		});
		btnRegistrar.setBounds(80, 332, 100, 28);
		panel.add(btnRegistrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCancelar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnCancelar.setBounds(207, 332, 100, 28);
		panel.add(btnCancelar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(18, 18, 18));
		panel_1.setBounds(0, 0, 422, 49);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ALTICE | REGISTRAR CONTRATO");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setForeground(new Color(0, 168, 232));
		lblNewLabel.setBounds(12, 0, 398, 51);
		panel_1.add(lblNewLabel);

		cargarPlanes();
	}

	private void cargarPlanes()
	{
		cbxPlan.removeAllItems();
		planesActivos = Altice.getInstance().obtenerPlanesActivos();
		for (int i = 0; i < planesActivos.size(); i++)
		{
			Plan p = planesActivos.get(i);
			String servicios = "";
			if (p.getMisServicios() != null)
			{
				for (int j = 0; j < p.getMisServicios().size(); j++)
				{
					servicios += p.getMisServicios().get(j).getClass().getSimpleName();
					if (j < p.getMisServicios().size() - 1)
					{
						servicios += ", ";
					}
				}
			}
			cbxPlan.addItem(p.getId() + " (" + servicios + ") - RD$ " + String.format("%.2f", p.getPrecioMensual()));
		}
		calcularTotal();
	}

	private void calcularTotal()
	{
		int index = cbxPlan.getSelectedIndex();
		if (index >= 0 && index < planesActivos.size())
		{
			float subtotal = planesActivos.get(index).getPrecioMensual();
			float total = subtotal + (subtotal * 0.18f);
			txtTotal.setText(String.format("RD$ %.2f", total));
		}
		else
		{
			txtTotal.setText("");
		}
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
			txtCliente.setText("");
			txtDeuda.setText("");
			btnRegistrar.setEnabled(false);
			JOptionPane.showMessageDialog(this, "No existe un cliente con esa c\u00E9dula.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		txtCliente.setText(clienteActual.getNombre());
		txtDeuda.setText(String.format("RD$ %.2f", clienteActual.getDeuda()));

		if (clienteActual.getDeuda() > 0 || clienteActual.getFacturaPendientes() > 0)
		{
			btnRegistrar.setEnabled(false);
			JOptionPane.showMessageDialog(this, "El cliente tiene deuda pendiente. Debe saldar antes de contratar.",
					"Aviso", JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			btnRegistrar.setEnabled(true);
		}
	}

	private void registrar()
	{
		if (clienteActual == null)
		{
			return;
		}

		int index = cbxPlan.getSelectedIndex();
		if (index < 0 || index >= planesActivos.size())
		{
			JOptionPane.showMessageDialog(this, "Debe seleccionar un plan.", "Campos vac\u00EDos",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Plan plan = planesActivos.get(index);
		String metodo = cbxMetodoPago.getSelectedItem().toString();
		Comercial facilitador = null;

		if (Altice.getLoginUser() instanceof Comercial)
		{
			facilitador = (Comercial) Altice.getLoginUser();
		}

		Contrato contrato = Altice.getInstance().crearContrato(clienteActual, facilitador, plan, metodo);
		if (contrato != null)
		{
			JOptionPane.showMessageDialog(this, "Contrato registrado con \u00E9xito.", "Registro",
					JOptionPane.INFORMATION_MESSAGE);
			limpiar();
		}
		else
		{
			JOptionPane.showMessageDialog(this, "No se pudo registrar el contrato.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limpiar()
	{
		txtId.setText(Altice.getInstance().generarIdContrato());
		txtCedula.setText("");
		txtCliente.setText("");
		txtDeuda.setText("");
		clienteActual = null;
		btnRegistrar.setEnabled(false);
		txtCedula.requestFocus();
	}
}