package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Contrato;

public class ListContrato extends JDialog
{
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private JTable table;

	public ListContrato()
	{
		setTitle("Listar Contratos");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 820, 460);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		String[] columnas = { "ID Contrato", "Cliente", "C\u00E9dula", "ID Plan", "Mensualidad", "Fecha", "Estado" };
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

		cargarContratos();
	}

	private void cargarContratos()
	{
		model.setRowCount(0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (int i = 0; i < Altice.getInstance().getMisContratos().size(); i++)
		{
			Contrato c = Altice.getInstance().getMisContratos().get(i);
			Object[] fila = new Object[7];
			fila[0] = c.getId();
			fila[1] = c.getMiCliente().getNombre();
			fila[2] = c.getMiCliente().getCedula();
			fila[3] = c.getMiPlan().getId();
			fila[4] = String.format("RD$ %.2f", c.getMiPlan().getPrecioMensual());
			fila[5] = sdf.format(c.getFechaIni());
			fila[6] = c.isActivo() ? "Activo" : "Suspendido";
			model.addRow(fila);
		}
	}
}