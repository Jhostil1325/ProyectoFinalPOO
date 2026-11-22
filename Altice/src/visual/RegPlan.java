package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Internet;
import logico.Plan;
import logico.Servicio;
import logico.Telefonia;
import logico.Television;

public class RegPlan extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTextField txtId;
	private JTextField txtPrecio;
	private JTable tableDisponibles;
	private DefaultTableModel modelDisponibles;
	private JTable tableIncluidos;
	private DefaultTableModel modelIncluidos;

	private ArrayList<Servicio> disponibles = new ArrayList<>();
	private ArrayList<Servicio> incluidos = new ArrayList<>();

	public RegPlan()
	{
		setTitle("Registrar Plan");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 680, 440);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setLayout(null);
		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblId = new JLabel("ID Plan:");
		lblId.setBounds(30, 20, 100, 14);
		panel.add(lblId);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setForeground(Color.BLACK);
		txtId.setText(Altice.getInstance().generarIdPlan());
		txtId.setBounds(30, 40, 250, 25);
		panel.add(txtId);

		JLabel lblPrecio = new JLabel("Precio Mensual (RD$):");
		lblPrecio.setBounds(310, 20, 150, 14);
		panel.add(lblPrecio);

		txtPrecio = new JTextField("0.00");
		txtPrecio.setBounds(310, 40, 330, 25);
		panel.add(txtPrecio);

		JLabel lblDisp = new JLabel("Servicios Disponibles:");
		lblDisp.setBounds(30, 80, 180, 14);
		panel.add(lblDisp);

		String[] colDisp = { "ID", "Tipo", "Descripci\u00F3n", "Precio Base" };
		modelDisponibles = new DefaultTableModel(colDisp, 0)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};

		tableDisponibles = new JTable(modelDisponibles);
		tableDisponibles.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollDisp = new JScrollPane(tableDisponibles);
		scrollDisp.setBounds(30, 100, 270, 220);
		panel.add(scrollDisp);

		JButton btnAgregar = new JButton(">>");
		btnAgregar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				agregarServicio();
			}
		});
		btnAgregar.setBounds(310, 165, 50, 30);
		panel.add(btnAgregar);

		JButton btnQuitar = new JButton("<<");
		btnQuitar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				quitarServicio();
			}
		});
		btnQuitar.setBounds(310, 210, 50, 30);
		panel.add(btnQuitar);

		JLabel lblInc = new JLabel("Servicios en el Plan:");
		lblInc.setBounds(370, 80, 180, 14);
		panel.add(lblInc);

		String[] colInc = { "ID", "Tipo", "Descripci\u00F3n", "Precio Base" };
		modelIncluidos = new DefaultTableModel(colInc, 0)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};

		tableIncluidos = new JTable(modelIncluidos);
		tableIncluidos.getTableHeader().setReorderingAllowed(false);
		JScrollPane scrollInc = new JScrollPane(tableIncluidos);
		scrollInc.setBounds(370, 100, 270, 220);
		panel.add(scrollInc);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				registrar();
			}
		});
		btnRegistrar.setBounds(225, 345, 100, 28);
		panel.add(btnRegistrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnCancelar.setBounds(340, 345, 100, 28);
		panel.add(btnCancelar);

		cargarServicios();
	}

	private void cargarServicios()
	{
		disponibles.clear();
		incluidos.clear();
		for (int i = 0; i < Altice.getInstance().getMisServicios().size(); i++)
		{
			disponibles.add(Altice.getInstance().getMisServicios().get(i));
		}
		actualizarTablas();
	}

	private String obtenerTipoServicio(Servicio s)
	{
		if (s instanceof Internet)
		{
			return "Internet";
		}
		else if (s instanceof Television)
		{
			return "Televisi\u00F3n";
		}
		else if (s instanceof Telefonia)
		{
			return "Telefon\u00EDa";
		}
		return "Servicio";
	}

	private void actualizarTablas()
	{
		modelDisponibles.setRowCount(0);
		for (int i = 0; i < disponibles.size(); i++)
		{
			Servicio s = disponibles.get(i);
			Object[] fila = new Object[4];
			fila[0] = s.getId();
			fila[1] = obtenerTipoServicio(s);
			fila[2] = s.getDescripcion();
			fila[3] = String.format("RD$ %.2f", s.calcularPrecioBase());
			modelDisponibles.addRow(fila);
		}

		modelIncluidos.setRowCount(0);
		float suma = 0.0f;
		for (int i = 0; i < incluidos.size(); i++)
		{
			Servicio s = incluidos.get(i);
			Object[] fila = new Object[4];
			fila[0] = s.getId();
			fila[1] = obtenerTipoServicio(s);
			fila[2] = s.getDescripcion();
			fila[3] = String.format("RD$ %.2f", s.calcularPrecioBase());
			modelIncluidos.addRow(fila);
			suma += s.calcularPrecioBase();
		}
		txtPrecio.setText(String.format("%.2f", suma));
	}

	private void agregarServicio()
	{
		int row = tableDisponibles.getSelectedRow();
		if (row >= 0)
		{
			Servicio s = disponibles.remove(row);
			incluidos.add(s);
			actualizarTablas();
		}
	}

	private void quitarServicio()
	{
		int row = tableIncluidos.getSelectedRow();
		if (row >= 0)
		{
			Servicio s = incluidos.remove(row);
			disponibles.add(s);
			actualizarTablas();
		}
	}

	private void registrar()
	{
		if (incluidos.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Debe agregar al menos un servicio al plan.", "Campos vac\u00EDos",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		float precio = 0.0f;
		try
		{
			precio = Float.parseFloat(txtPrecio.getText().trim().replace(",", "."));
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(this, "Ingrese un precio mensual v\u00E1lido.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (precio <= 0)
		{
			JOptionPane.showMessageDialog(this, "El precio mensual debe ser mayor a cero.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Plan plan = new Plan(txtId.getText(), precio, true);
		for (int i = 0; i < incluidos.size(); i++)
		{
			Servicio s = incluidos.get(i);
			s.setEnPlan(true);
			plan.getMisServicios().add(s);
		}

		Altice.getInstance().registrarPlan(plan);
		JOptionPane.showMessageDialog(this, "Plan registrado con \u00E9xito.", "Registro",
				JOptionPane.INFORMATION_MESSAGE);
		limpiar();
	}

	private void limpiar()
	{
		txtId.setText(Altice.getInstance().generarIdPlan());
		cargarServicios();
	}
}