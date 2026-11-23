package visual;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logico.Altice;
import logico.Persona;

public class RegUsuario extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTextField txtCedula;
	private JTextField txtNombre;
	private JTextField txtTipo;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private JPasswordField txtConfirmar;
	private JButton btnCrear;
	private Persona personaActual = null;

	public RegUsuario()
	{
		setTitle("Crear Usuario");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 420, 460);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblCedula = new JLabel("C\u00E9dula:");
		lblCedula.setBounds(30, 20, 180, 14);
		panel.add(lblCedula);

		txtCedula = new JTextField();
		txtCedula.setBounds(30, 40, 240, 25);
		panel.add(txtCedula);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buscarPersona();
			}
		});
		btnBuscar.setBounds(280, 40, 90, 25);
		panel.add(btnBuscar);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(30, 80, 100, 14);
		panel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setBounds(30, 100, 340, 25);
		panel.add(txtNombre);

		JLabel lblTipo = new JLabel("Tipo de usuario:");
		lblTipo.setBounds(30, 135, 150, 14);
		panel.add(lblTipo);

		txtTipo = new JTextField();
		txtTipo.setEditable(false);
		txtTipo.setBounds(30, 155, 340, 25);
		panel.add(txtTipo);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(30, 190, 100, 14);
		panel.add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(30, 210, 340, 25);
		panel.add(txtUsuario);

		JLabel lblPassword = new JLabel("Contrase\u00F1a:");
		lblPassword.setBounds(30, 245, 100, 14);
		panel.add(lblPassword);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(30, 265, 340, 25);
		panel.add(txtPassword);

		JLabel lblConfirmar = new JLabel("Confirmar contrase\u00F1a:");
		lblConfirmar.setBounds(30, 300, 150, 14);
		panel.add(lblConfirmar);

		txtConfirmar = new JPasswordField();
		txtConfirmar.setBounds(30, 320, 340, 25);
		panel.add(txtConfirmar);

		btnCrear = new JButton("Crear");
		btnCrear.setEnabled(false);
		btnCrear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				crearUsuario();
			}
		});
		btnCrear.setBounds(95, 375, 100, 28);
		panel.add(btnCrear);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnCerrar.setBounds(210, 375, 100, 28);
		panel.add(btnCerrar);
	}

	private void buscarPersona()
	{
		String cedula = txtCedula.getText().trim();
		if (cedula.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Debe ingresar una c\u00E9dula.", "Campos vac\u00EDos",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		personaActual = Altice.getInstance().buscarPersonaCed(cedula);
		if (personaActual == null)
		{
			limpiarPersona();
			JOptionPane.showMessageDialog(this, "No existe una persona registrada con esa c\u00E9dula.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		txtNombre.setText(personaActual.getNombre());
		txtTipo.setText(Altice.getInstance().obtenerTipoPersona(personaActual));

		if (personaActual.getUsuario() != null && !personaActual.getUsuario().trim().isEmpty())
		{
			btnCrear.setEnabled(false);
			JOptionPane.showMessageDialog(this,
					"Esta persona ya tiene un usuario asignado (" + personaActual.getUsuario() + ").", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			btnCrear.setEnabled(true);
		}
	}

	private void crearUsuario()
	{
		String usuario = txtUsuario.getText().trim();
		String password = new String(txtPassword.getPassword());
		String confirmar = new String(txtConfirmar.getPassword());

		if (personaActual == null)
		{
			return;
		}

		if (usuario.isEmpty() || password.isEmpty() || confirmar.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Campos vac\u00EDos",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (!password.equals(confirmar))
		{
			JOptionPane.showMessageDialog(this, "Las contrase\u00F1as no coinciden.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (Altice.getInstance().asignarUsuario(personaActual, usuario, password))
		{
			JOptionPane.showMessageDialog(this, "Usuario creado con \u00E9xito.", "Usuario",
					JOptionPane.INFORMATION_MESSAGE);
			txtCedula.setText("");
			limpiarPersona();
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Ese nombre de usuario ya est\u00E1 en uso.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limpiarPersona()
	{
		personaActual = null;
		txtNombre.setText("");
		txtTipo.setText("");
		txtUsuario.setText("");
		txtPassword.setText("");
		txtConfirmar.setText("");
		btnCrear.setEnabled(false);
	}
}