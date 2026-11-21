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
}
