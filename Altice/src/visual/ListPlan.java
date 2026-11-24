package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class ListPlan extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;

	public ListPlan()
	{
		setTitle("Listar Planes");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 820, 440);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));

		JLabel lblTitulo = new JLabel("ALTICE | LISTADO DE PLANES");
		lblTitulo.setForeground(new Color(0, 168, 232));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelHeader.add(lblTitulo);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

		String[] columnas = { "ID Plan", "Servicios Incluidos", "Precio Mensual", "Estado" };
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
		table.getTableHeader().setBackground(new Color(30, 30, 30));
		table.getTableHeader().setForeground(Color.WHITE);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(new EmptyBorder(10, 15, 5, 15));
		getContentPane().add(scroll, BorderLayout.CENTER);

		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				modificarPlan();
			}
		});
		panelSur.add(btnModificar);

		JButton btnActivar = new JButton("Activar");
		btnActivar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cambiarEstadoPlan(true);
			}
		});
		panelSur.add(btnActivar);

		JButton btnDesactivar = new JButton("Desactivar");
		btnDesactivar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cambiarEstadoPlan(false);
			}
		});
		panelSur.add(btnDesactivar);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		panelSur.add(btnCerrar);
		getContentPane().add(panelSur, BorderLayout.SOUTH);

		cargarPlanes();
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

	private void cargarPlanes()
	{
		model.setRowCount(0);
		for (int i = 0; i < Altice.getInstance().getMisPlanes().size(); i++)
		{
			Plan p = Altice.getInstance().getMisPlanes().get(i);
			String servicios = "";
			if (p.getMisServicios() != null)
			{
				for (int j = 0; j < p.getMisServicios().size(); j++)
				{
					Servicio s = p.getMisServicios().get(j);
					servicios += obtenerTipoServicio(s) + ": " + s.getDescripcion();
					if (j < p.getMisServicios().size() - 1)
					{
						servicios += " | ";
					}
				}
			}
			Object[] fila = new Object[4];
			fila[0] = p.getId();
			fila[1] = servicios;
			fila[2] = String.format("RD$ %.2f", p.getPrecioMensual());
			fila[3] = p.isEstado() ? "Activo" : "Inactivo";
			model.addRow(fila);
		}
	}

	private void cambiarEstadoPlan(boolean nuevoEstado)
	{
		int row = table.getSelectedRow();
		if (row < 0)
		{
			JOptionPane.showMessageDialog(this, "Seleccione un plan de la tabla.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Plan p = Altice.getInstance().getMisPlanes().get(row);
		if (p.isEstado() == nuevoEstado)
		{
			JOptionPane.showMessageDialog(this,
					"El plan ya se encuentra " + (nuevoEstado ? "activo." : "inactivo."), "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		p.setEstado(nuevoEstado);
		cargarPlanes();
	}

	private void modificarPlan()
	{
		int row = table.getSelectedRow();
		if (row < 0)
		{
			JOptionPane.showMessageDialog(this, "Seleccione un plan de la tabla.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Plan p = Altice.getInstance().getMisPlanes().get(row);
		JTextField txtPrecio = new JTextField(String.format("%.2f", p.getPrecioMensual()));

		JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
		panel.add(new JLabel("ID Plan:"));
		panel.add(new JLabel(p.getId()));
		panel.add(new JLabel("Precio mensual (RD$):"));
		panel.add(txtPrecio);

		while (true)
		{
			int opcion = JOptionPane.showConfirmDialog(this, panel, "Modificar Plan",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (opcion != JOptionPane.OK_OPTION)
			{
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
				continue;
			}

			if (precio <= 0)
			{
				JOptionPane.showMessageDialog(this, "El precio mensual debe ser mayor a cero.", "Error",
						JOptionPane.ERROR_MESSAGE);
				continue;
			}

			Altice.getInstance().modificarPlan(p, precio);
			JOptionPane.showMessageDialog(this, "Plan modificado con \u00E9xito.", "Modificar",
					JOptionPane.INFORMATION_MESSAGE);
			cargarPlanes();
			return;
		}
	}
}
