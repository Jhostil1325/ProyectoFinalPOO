package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import logico.Internet;
import logico.Servicio;
import logico.Telefonia;
import logico.Television;
import java.awt.Font;

public class RegServicio extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTextField txtId;
	private JTextField txtDescripcion;
	private JComboBox<String> cbxTipo;
	private JPanel panelInternet;
	private JPanel panelTelevision;
	private JPanel panelTelefonia;
	private JSpinner spnVelocidad;
	private JComboBox<String> cbxTipoConexion;
	private JSpinner spnCanales;
	private JComboBox<String> cbxCalidad;
	private JSpinner spnMinutos;
	private JCheckBox chckbxIlimitadas;
	private JPanel panel_1;
	private JLabel lblNewLabel;

	public RegServicio()
	{
		setTitle("Registrar Servicio");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 408, 446);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 52, 393, 357);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		getContentPane().add(panel);

		JLabel lblId = new JLabel("ID Servicio:");
		lblId.setBounds(40, 20, 100, 14);
		panel.add(lblId);

		txtId = new JTextField();
		txtId.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtId.setEditable(false);
		txtId.setForeground(new Color(0, 168, 232));
		txtId.setText(Altice.getInstance().generarIdServicio());
		txtId.setBounds(40, 40, 320, 25);
		panel.add(txtId);

		JLabel lblDescripcion = new JLabel("Descripci\u00F3n:");
		lblDescripcion.setBounds(40, 75, 100, 14);
		panel.add(lblDescripcion);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(40, 95, 320, 25);
		panel.add(txtDescripcion);

		JLabel lblTipo = new JLabel("Tipo de Servicio:");
		lblTipo.setBounds(40, 130, 150, 14);
		panel.add(lblTipo);

		cbxTipo = new JComboBox<String>();
		cbxTipo.addItem("Internet");
		cbxTipo.addItem("Televisi\u00F3n");
		cbxTipo.addItem("Telefon\u00EDa");
		cbxTipo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cambiarPanelTipo();
			}
		});
		cbxTipo.setBounds(40, 150, 320, 25);
		panel.add(cbxTipo);

		panelInternet = new JPanel();
		panelInternet.setLayout(null);
		panelInternet.setBounds(40, 185, 320, 105);
		panel.add(panelInternet);

		JLabel lblVelocidad = new JLabel("Velocidad (Mbps):");
		lblVelocidad.setBounds(0, 0, 150, 14);
		panelInternet.add(lblVelocidad);

		spnVelocidad = new JSpinner(new SpinnerNumberModel(50, 1, 10000, 10));
		spnVelocidad.setBounds(0, 20, 320, 25);
		panelInternet.add(spnVelocidad);

		JLabel lblConexion = new JLabel("Tipo de Conexi\u00F3n:");
		lblConexion.setBounds(0, 55, 150, 14);
		panelInternet.add(lblConexion);

		cbxTipoConexion = new JComboBox<String>();
		cbxTipoConexion.addItem("Fibra Optica");
		cbxTipoConexion.addItem("HFC / Cable");
		cbxTipoConexion.addItem("ADSL");
		cbxTipoConexion.setBounds(0, 75, 320, 25);
		panelInternet.add(cbxTipoConexion);

		panelTelevision = new JPanel();
		panelTelevision.setLayout(null);
		panelTelevision.setBounds(40, 185, 320, 105);
		panelTelevision.setVisible(false);
		panel.add(panelTelevision);

		JLabel lblCanales = new JLabel("Cantidad de Canales:");
		lblCanales.setBounds(0, 0, 150, 14);
		panelTelevision.add(lblCanales);

		spnCanales = new JSpinner(new SpinnerNumberModel(100, 1, 1000, 10));
		spnCanales.setBounds(0, 20, 320, 25);
		panelTelevision.add(spnCanales);

		JLabel lblCalidad = new JLabel("Calidad:");
		lblCalidad.setBounds(0, 55, 150, 14);
		panelTelevision.add(lblCalidad);

		cbxCalidad = new JComboBox<String>();
		cbxCalidad.addItem("SD");
		cbxCalidad.addItem("HD");
		cbxCalidad.addItem("4K");
		cbxCalidad.setBounds(0, 75, 320, 25);
		panelTelevision.add(cbxCalidad);

		panelTelefonia = new JPanel();
		panelTelefonia.setLayout(null);
		panelTelefonia.setBounds(40, 185, 320, 105);
		panelTelefonia.setVisible(false);
		panel.add(panelTelefonia);

		JLabel lblMinutos = new JLabel("Cantidad de Minutos:");
		lblMinutos.setBounds(0, 0, 150, 14);
		panelTelefonia.add(lblMinutos);

		spnMinutos = new JSpinner(new SpinnerNumberModel(200, 0, 10000, 50));
		spnMinutos.setBounds(0, 20, 320, 25);
		panelTelefonia.add(spnMinutos);

		chckbxIlimitadas = new JCheckBox("Llamadas ilimitadas");
		chckbxIlimitadas.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				spnMinutos.setEnabled(!chckbxIlimitadas.isSelected());
			}
		});
		chckbxIlimitadas.setBounds(0, 60, 200, 23);
		panelTelefonia.add(chckbxIlimitadas);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRegistrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				registrar();
			}
		});
		btnRegistrar.setBounds(99, 305, 100, 28);
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
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(18, 18, 18));
		panel_1.setBounds(0, 0, 402, 53);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		lblNewLabel = new JLabel("ALTICE | REGISTRAR SERVICIO");
		lblNewLabel.setForeground(new Color(0, 168, 232));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(12, 0, 390, 53);
		panel_1.add(lblNewLabel);
	}

	private void cambiarPanelTipo()
	{
		int index = cbxTipo.getSelectedIndex();
		panelInternet.setVisible(index == 0);
		panelTelevision.setVisible(index == 1);
		panelTelefonia.setVisible(index == 2);
	}

	private void registrar()
	{
		String descripcion = txtDescripcion.getText().trim();
		if (descripcion.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Debe ingresar una descripci\u00F3n.", "Campos vac\u00EDos",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Servicio servicio = null;
		int index = cbxTipo.getSelectedIndex();
		if (index == 0)
		{
			int velocidad = Integer.parseInt(spnVelocidad.getValue().toString());
			String conexion = cbxTipoConexion.getSelectedItem().toString();
			servicio = new Internet(txtId.getText(), descripcion, false, velocidad, conexion);
		}
		else if (index == 1)
		{
			int canales = Integer.parseInt(spnCanales.getValue().toString());
			String calidad = cbxCalidad.getSelectedItem().toString();
			servicio = new Television(txtId.getText(), descripcion, false, canales, calidad);
		}
		else
		{
			boolean ilimitadas = chckbxIlimitadas.isSelected();
			int minutos = ilimitadas ? 0 : Integer.parseInt(spnMinutos.getValue().toString());
			servicio = new Telefonia(txtId.getText(), descripcion, false, minutos, ilimitadas);
		}

		Altice.getInstance().registrarServicio(servicio);
		JOptionPane.showMessageDialog(this, "Servicio registrado con \u00E9xito.", "Registro",
				JOptionPane.INFORMATION_MESSAGE);
		limpiar();
	}

	private void limpiar()
	{
		txtId.setText(Altice.getInstance().generarIdServicio());
		txtDescripcion.setText("");
		spnVelocidad.setValue(50);
		spnCanales.setValue(100);
		spnMinutos.setValue(200);
		spnMinutos.setEnabled(true);
		chckbxIlimitadas.setSelected(false);
		txtDescripcion.requestFocus();
	}
}