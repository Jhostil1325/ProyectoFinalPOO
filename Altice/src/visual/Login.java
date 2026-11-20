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
import java.util.Date;

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
import logico.Usuario;

public class Login extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtContrasena;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				FileInputStream empresa;
				FileOutputStream empresa2;
				ObjectInputStream empresaRead;
				ObjectOutputStream empresaWrite;
				try {
					empresa = new FileInputStream("altice.dat");
					empresaRead = new ObjectInputStream(empresa);
					Altice temp = (Altice) empresaRead.readObject();
					Altice.setAltice(temp);
					empresa.close();
					empresaRead.close();
				} catch (FileNotFoundException e) {
					try {
						empresa2 = new FileOutputStream("altice.dat");
						empresaWrite = new ObjectOutputStream(empresa2);
						
						Usuario userAdmin = new Usuario("USR-1", "Admin", "Admin", "admin@altice.do", true, new Date());
						Administrativo admin = new Administrativo("EMP-1", "Administrador General", "000-0000000-0", 
								"Sede Central", "809-000-0000", userAdmin, 50000f, 0f, 0f, "Sede Central", "Gerente");
						
						Altice.getInstance().regPersona(admin);
						empresaWrite.writeObject(Altice.getInstance());
						empresa2.close();
						empresaWrite.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Login() {
		setTitle("Altice - Inicio de Sesión");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 280);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(45, 35, 100, 14);
		panel.add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(45, 58, 300, 25);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblContrasena = new JLabel("Contraseña:");
		lblContrasena.setBounds(45, 100, 105, 14);
		panel.add(lblContrasena);

		txtContrasena = new JPasswordField();
		txtContrasena.setBounds(45, 123, 300, 25);
		panel.add(txtContrasena);

		JButton btnLogin = new JButton("Iniciar Sesión");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = txtUsuario.getText();
				String pass = new String(txtContrasena.getPassword());

				if (Altice.getInstance().confirmLogin(user, pass)) {
					Principal frame = new Principal();
					dispose();
					frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.", "Error de acceso", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnLogin.setBounds(45, 175, 130, 30);
		panel.add(btnLogin);
	}
}