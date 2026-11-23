package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Administrativo;
import logico.Altice;
import logico.Comercial;
import logico.Empleado;

public class ListEmpleado extends JDialog
{
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private JTable table;

	public ListEmpleado()
	{
		setTitle("Listar Empleados");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 460);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

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
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(new EmptyBorder(10, 10, 5, 10));
		getContentPane().add(scroll, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
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

			if (emp.getUsuario() != null && emp.getUsuario().equalsIgnoreCase("Admin"))
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
}