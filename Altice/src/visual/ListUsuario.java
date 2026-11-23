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
import logico.Persona;

public class ListUsuario extends JDialog
{
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private JTable table;

	public ListUsuario()
	{
		setTitle("Listar Usuarios");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 420);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		String[] columnas = { "Usuario", "Nombre", "C\u00E9dula", "Tipo" };
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

		cargarUsuarios();
	}

	private void cargarUsuarios()
	{
		model.setRowCount(0);
		for (int i = 0; i < Altice.getInstance().getPersonasConUsuario().size(); i++)
		{
			Persona p = Altice.getInstance().getPersonasConUsuario().get(i);
			Object[] fila = new Object[4];
			fila[0] = p.getUsuario();
			fila[1] = p.getNombre();
			fila[2] = p.getCedula();
			fila[3] = Altice.getInstance().obtenerTipoPersona(p);
			model.addRow(fila);
		}
	}
}