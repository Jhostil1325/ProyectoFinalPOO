package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Contrato;
import logico.Internet;
import logico.Plan;
import logico.Producto;
import logico.Servicio;
import logico.Telefonia;
import logico.Television;

public class ReporteInventarioPlanes extends JDialog
{
	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelPlanes;
	private DefaultTableModel modelProductos;

	public ReporteInventarioPlanes()
	{
		setTitle("Reporte - Inventario y Planes Activos");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 860, 490);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
		JLabel lblTitulo = new JLabel("ALTICE | CAT\u00C1LOGO DE PLANES ACTIVOS E INVENTARIO DE EQUIPOS");
		lblTitulo.setForeground(new Color(0, 168, 232));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelHeader.add(lblTitulo);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(new EmptyBorder(10, 15, 5, 15));
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// Pestaña 1: Planes Activos
		String[] colPlanes = { "ID Plan", "Servicios Incluidos", "Mensualidad Base", "Contratos Suscritos", "Estado" };
		modelPlanes = new DefaultTableModel(colPlanes, 0)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		JTable tablePlanes = new JTable(modelPlanes);
		tablePlanes.getTableHeader().setReorderingAllowed(false);
		tablePlanes.getTableHeader().setBackground(new Color(30, 30, 30));
		tablePlanes.getTableHeader().setForeground(Color.WHITE);
		tabbedPane.addTab("Planes Activos", new JScrollPane(tablePlanes));

		// Pestaña 2: Inventario de Productos
		String[] colProd = { "ID", "Descripci\u00F3n", "Marca", "Tipo", "Precio Unit.", "Stock", "Estado Stock" };
		modelProductos = new DefaultTableModel(colProd, 0)
		{
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		JTable tableProductos = new JTable(modelProductos);
		tableProductos.getTableHeader().setReorderingAllowed(false);
		tableProductos.getTableHeader().setBackground(new Color(30, 30, 30));
		tableProductos.getTableHeader().setForeground(Color.WHITE);
		tabbedPane.addTab("Inventario de Productos", new JScrollPane(tableProductos));

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

		cargarPlanesActivos();
		cargarProductos();
	}

	private int contarContratosPorPlan(Plan plan)
	{
		int count = 0;
		for (int i = 0; i < Altice.getInstance().getMisContratos().size(); i++)
		{
			Contrato c = Altice.getInstance().getMisContratos().get(i);
			if (c.getMiPlan() == plan && c.isActivo())
			{
				count++;
			}
		}
		return count;
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

	private void cargarPlanesActivos()
	{
		modelPlanes.setRowCount(0);
		for (int i = 0; i < Altice.getInstance().obtenerPlanesActivos().size(); i++)
		{
			Plan p = Altice.getInstance().obtenerPlanesActivos().get(i);
			String servicios = "";
			for (int j = 0; j < p.getMisServicios().size(); j++)
			{
				Servicio s = p.getMisServicios().get(j);
				servicios += obtenerTipoServicio(s) + " (" + s.getDescripcion() + ")";
				if (j < p.getMisServicios().size() - 1)
				{
					servicios += " + ";
				}
			}

			Object[] fila = new Object[5];
			fila[0] = p.getId();
			fila[1] = servicios;
			fila[2] = String.format("RD$ %.2f", p.getPrecioMensual());
			fila[3] = contarContratosPorPlan(p);
			fila[4] = "Activo";
			modelPlanes.addRow(fila);
		}
	}

	private void cargarProductos()
	{
		modelProductos.setRowCount(0);
		for (int i = 0; i < Altice.getInstance().getMisProductos().size(); i++)
		{
			Producto prod = Altice.getInstance().getMisProductos().get(i);
			String alerta = "Disponible";
			if (prod.getStock() == 0)
			{
				alerta = "AGOTADO";
			}
			else if (prod.getStock() <= 5)
			{
				alerta = "STOCK BAJO";
			}

			Object[] fila = new Object[7];
			fila[0] = prod.getId();
			fila[1] = prod.getDescripcion();
			fila[2] = prod.getMarca();
			fila[3] = prod.getTipoProducto();
			fila[4] = String.format("RD$ %.2f", prod.getPrecio());
			fila[5] = prod.getStock();
			fila[6] = alerta;
			modelProductos.addRow(fila);
		}
	}
}