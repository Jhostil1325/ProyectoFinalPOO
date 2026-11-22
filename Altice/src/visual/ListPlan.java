package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
	private DefaultTableModel model;
	private JTable table;

	public ListPlan()
	{
		setTitle("Listar Planes");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 820, 440);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

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
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(new EmptyBorder(10, 10, 5, 10));
		getContentPane().add(scroll, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JButton btnActivar = new JButton("Activar");
		btnActivar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cambiarEstadoPlan(true);
			}
		});
		panelBotones.add(btnActivar);

		JButton btnDesactivar = new JButton("Desactivar");
		btnDesactivar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cambiarEstadoPlan(false);
			}
		});
		panelBotones.add(btnDesactivar);

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
}