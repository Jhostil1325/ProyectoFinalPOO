package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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

import logico.Administrativo;
import logico.Altice;
import logico.Comercial;
import logico.Empleado;

public class ListEmpleado extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;

	public ListEmpleado()
	{
		setTitle("Listar Empleados");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 460);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));

		JLabel lblTitulo = new JLabel("ALTICE | LISTADO DE EMPLEADOS");
		lblTitulo.setForeground(new Color(0, 168, 232));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelHeader.add(lblTitulo);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

		String[] columnas = { "ID", "Nombre", "C\u00E9dula", "Tel\u00E9fono", "Tipo", "Local", "Sueldo Base",
				"Comisi\u00F3n Mensual", "Detalle" };
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
		scroll.setBorder(new EmptyBorder(10, 15, 5, 15));
		getContentPane().add(scroll, BorderLayout.CENTER);

		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				modificarEmpleado();
			}
		});
		panelSur.add(btnModificar);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		panelSur.add(btnCerrar);
		getContentPane().add(panelSur, BorderLayout.SOUTH);

		cargarEmpleados();
	}

	private void cargarEmpleados()
	{
		model.setRowCount(0);
		for (int i = 0; i < Altice.getInstance().getEmpleados().size(); i++)
		{
			Empleado emp = Altice.getInstance().getEmpleados().get(i);
			Object[] fila = new Object[9];
			fila[0] = emp.getId();
			fila[1] = emp.getNombre();
			fila[2] = emp.getCedula();
			fila[3] = emp.getTelefono();
			fila[4] = Altice.getInstance().obtenerTipoPersona(emp);
			fila[5] = emp.getMiLocal();
			fila[6] = String.format("RD$ %.2f", emp.getSueldobase());
			float comision = 0.0f;
			if (emp instanceof Comercial)
			{
				comision = Altice.getInstance().calcularComisionMensual((Comercial) emp);
			}
			fila[7] = String.format("RD$ %.2f", comision);

			if (emp instanceof Comercial)
			{
				fila[8] = "Meta: " + String.format("RD$ %.2f", ((Comercial) emp).getMetaVentas());
			}
			else if (emp instanceof Administrativo)
			{
				fila[8] = "Cargo: " + ((Administrativo) emp).getCargo();
			}

			if (Altice.getInstance().esAdminBase(emp))
			{
				fila[2] = "";
				fila[3] = "";
				fila[5] = "";
				fila[6] = "";
				fila[7] = "";
				fila[8] = "";
			}
			model.addRow(fila);
		}
	}

	private void modificarEmpleado()
	{
		int row = table.getSelectedRow();
		if (row < 0)
		{
			JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Empleado emp = Altice.getInstance().getEmpleados().get(row);
		if (Altice.getInstance().esAdminBase(emp))
		{
			JOptionPane.showMessageDialog(this, "El administrador base no se puede modificar.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		JTextField txtNombre = new JTextField(emp.getNombre());
		JTextField txtCedula = new JTextField(emp.getCedula());
		JTextField txtTelefono = new JTextField(emp.getTelefono());
		JTextField txtDireccion = new JTextField(emp.getDireccion());
		JTextField txtSueldo = new JTextField(String.valueOf(emp.getSueldobase()));
		JTextField txtLocal = new JTextField(emp.getMiLocal());
		JTextField txtExtra = new JTextField();
		String etiquetaExtra = "Detalle:";

		if (emp instanceof Comercial)
		{
			etiquetaExtra = "Meta de ventas:";
			txtExtra.setText(String.valueOf(((Comercial) emp).getMetaVentas()));
		}
		else if (emp instanceof Administrativo)
		{
			etiquetaExtra = "Cargo:";
			txtExtra.setText(((Administrativo) emp).getCargo());
		}
		else
		{
			txtExtra.setEnabled(false);
		}

		JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
		panel.add(new JLabel("ID:"));
		panel.add(new JLabel(emp.getId()));
		panel.add(new JLabel("Nombre:"));
		panel.add(txtNombre);
		panel.add(new JLabel("C\u00E9dula:"));
		panel.add(txtCedula);
		panel.add(new JLabel("Tel\u00E9fono:"));
		panel.add(txtTelefono);
		panel.add(new JLabel("Direcci\u00F3n:"));
		panel.add(txtDireccion);
		panel.add(new JLabel("Sueldo base:"));
		panel.add(txtSueldo);
		panel.add(new JLabel("Local:"));
		panel.add(txtLocal);
		panel.add(new JLabel(etiquetaExtra));
		panel.add(txtExtra);

		while (true)
		{
			int opcion = JOptionPane.showConfirmDialog(this, panel, "Modificar Empleado",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (opcion != JOptionPane.OK_OPTION)
			{
				return;
			}

			String nombre = txtNombre.getText().trim();
			String cedula = txtCedula.getText().trim();
			String telefono = txtTelefono.getText().trim();
			String direccion = txtDireccion.getText().trim();
			String local = txtLocal.getText().trim();
			String extra = txtExtra.getText().trim();

			if (nombre.isEmpty() || cedula.isEmpty() || telefono.isEmpty() || direccion.isEmpty()
					|| local.isEmpty() || txtSueldo.getText().trim().isEmpty()
					|| (txtExtra.isEnabled() && extra.isEmpty()))
			{
				JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Campos vac\u00EDos",
						JOptionPane.WARNING_MESSAGE);
				continue;
			}

			float sueldo = 0.0f;
			float meta = 0.0f;
			try
			{
				sueldo = Float.parseFloat(txtSueldo.getText().trim().replace(",", "."));
				if (emp instanceof Comercial)
				{
					meta = Float.parseFloat(extra.replace(",", "."));
				}
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(this, "El sueldo y la meta deben ser n\u00FAmeros v\u00E1lidos.",
						"Error", JOptionPane.ERROR_MESSAGE);
				continue;
			}

			if (sueldo < 0 || meta < 0)
			{
				JOptionPane.showMessageDialog(this, "El sueldo y la meta no pueden ser negativos.", "Error",
						JOptionPane.ERROR_MESSAGE);
				continue;
			}

			if (!Altice.getInstance().modificarEmpleado(emp, nombre, cedula, direccion, telefono, sueldo, local))
			{
				JOptionPane.showMessageDialog(this, "Ya existe otra persona registrada con esa c\u00E9dula.",
						"Error", JOptionPane.ERROR_MESSAGE);
				continue;
			}

			if (emp instanceof Comercial)
			{
				((Comercial) emp).setMetaVentas(meta);
			}
			else if (emp instanceof Administrativo)
			{
				((Administrativo) emp).setCargo(extra);
			}

			JOptionPane.showMessageDialog(this, "Empleado modificado con \u00E9xito.", "Modificar",
					JOptionPane.INFORMATION_MESSAGE);
			cargarEmpleados();
			return;
		}
	}
}
