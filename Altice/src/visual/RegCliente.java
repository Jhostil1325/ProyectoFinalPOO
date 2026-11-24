package visual;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logico.Altice;
import logico.Cliente;
import logico.Contrato;
import logico.DetalleVenta;
import logico.Pago;
import java.awt.Color;
import java.awt.Font;

public class RegCliente extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTextField txtId;
	private JTextField txtNombre;
	private JTextField txtCedula;
	private JTextField txtDireccion;
	private JTextField txtTelefono;

	public RegCliente()
	{
		setTitle("Registrar Cliente");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 402, 447);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 51, 396, 339);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		getContentPane().add(panel);

		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(40, 25, 100, 14);
		panel.add(lblId);

		txtId = new JTextField();
		txtId.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtId.setForeground(new Color(0, 168, 232));
		txtId.setEditable(false);
		txtId.setText(Altice.getInstance().generarIdCliente());
		txtId.setBounds(40, 45, 320, 25);
		panel.add(txtId);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(40, 80, 100, 14);
		panel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(40, 100, 320, 25);
		panel.add(txtNombre);

		JLabel lblCedula = new JLabel("C\u00E9dula:");
		lblCedula.setBounds(40, 135, 100, 14);
		panel.add(lblCedula);

		txtCedula = new JTextField();
		txtCedula.setBounds(40, 155, 320, 25);
		panel.add(txtCedula);

		JLabel lblDireccion = new JLabel("Direcci\u00F3n:");
		lblDireccion.setBounds(40, 190, 100, 14);
		panel.add(lblDireccion);

		txtDireccion = new JTextField();
		txtDireccion.setBounds(40, 210, 320, 25);
		panel.add(txtDireccion);

		JLabel lblTelefono = new JLabel("Tel\u00E9fono:");
		lblTelefono.setBounds(40, 245, 100, 14);
		panel.add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(40, 265, 320, 25);
		panel.add(txtTelefono);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setForeground(new Color(0, 0, 0));
		btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRegistrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				registrar();
			}
		});
		btnRegistrar.setBounds(98, 305, 100, 28);
		panel.add(btnRegistrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCancelar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnCancelar.setBounds(210, 305, 100, 28);
		panel.add(btnCancelar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(18, 18, 18));
		panel_1.setBounds(0, 0, 427, 54);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ALTICE | REGISTRAR CLIENTES ");
		lblNewLabel.setForeground(new Color(0, 168, 232));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(12, 0, 403, 49);
		panel_1.add(lblNewLabel);
	}

	private void registrar()
	{
		String nombre = txtNombre.getText().trim();
		String cedula = txtCedula.getText().trim();
		String direccion = txtDireccion.getText().trim();
		String telefono = txtTelefono.getText().trim();

		if (nombre.isEmpty() || cedula.isEmpty() || direccion.isEmpty() || telefono.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Campos vac\u00EDos",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Cliente cliente = new Cliente(txtId.getText(), nombre, cedula, direccion, telefono, cedula, cedula,
				new ArrayList<DetalleVenta>(), null, new ArrayList<Contrato>(), new ArrayList<Pago>(), 0f, 0);

		if (Altice.getInstance().registrarCliente(cliente))
		{
			JOptionPane.showMessageDialog(this, "Cliente registrado con \u00E9xito.", "Registro",
					JOptionPane.INFORMATION_MESSAGE);
			limpiar();
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Ya existe una persona registrada con esa c\u00E9dula.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void limpiar()
	{
		txtId.setText(Altice.getInstance().generarIdCliente());
		txtNombre.setText("");
		txtCedula.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtNombre.requestFocus();
	}
}