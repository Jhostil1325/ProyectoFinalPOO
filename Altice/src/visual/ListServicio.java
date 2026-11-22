package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
}