package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Cliente;

public class ListCliente extends JDialog
{
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private JTable table;

	public ListCliente()
	{
		setTitle("Listar Clientes");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 460);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		String[] columnas = { "ID", "Nombre", "C\u00E9dula", "Direcci\u00F3n", "Tel\u00E9fono", "Deuda", "Estado" };
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
		scroll.setBorder(new EmptyBorder(10, 10, 5, 10));
		getContentPane().add(scroll, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				modificarCliente();
			}
		});
		panelBotones.add(btnModificar);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		panelBotones.add(btnCerrar);
		getContentPane().add(panelBotones, BorderLayout.SOUTH);

		cargarClientes();
	}

	private void cargarClientes()
	{
		model.setRowCount(0);
		for (Cliente c : Altice.getInstance().getClientes())
		{
			Object[] fila = new Object[7];
			fila[0] = c.getId();
			fila[1] = c.getNombre();
			fila[2] = c.getCedula();
			fila[3] = c.getDireccion();
			fila[4] = c.getTelefono();
			fila[5] = c.getDeuda();
			fila[6] = c.isActivo() ? "Activo" : "Inactivo";
			model.addRow(fila);
		}
	}

	private void modificarCliente()
	{
		int row = table.getSelectedRow();
		if (row < 0)
		{
			JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Cliente c = Altice.getInstance().getClientes().get(row);

		JTextField txtNombre = new JTextField(c.getNombre());
		JTextField txtCedula = new JTextField(c.getCedula());
		JTextField txtDireccion = new JTextField(c.getDireccion());
		JTextField txtTelefono = new JTextField(c.getTelefono());

		JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
		panel.add(new JLabel("ID:"));
		panel.add(new JLabel(c.getId()));
		panel.add(new JLabel("Nombre:"));
		panel.add(txtNombre);
		panel.add(new JLabel("C\u00E9dula:"));
		panel.add(txtCedula);
		panel.add(new JLabel("Direcci\u00F3n:"));
		panel.add(txtDireccion);
		panel.add(new JLabel("Tel\u00E9fono:"));
		panel.add(txtTelefono);

		while (true)
		{
			int opcion = JOptionPane.showConfirmDialog(this, panel, "Modificar Cliente",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (opcion != JOptionPane.OK_OPTION)
			{
				return;
			}

			String nombre = txtNombre.getText().trim();
			String cedula = txtCedula.getText().trim();
			String direccion = txtDireccion.getText().trim();
			String telefono = txtTelefono.getText().trim();

			if (nombre.isEmpty() || cedula.isEmpty() || direccion.isEmpty() || telefono.isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Campos vac\u00EDos",
						JOptionPane.WARNING_MESSAGE);
				continue;
			}

			if (!Altice.getInstance().modificarCliente(c, nombre, cedula, direccion, telefono))
			{
				JOptionPane.showMessageDialog(this, "Ya existe otra persona registrada con esa c\u00E9dula.",
						"Error", JOptionPane.ERROR_MESSAGE);
				continue;
			}

			JOptionPane.showMessageDialog(this, "Cliente modificado con \u00E9xito.", "Modificar",
					JOptionPane.INFORMATION_MESSAGE);
			cargarClientes();
			return;
		}
	}
}