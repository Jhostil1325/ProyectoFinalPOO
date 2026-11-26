package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logico.Altice;
import logico.Persona;
import java.awt.Font;

public class MiUsuario extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtCedula;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private JCheckBox chckbxMostrar;
	private Persona usuarioActual;

	public MiUsuario()
	{
		getContentPane().setBackground(new Color(18, 18, 18));
		setTitle("Mi Usuario");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 439, 447);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(12, 53, 413, 346);
		panel.setBackground(new Color(18, 18, 18));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		getContentPane().add(panel);

		usuarioActual = Altice.getLoginUser();

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setBackground(new Color(255, 255, 255));
		lblNombre.setBounds(40, 20, 100, 14);
		panel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtNombre.setEditable(false);
		txtNombre.setForeground(Color.BLACK);
		txtNombre.setBounds(40, 40, 320, 25);
		panel.add(txtNombre);

		JLabel lblCedula = new JLabel("C\u00E9dula:");
		lblCedula.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCedula.setForeground(new Color(255, 255, 255));
		lblCedula.setBounds(40, 75, 100, 14);
		panel.add(lblCedula);

		txtCedula = new JTextField();
		txtCedula.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtCedula.setEditable(false);
		txtCedula.setForeground(Color.BLACK);
		txtCedula.setBounds(40, 95, 320, 25);
		panel.add(txtCedula);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setBounds(40, 130, 100, 14);
		panel.add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(40, 150, 320, 25);
		panel.add(txtUsuario);

		JLabel lblPassword = new JLabel("Contrase\u00F1a:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPassword.setForeground(new Color(255, 255, 255));
		lblPassword.setBounds(40, 185, 100, 14);
		panel.add(lblPassword);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(40, 205, 320, 25);
		panel.add(txtPassword);

		chckbxMostrar = new JCheckBox("Mostrar contrase\u00F1a");
		chckbxMostrar.setBackground(new Color(18, 18, 18));
		chckbxMostrar.setForeground(new Color(255, 255, 255));
		chckbxMostrar.setFont(new Font("Tahoma", Font.BOLD, 13));
		chckbxMostrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (chckbxMostrar.isSelected())
				{
					txtPassword.setEchoChar((char) 0);
				}
				else
				{
					txtPassword.setEchoChar('\u2022');
				}
			}
		});
		chckbxMostrar.setBounds(40, 240, 180, 23);
		panel.add(chckbxMostrar);

		JButton btnGuardar = new JButton("Guardar Cambios");
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnGuardar.setBackground(new Color(0, 136, 255));
		btnGuardar.setForeground(new Color(255, 255, 255));
		btnGuardar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				guardarCambios();
			}
		});
		btnGuardar.setBounds(40, 290, 180, 28);
		panel.add(btnGuardar);

		JButton btnCancelar = new JButton("Cerrar");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCancelar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnCancelar.setBounds(245, 290, 115, 28);
		panel.add(btnCancelar);
		
		String nombreCliente = (usuarioActual != null) ? usuarioActual.getNombre().toUpperCase() : "CLIENTE";
		JLabel lblNewLabel = new JLabel("ALTICE | USUARIO: " + nombreCliente);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setForeground(new Color(0, 168, 232));
		lblNewLabel.setBounds(36, 13, 370, 32);
		getContentPane().add(lblNewLabel);
		
		cargarDatos();
	}

	private void cargarDatos()
	{
		if (usuarioActual != null)
		{
			txtNombre.setText(usuarioActual.getNombre());
			txtCedula.setText(usuarioActual.getCedula());
			txtUsuario.setText(usuarioActual.getUsuario());
			txtPassword.setText(usuarioActual.getPassword());
		}
	}

	private void guardarCambios()
	{
		if (usuarioActual == null)
		{
			return;
		}

		String nuevoUsuario = txtUsuario.getText().trim();
		String nuevaPassword = new String(txtPassword.getPassword()).trim();

		if (nuevoUsuario.isEmpty() || nuevaPassword.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "El usuario y la contrase\u00F1a no pueden estar vac\u00EDos.",
					"Campos vac\u00EDos", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (!nuevoUsuario.equalsIgnoreCase(usuarioActual.getUsuario())
				&& Altice.getInstance().existeUsuario(nuevoUsuario))
		{
			JOptionPane.showMessageDialog(this, "Ese nombre de usuario ya est\u00E1 en uso por otra persona.",
					"Usuario duplicado", JOptionPane.ERROR_MESSAGE);
			return;
		}

		usuarioActual.setUsuario(nuevoUsuario);
		usuarioActual.setPassword(nuevaPassword);
		guardarArchivo();

		JOptionPane.showMessageDialog(this, "Usuario y contrase\u00F1a actualizados con \u00E9xito.", "Guardado",
				JOptionPane.INFORMATION_MESSAGE);
		dispose();
	}

	private void guardarArchivo()
	{
		FileOutputStream archivo;
		ObjectOutputStream salida;
		try
		{
			archivo = new FileOutputStream("altice.dat");
			salida = new ObjectOutputStream(archivo);
			salida.writeObject(Altice.getInstance());
			salida.close();
			archivo.close();
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}
}