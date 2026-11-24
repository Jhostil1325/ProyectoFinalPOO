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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Persona;

public class ListUsuario extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;

	public ListUsuario()
	{
		setTitle("Listar Usuarios");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 420);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));

		JLabel lblTitulo = new JLabel("ALTICE | LISTADO DE USUARIOS");
		lblTitulo.setForeground(new Color(0, 168, 232));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelHeader.add(lblTitulo);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

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
				modificarUsuario();
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

	private void modificarUsuario()
	{
		int row = table.getSelectedRow();
		if (row < 0)
		{
			JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Persona p = Altice.getInstance().getPersonasConUsuario().get(row);

		JTextField txtUsuario = new JTextField(p.getUsuario());
		if (Altice.getInstance().esAdminBase(p))
		{
			txtUsuario.setEnabled(false);
		}
		JPasswordField txtPassword = new JPasswordField();
		JPasswordField txtConfirmar = new JPasswordField();

		JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
		panel.add(new JLabel("Nombre:"));
		panel.add(new JLabel(p.getNombre()));
		panel.add(new JLabel("Tipo:"));
		panel.add(new JLabel(Altice.getInstance().obtenerTipoPersona(p)));
		panel.add(new JLabel("Usuario:"));
		panel.add(txtUsuario);
		panel.add(new JLabel("Nueva contrase\u00F1a:"));
		panel.add(txtPassword);
		panel.add(new JLabel("Confirmar contrase\u00F1a:"));
		panel.add(txtConfirmar);
		panel.add(new JLabel(""));
		panel.add(new JLabel("(vac\u00EDo = no cambiar)"));

		while (true)
		{
			int opcion = JOptionPane.showConfirmDialog(this, panel, "Modificar Usuario",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (opcion != JOptionPane.OK_OPTION)
			{
				return;
			}

			String usuario = txtUsuario.getText().trim();
			String password = new String(txtPassword.getPassword());
			String confirmar = new String(txtConfirmar.getPassword());

			if (usuario.isEmpty())
			{
				JOptionPane.showMessageDialog(this, "El usuario no puede estar vac\u00EDo.", "Campos vac\u00EDos",
						JOptionPane.WARNING_MESSAGE);
				continue;
			}

			if (!password.equals(confirmar))
			{
				JOptionPane.showMessageDialog(this, "Las contrase\u00F1as no coinciden.", "Error",
						JOptionPane.ERROR_MESSAGE);
				continue;
			}

			if (!Altice.getInstance().modificarUsuario(p, usuario, password))
			{
				JOptionPane.showMessageDialog(this, "Ese nombre de usuario ya est\u00E1 en uso.", "Error",
						JOptionPane.ERROR_MESSAGE);
				continue;
			}

			JOptionPane.showMessageDialog(this, "Usuario modificado con \u00E9xito.", "Modificar",
					JOptionPane.INFORMATION_MESSAGE);
			cargarUsuarios();
			return;
		}
	}
}
