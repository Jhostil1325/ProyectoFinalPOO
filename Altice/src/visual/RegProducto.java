package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import logico.Altice;
import logico.Producto;

public class RegProducto extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTextField txtId;
	private JTextField txtDescripcion;
	private JTextField txtMarca;
	private JComboBox<String> cbxTipo;
	private JSpinner spnStock;
	private JTextField txtPrecio;

	public RegProducto()
	{
		setTitle("Registrar Producto");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 440, 430);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
		JLabel lblHeader = new JLabel("ALTICE | REGISTRO DE EQUIPOS Y PRODUCTOS");
		lblHeader.setForeground(new Color(0, 168, 232));
		lblHeader.setFont(new Font("Tahoma", Font.BOLD, 14));
		panelHeader.add(lblHeader);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(24, 24, 24));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblId = new JLabel("ID Producto:");
		lblId.setForeground(Color.WHITE);
		lblId.setBounds(35, 20, 100, 14);
		panel.add(lblId);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBackground(new Color(40, 40, 40));
		txtId.setForeground(new Color(0, 168, 232));
		txtId.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtId.setText("PRD-" + Producto.getCont());
		txtId.setBounds(35, 40, 355, 26);
		panel.add(txtId);

		JLabel lblDesc = new JLabel("Descripci\u00F3n / Modelo:");
		lblDesc.setForeground(Color.WHITE);
		lblDesc.setBounds(35, 78, 160, 14);
		panel.add(lblDesc);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(35, 98, 355, 26);
		panel.add(txtDescripcion);

		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setForeground(Color.WHITE);
		lblMarca.setBounds(35, 136, 100, 14);
		panel.add(lblMarca);

		txtMarca = new JTextField();
		txtMarca.setBounds(35, 156, 170, 26);
		panel.add(txtMarca);

		JLabel lblTipo = new JLabel("Categor\u00EDa:");
		lblTipo.setForeground(Color.WHITE);
		lblTipo.setBounds(220, 136, 100, 14);
		panel.add(lblTipo);

		cbxTipo = new JComboBox<String>();
		cbxTipo.addItem("Router / M\u00F3dem");
		cbxTipo.addItem("Celular / Smartphone");
		cbxTipo.addItem("Decodificador TV");
		cbxTipo.addItem("Accesorio / Cable");
		cbxTipo.setBounds(220, 156, 170, 26);
		panel.add(cbxTipo);

		JLabel lblStock = new JLabel("Stock Inicial:");
		lblStock.setForeground(Color.WHITE);
		lblStock.setBounds(35, 194, 100, 14);
		panel.add(lblStock);

		spnStock = new JSpinner(new SpinnerNumberModel(10, 1, 5000, 1));
		spnStock.setBounds(35, 214, 170, 26);
		panel.add(spnStock);

		JLabel lblPrecio = new JLabel("Precio Unitario (RD$):");
		lblPrecio.setForeground(Color.WHITE);
		lblPrecio.setBounds(220, 194, 160, 14);
		panel.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(220, 214, 170, 26);
		panel.add(txtPrecio);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBackground(new Color(0, 136, 255));
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRegistrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				registrar();
			}
		});
		btnRegistrar.setBounds(95, 275, 115, 30);
		panel.add(btnRegistrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnCancelar.setBounds(225, 275, 115, 30);
		panel.add(btnCancelar);
	}

	private void registrar()
	{
		String desc = txtDescripcion.getText().trim();
		String marca = txtMarca.getText().trim();
		String tipo = cbxTipo.getSelectedItem().toString();
		int stock = Integer.parseInt(spnStock.getValue().toString());

		if (desc.isEmpty() || marca.isEmpty() || txtPrecio.getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Campos vac\u00EDos",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		float precio = 0.0f;
		try
		{
			precio = Float.parseFloat(txtPrecio.getText().trim().replace(",", "."));
			if (precio <= 0)
			{
				throw new NumberFormatException();
			}
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(this, "Ingrese un precio v\u00E1lido mayor a cero.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Producto prod = new Producto(desc, marca, tipo, stock, stock > 0, true, precio);
		Altice.getInstance().registrarProducto(prod);
		JOptionPane.showMessageDialog(this, "Producto registrado en inventario.", "Inventario",
				JOptionPane.INFORMATION_MESSAGE);

		txtId.setText("PRD-" + Producto.getCont());
		txtDescripcion.setText("");
		txtMarca.setText("");
		txtPrecio.setText("");
		spnStock.setValue(10);
		txtDescripcion.requestFocus();
	}
}