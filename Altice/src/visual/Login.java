package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logico.Administrativo;
import logico.Altice;
import logico.Persona;
import logico.Producto;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;

public class Login extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtContrasena;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				FileInputStream empresa;
				FileOutputStream empresa2;
				ObjectInputStream empresaRead;
				ObjectOutputStream empresaWrite;
				try
				{
					empresa = new FileInputStream("altice.dat");
					empresaRead = new ObjectInputStream(empresa);
					Altice temp = (Altice) empresaRead.readObject();
					Altice.setAltice(temp);
					Producto.setCont(Altice.getInstance().getMisProductos().size());
					empresa.close();
					empresaRead.close();
				}
				catch (FileNotFoundException e)
				{
					try
					{
						empresa2 = new FileOutputStream("altice.dat");
						empresaWrite = new ObjectOutputStream(empresa2);

						Administrativo admin = new Administrativo("EMP-1", "Administrador General", "000-0000000-0",
								"Sede Central", "809-000-0000", "Admin", "Admin", 50000f, 0f, 0f, "Sede Central",
								"Gerente");

						Altice.getInstance().regPersona(admin);
						empresaWrite.writeObject(Altice.getInstance());
						empresa2.close();
						empresaWrite.close();
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
				catch (IOException e)
				{
					e.printStackTrace();
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}

				if (!Altice.getInstance().existeUsuario("Admin"))
				{
					Persona existente = Altice.getInstance().buscarPersonaCed("000-0000000-0");
					if (existente != null)
					{
						Altice.getInstance().asignarUsuario(existente, "Admin", "Admin");
					}
					else
					{
						Administrativo admin = new Administrativo(Altice.getInstance().generarIdEmpleado(),
								"Administrador General", "000-0000000-0", "Sede Central", "809-000-0000", "Admin",
								"Admin", 50000f, 0f, 0f, "Sede Central", "Gerente");
						Altice.getInstance().regPersona(admin);
					}
				}

				try
				{
					Login frame = new Login();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public Login()
	{
		setTitle("Altice - Inicio de Sesi\u00F3n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 425, 291);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setToolTipText("Inicio de Sesi\u00F3n");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(12, 13, 392, 230);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Inicio de Sesi\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(45, 35, 100, 14);
		panel.add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(45, 58, 300, 25);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblContrasena = new JLabel("Contrase\u00F1a:");
		lblContrasena.setBounds(45, 100, 105, 14);
		panel.add(lblContrasena);

		txtContrasena = new JPasswordField();
		txtContrasena.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				iniciarSesion();
			}
		});
		txtContrasena.setBounds(45, 123, 300, 25);
		panel.add(txtContrasena);

		JButton btnLogin = new JButton("Iniciar Sesi\u00F3n");
		btnLogin.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				iniciarSesion();
			}
		});
		btnLogin.setBounds(127, 176, 130, 30);
		panel.add(btnLogin);
	}

	private void iniciarSesion()
	{
		String user = txtUsuario.getText().trim();
		String pass = new String(txtContrasena.getPassword());

		if (Altice.getInstance().confirmLogin(user, pass))
		{
			Principal frame = new Principal();
			dispose();
			frame.setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Usuario o contrase\u00F1a incorrectos.", "Error de acceso",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}