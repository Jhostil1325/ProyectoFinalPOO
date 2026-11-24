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
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.border.LineBorder;

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
		setBounds(100, 100, 425, 484);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(18, 18, 18));
		contentPane.setForeground(Color.DARK_GRAY);
		contentPane.setToolTipText("Inicio de Sesi\u00F3n");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(18, 18, 18));
		panel.setBounds(12, 218, 392, 218);
		panel.setBorder(new LineBorder(new Color(18, 18, 18)));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblUsuario.setBounds(153, 13, 100, 25);
		panel.add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setForeground(new Color(0, 0, 255));
		txtUsuario.setBounds(45, 48, 300, 25);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblContrasena = new JLabel("Contrase\u00F1a:");
		lblContrasena.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblContrasena.setForeground(new Color(255, 255, 255));
		lblContrasena.setBounds(133, 86, 130, 25);
		panel.add(lblContrasena);

		txtContrasena = new JPasswordField();
		txtContrasena.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				iniciarSesion();
			}
		});
		txtContrasena.setBounds(45, 117, 300, 25);
		panel.add(txtContrasena);

		JButton btnLogin = new JButton("Iniciar Sesi\u00F3n");                      
		btnLogin.setBackground(new Color(255, 255, 255));  
		btnLogin.setForeground(new Color(0, 168, 232));             
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 17));
		btnLogin.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        iniciarSesion();
		    }
		});
		btnLogin.setBounds(122, 166, 154, 39);
		panel.add(btnLogin);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(44, 13, 325, 208);
		contentPane.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\esthe\\OneDrive - Pontificia Universidad Cat\u00F3lica Madre y Maestra\\Desktop\\Altice logo.png"));
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