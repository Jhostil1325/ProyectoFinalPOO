package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Cliente;
import logico.Contrato;
import logico.Internet;
import logico.Persona;
import logico.Servicio;
import logico.Telefonia;
import logico.Television;

public class MisContratos extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;

	public MisContratos()
	{
		setTitle("Portal Cliente - Mis Contratos");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 840, 450);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));

		Persona user = Altice.getLoginUser();
		String nombre = (user != null) ? user.getNombre() : "Cliente";
		JLabel lblTitulo = new JLabel("ALTICE | CONTRATOS DE " + nombre.toUpperCase());
		lblTitulo.setForeground(new Color(0, 168, 232));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelHeader.add(lblTitulo);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

		String[] columnas = { "ID Contrato", "Plan", "Servicios Incluidos", "Mensualidad", "Fecha Inicio", "Estado" };
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

		cargarContratos();
	}

	private String obtenerTipoServicio(Servicio s)
	{
		if (s instanceof Internet)
			return "Internet";
		if (s instanceof Television)
			return "TV";
		if (s instanceof Telefonia)
			return "Telefon\u00EDa";
		return "Servicio";
	}

	private void cargarContratos()
	{
		model.setRowCount(0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Persona user = Altice.getLoginUser();
		ArrayList<Contrato> lista = (user instanceof Cliente) ? ((Cliente) user).getMisContratos()
				: Altice.getInstance().getMisContratos();

		for (int i = 0; i < lista.size(); i++)
		{
			Contrato c = lista.get(i);
			String servicios = "";
			for (int j = 0; j < c.getMiPlan().getMisServicios().size(); j++)
			{
				Servicio s = c.getMiPlan().getMisServicios().get(j);
				servicios += obtenerTipoServicio(s) + ": " + s.getDescripcion();
				if (j < c.getMiPlan().getMisServicios().size() - 1)
				{
					servicios += " | ";
				}
			}

			Object[] fila = new Object[6];
			fila[0] = c.getId();
			fila[1] = c.getMiPlan().getId();
			fila[2] = servicios;
			fila[3] = String.format("RD$ %.2f", c.getMiPlan().getPrecioMensual());
			fila[4] = sdf.format(c.getFechaIni());
			fila[5] = c.isActivo() ? "Activo" : "Suspendido";
			model.addRow(fila);
		}
	}
}