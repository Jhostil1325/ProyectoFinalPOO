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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Contrato;
import logico.Empleado;
import logico.Pago;

public class ReporteVentasLocal extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JComboBox<String> cbxLocales;
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtContratosLocal;
	private JTextField txtIngresosLocal;

	public ReporteVentasLocal()
	{
		setTitle("Reporte - Ventas y Contratos por Local");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 860, 510);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
		JLabel lblTitulo = new JLabel("ALTICE | RENDIMIENTO DE VENTAS Y CONTRATOS POR SUCURSAL");
		lblTitulo.setForeground(new Color(0, 168, 232));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelHeader.add(lblTitulo);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new EmptyBorder(10, 15, 10, 15));
		panelCentro.setLayout(null);
		getContentPane().add(panelCentro, BorderLayout.CENTER);

		JLabel lblLocal = new JLabel("Seleccionar Sucursal / Local:");
		lblLocal.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLocal.setBounds(15, 15, 200, 20);
		panelCentro.add(lblLocal);

		cbxLocales = new JComboBox<String>();
		cbxLocales.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cargarDatosLocal();
			}
		});
		cbxLocales.setBounds(215, 13, 280, 26);
		panelCentro.add(cbxLocales);

		String[] columnas = { "Tipo Operaci\u00F3n", "ID", "Fecha", "Cliente", "Comercial / Asesor", "Local", "Monto" };
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
		scroll.setBounds(15, 52, 815, 290);
		panelCentro.add(scroll);

		JLabel lblCont = new JLabel("Contratos en Sucursal:");
		lblCont.setBounds(15, 355, 160, 14);
		panelCentro.add(lblCont);

		txtContratosLocal = new JTextField();
		txtContratosLocal.setEditable(false);
		txtContratosLocal.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtContratosLocal.setBounds(15, 375, 180, 28);
		panelCentro.add(txtContratosLocal);

		JLabel lblIng = new JLabel("Ingresos Cobrados en Sucursal:");
		lblIng.setBounds(220, 355, 210, 14);
		panelCentro.add(lblIng);

		txtIngresosLocal = new JTextField();
		txtIngresosLocal.setEditable(false);
		txtIngresosLocal.setForeground(new Color(0, 128, 0));
		txtIngresosLocal.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtIngresosLocal.setBounds(220, 375, 230, 28);
		panelCentro.add(txtIngresosLocal);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnCerrar.setBounds(710, 375, 120, 28);
		panelCentro.add(btnCerrar);

		cargarListaLocales();
	}

	private void cargarListaLocales()
	{
		cbxLocales.removeAllItems();
		cbxLocales.addItem("Todos los Locales");
		ArrayList<String> unicos = new ArrayList<>();

		for (int i = 0; i < Altice.getInstance().getEmpleados().size(); i++)
		{
			Empleado emp = Altice.getInstance().getEmpleados().get(i);
			if (emp.getMiLocal() != null && !emp.getMiLocal().trim().isEmpty())
			{
				String loc = emp.getMiLocal().trim();
				boolean existe = false;
				for (String u : unicos)
				{
					if (u.equalsIgnoreCase(loc))
					{
						existe = true;
						break;
					}
				}
				if (!existe)
				{
					unicos.add(loc);
					cbxLocales.addItem(loc);
				}
			}
		}
		cargarDatosLocal();
	}

	private void cargarDatosLocal()
	{
		if (cbxLocales.getSelectedItem() == null)
		{
			return;
		}

		String seleccionado = cbxLocales.getSelectedItem().toString();
		boolean todos = seleccionado.equals("Todos los Locales");

		model.setRowCount(0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int cantContratos = 0;
		float ingresos = 0.0f;

		for (int i = 0; i < Altice.getInstance().getMisContratos().size(); i++)
		{
			Contrato c = Altice.getInstance().getMisContratos().get(i);
			String local = (c.getFacilitador() != null && c.getFacilitador().getMiLocal() != null)
					? c.getFacilitador().getMiLocal() : "Sede Central";

			if (todos || local.equalsIgnoreCase(seleccionado))
			{
				Object[] fila = new Object[7];
				fila[0] = "Contrato";
				fila[1] = c.getId();
				fila[2] = sdf.format(c.getFechaIni());
				fila[3] = c.getMiCliente().getNombre();
				fila[4] = c.getFacilitador() != null ? c.getFacilitador().getNombre() : "Admin / Sistema";
				fila[5] = local;
				fila[6] = String.format("RD$ %.2f/mes", c.getMiPlan().getPrecioMensual());
				model.addRow(fila);
				cantContratos++;
			}
		}

		for (int i = 0; i < Altice.getInstance().getMisPagos().size(); i++)
		{
			Pago p = Altice.getInstance().getMisPagos().get(i);
			if (p.isPagada())
			{
				String local = (p.getFacilitador() != null && p.getFacilitador().getMiLocal() != null)
						? p.getFacilitador().getMiLocal() : "Sede Central";

				if (todos || local.equalsIgnoreCase(seleccionado))
				{
					Object[] fila = new Object[7];
					fila[0] = "Pago Cobrado";
					fila[1] = p.getId();
					fila[2] = sdf.format(p.getFechaPago());
					fila[3] = p.getMiCliente().getNombre();
					fila[4] = p.getFacilitador() != null ? p.getFacilitador().getNombre() : "Admin / Caja";
					fila[5] = local;
					fila[6] = String.format("RD$ %.2f", p.getTotalPago());
					model.addRow(fila);
					ingresos += p.getTotalPago();
				}
			}
		}

		txtContratosLocal.setText(String.valueOf(cantContratos));
		txtIngresosLocal.setText(String.format("RD$ %.2f", ingresos));
	}
}