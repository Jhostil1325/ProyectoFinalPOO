package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Internet;
import logico.Servicio;
import logico.Telefonia;
import logico.Television;

public class ListServicio extends JDialog
{
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private JTable table;

	public ListServicio()
	{
		setTitle("Listar Servicios");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 780, 440);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		String[] columnas = { "ID", "Tipo", "Descripci\u00F3n", "Detalles", "Precio Base", "En Plan" };
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
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(new EmptyBorder(10, 10, 5, 10));
		getContentPane().add(scroll, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				modificarServicio();
			}
		});
		panelBotones.add(btnModificar);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		panelBotones.add(btnCerrar);

		getContentPane().add(panelBotones, BorderLayout.SOUTH);

		cargarServicios();
	}

	private void cargarServicios()
	{
		model.setRowCount(0);
		for (int i = 0; i < Altice.getInstance().getMisServicios().size(); i++)
		{
			Servicio s = Altice.getInstance().getMisServicios().get(i);
			Object[] fila = new Object[6];
			fila[0] = s.getId();
			fila[2] = s.getDescripcion();

			if (s instanceof Internet)
			{
				Internet net = (Internet) s;
				fila[1] = "Internet";
				fila[3] = net.getVelocidad() + " Mbps - " + net.getTipoConexion();
			}
			else if (s instanceof Television)
			{
				Television tv = (Television) s;
				fila[1] = "Televisi\u00F3n";
				fila[3] = tv.getCantCanales() + " canales - " + tv.getCalidad();
			}
			else if (s instanceof Telefonia)
			{
				Telefonia tel = (Telefonia) s;
				fila[1] = "Telefon\u00EDa";
				fila[3] = tel.isLlamadasIlimitadas() ? "Llamadas ilimitadas" : tel.getMinutos() + " minutos";
			}

			fila[4] = String.format("RD$ %.2f", s.calcularPrecioBase());
			fila[5] = s.isEnPlan() ? "S\u00ED" : "No";
			model.addRow(fila);
		}
	}

	private void modificarServicio()
	{
		int row = table.getSelectedRow();
		if (row < 0)
		{
			JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Servicio s = Altice.getInstance().getMisServicios().get(row);

		JTextField txtDescripcion = new JTextField(s.getDescripcion());
		JSpinner spnValor = null;
		JComboBox<String> cbxOpcion = null;
		JCheckBox chkIlimitadas = null;

		JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
		panel.add(new JLabel("ID:"));
		panel.add(new JLabel(s.getId()));
		panel.add(new JLabel("Descripci\u00F3n:"));
		panel.add(txtDescripcion);

		if (s instanceof Internet)
		{
			Internet net = (Internet) s;
			spnValor = new JSpinner(new SpinnerNumberModel(net.getVelocidad(), 1, 10000, 10));
			cbxOpcion = new JComboBox<String>(new String[] { "Fibra Optica", "HFC / Cable", "ADSL" });
			cbxOpcion.setSelectedItem(net.getTipoConexion());
			panel.add(new JLabel("Velocidad (Mbps):"));
			panel.add(spnValor);
			panel.add(new JLabel("Tipo de conexi\u00F3n:"));
			panel.add(cbxOpcion);
		}
		else if (s instanceof Television)
		{
			Television tv = (Television) s;
			spnValor = new JSpinner(new SpinnerNumberModel(tv.getCantCanales(), 1, 1000, 10));
			cbxOpcion = new JComboBox<String>(new String[] { "SD", "HD", "4K" });
			cbxOpcion.setSelectedItem(tv.getCalidad());
			panel.add(new JLabel("Cantidad de canales:"));
			panel.add(spnValor);
			panel.add(new JLabel("Calidad:"));
			panel.add(cbxOpcion);
		}
		else if (s instanceof Telefonia)
		{
			Telefonia tel = (Telefonia) s;
			final JSpinner spnMinutos = new JSpinner(new SpinnerNumberModel(tel.getMinutos(), 0, 10000, 50));
			final JCheckBox chkIlim = new JCheckBox("Llamadas ilimitadas", tel.isLlamadasIlimitadas());
			spnMinutos.setEnabled(!tel.isLlamadasIlimitadas());
			chkIlim.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					spnMinutos.setEnabled(!chkIlim.isSelected());
				}
			});
			spnValor = spnMinutos;
			chkIlimitadas = chkIlim;
			panel.add(new JLabel("Cantidad de minutos:"));
			panel.add(spnMinutos);
			panel.add(new JLabel(""));
			panel.add(chkIlim);
		}

		while (true)
		{
			int opcion = JOptionPane.showConfirmDialog(this, panel, "Modificar Servicio",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (opcion != JOptionPane.OK_OPTION)
			{
				return;
			}

			String descripcion = txtDescripcion.getText().trim();
			if (descripcion.isEmpty())
			{
				JOptionPane.showMessageDialog(this, "Debe ingresar una descripci\u00F3n.", "Campos vac\u00EDos",
						JOptionPane.WARNING_MESSAGE);
				continue;
			}

			s.setDescripcion(descripcion);
			if (s instanceof Internet)
			{
				((Internet) s).setVelocidad(((Number) spnValor.getValue()).intValue());
				((Internet) s).setTipoConexion(cbxOpcion.getSelectedItem().toString());
			}
			else if (s instanceof Television)
			{
				((Television) s).setCantCanales(((Number) spnValor.getValue()).intValue());
				((Television) s).setCalidad(cbxOpcion.getSelectedItem().toString());
			}
			else if (s instanceof Telefonia)
			{
				boolean ilimitadas = chkIlimitadas.isSelected();
				((Telefonia) s).setLlamadasIlimitadas(ilimitadas);
				((Telefonia) s).setMinutos(ilimitadas ? 0 : ((Number) spnValor.getValue()).intValue());
			}

			JOptionPane.showMessageDialog(this, "Servicio modificado con \u00E9xito.", "Modificar",
					JOptionPane.INFORMATION_MESSAGE);
			cargarServicios();
			return;
		}
	}
}