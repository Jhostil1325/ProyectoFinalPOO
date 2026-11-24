package visual;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logico.Administrativo;
import logico.Altice;
import logico.Comercial;
import logico.Empleado;
import java.awt.Font;
import java.awt.Color;

public class RegEmpleado extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTextField txtId;
	private JComboBox<String> cbxTipo;
	private JTextField txtNombre;
	private JTextField txtCedula;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private JTextField txtSueldo;
	private JTextField txtLocal;
	private JLabel lblExtra;
	private JTextField txtExtra;

	public RegEmpleado()
	{
		setTitle("Registrar Empleado");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 430, 501);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 54, 424, 412);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		getContentPane().add(panel);

		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(30, 20, 100, 14);
		panel.add(lblId);

		txtId = new JTextField();
		txtId.setForeground(new Color(0, 168, 232));
		txtId.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtId.setEditable(false);
		txtId.setText(Altice.getInstance().generarIdEmpleado());
		txtId.setBounds(30, 40, 170, 25);
		panel.add(txtId);

		JLabel lblTipo = new JLabel("Tipo de empleado:");
		lblTipo.setBounds(220, 20, 140, 14);
		panel.add(lblTipo);

		cbxTipo = new JComboBox<String>();
		cbxTipo.addItem("Comercial");
		cbxTipo.addItem("Administrativo");
		cbxTipo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				actualizarCampoExtra();
			}
		});
		cbxTipo.setBounds(220, 40, 170, 25);
		panel.add(cbxTipo);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(30, 75, 100, 14);
		panel.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(30, 95, 360, 25);
		panel.add(txtNombre);

		JLabel lblCedula = new JLabel("C\u00E9dula:");
		lblCedula.setBounds(30, 130, 100, 14);
		panel.add(lblCedula);

		txtCedula = new JTextField();
		txtCedula.setBounds(30, 150, 170, 25);
		panel.add(txtCedula);

		JLabel lblTelefono = new JLabel("Tel\u00E9fono:");
		lblTelefono.setBounds(220, 130, 100, 14);
		panel.add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(220, 150, 170, 25);
		panel.add(txtTelefono);

		JLabel lblDireccion = new JLabel("Direcci\u00F3n:");
		lblDireccion.setBounds(30, 185, 100, 14);
		panel.add(lblDireccion);

		txtDireccion = new JTextField();
		txtDireccion.setBounds(30, 205, 360, 25);
		panel.add(txtDireccion);

		JLabel lblSueldo = new JLabel("Sueldo base:");
		lblSueldo.setBounds(30, 240, 100, 14);
		panel.add(lblSueldo);

		txtSueldo = new JTextField();
		txtSueldo.setBounds(30, 260, 170, 25);
		panel.add(txtSueldo);

		JLabel lblLocal = new JLabel("Local:");
		lblLocal.setBounds(220, 240, 100, 14);
		panel.add(lblLocal);

		txtLocal = new JTextField();
		txtLocal.setBounds(220, 260, 170, 25);
		panel.add(txtLocal);

		lblExtra = new JLabel("Meta de ventas:");
		lblExtra.setBounds(30, 295, 150, 14);
		panel.add(lblExtra);

		txtExtra = new JTextField();
		txtExtra.setBounds(30, 315, 170, 25);
		panel.add(txtExtra);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRegistrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				registrar();
			}
		});
		btnRegistrar.setBounds(105, 370, 100, 28);
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
		btnCancelar.setBounds(220, 370, 100, 28);
		panel.add(btnCancelar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(18, 18, 18));
		panel_1.setBounds(0, 0, 424, 56);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ALTICE | REGISTRAR EMPLEADO");
		lblNewLabel.setForeground(new Color(0, 168, 232));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(12, 0, 400, 56);
		panel_1.add(lblNewLabel);
	}

	private void actualizarCampoExtra()
	{
		if (cbxTipo.getSelectedItem().toString().equals("Comercial"))
		{
			lblExtra.setText("Meta de ventas:");
		}
		else
		{
			lblExtra.setText("Cargo:");
		}
		txtExtra.setText("");
	}

	private Float leerNumero(String texto)
	{
		try
		{
			float valor = Float.parseFloat(texto.trim());
			if (valor < 0)
			{
				return null;
			}
			return valor;
		}
		catch (NumberFormatException ex)
		{
			return null;
		}
	}

	private void registrar()
	{
		String nombre = txtNombre.getText().trim();
		String cedula = txtCedula.getText().trim();
		String telefono = txtTelefono.getText().trim();
		String direccion = txtDireccion.getText().trim();
		String local = txtLocal.getText().trim();
		String extra = txtExtra.getText().trim();

		if (nombre.isEmpty() || cedula.isEmpty() || telefono.isEmpty() || direccion.isEmpty()
				|| local.isEmpty() || extra.isEmpty() || txtSueldo.getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Campos vac\u00EDos",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Float sueldo = leerNumero(txtSueldo.getText());
		if (sueldo == null)
		{
			JOptionPane.showMessageDialog(this, "El sueldo base debe ser un n\u00FAmero v\u00E1lido.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Empleado empleado;
		if (cbxTipo.getSelectedItem().toString().equals("Comercial"))
		{
			Float meta = leerNumero(extra);
			if (meta == null)
			{
				JOptionPane.showMessageDialog(this, "La meta de ventas debe ser un n\u00FAmero v\u00E1lido.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			empleado = new Comercial(txtId.getText(), nombre, cedula, direccion, telefono, null, null, sueldo,
					0f, 0f, local, meta);
		}
		else
		{
			empleado = new Administrativo(txtId.getText(), nombre, cedula, direccion, telefono, null, null,
					sueldo, 0f, 0f, local, extra);
		}

		if (Altice.getInstance().registrarEmpleado(empleado))
		{
			JOptionPane.showMessageDialog(this, "Empleado registrado con \u00E9xito.", "Registro",
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
		txtId.setText(Altice.getInstance().generarIdEmpleado());
		cbxTipo.setSelectedIndex(0);
		txtNombre.setText("");
		txtCedula.setText("");
		txtTelefono.setText("");
		txtDireccion.setText("");
		txtSueldo.setText("");
		txtLocal.setText("");
		txtExtra.setText("");
		txtNombre.requestFocus();
	}
}