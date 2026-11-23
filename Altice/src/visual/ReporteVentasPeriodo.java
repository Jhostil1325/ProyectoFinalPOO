package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Pago;

public class ReporteVentasPeriodo extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JSpinner spnInicio;
	private JSpinner spnFin;
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtCantFacturas;
	private JTextField txtTotalItbis;
	private JTextField txtTotalRecaudado;

	public ReporteVentasPeriodo()
	{
		setTitle("Reporte - Ventas y Facturaci\u00F3n por Per\u00EDodo");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 880, 540);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
		JLabel lblTitulo = new JLabel("ALTICE | VENTAS Y FACTURACI\u00D3N POR PER\u00CDODO");
		lblTitulo.setForeground(new Color(0, 168, 232));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelHeader.add(lblTitulo);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new EmptyBorder(10, 15, 10, 15));
		panelCentro.setLayout(null);
		getContentPane().add(panelCentro, BorderLayout.CENTER);

		JPanel panelFiltro = new JPanel();
		panelFiltro.setBorder(new TitledBorder(null, "Rango de Fechas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelFiltro.setBounds(15, 10, 835, 70);
		panelFiltro.setLayout(null);
		panelCentro.add(panelFiltro);

		JLabel lblInicio = new JLabel("Fecha Inicio:");
		lblInicio.setBounds(25, 28, 85, 14);
		panelFiltro.add(lblInicio);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		spnInicio = new JSpinner(new SpinnerDateModel(cal.getTime(), null, null, Calendar.DAY_OF_MONTH));
		spnInicio.setEditor(new JSpinner.DateEditor(spnInicio, "dd/MM/yyyy"));
		spnInicio.setBounds(110, 23, 140, 25);
		panelFiltro.add(spnInicio);

		JLabel lblFin = new JLabel("Fecha Fin:");
		lblFin.setBounds(280, 28, 75, 14);
		panelFiltro.add(lblFin);

		spnFin = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
		spnFin.setEditor(new JSpinner.DateEditor(spnFin, "dd/MM/yyyy"));
		spnFin.setBounds(355, 23, 140, 25);
		panelFiltro.add(spnFin);

		JButton btnFiltrar = new JButton("Generar Reporte");
		btnFiltrar.setBackground(new Color(0, 136, 255));
		btnFiltrar.setForeground(Color.WHITE);
		btnFiltrar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnFiltrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				filtrar();
			}
		});
		btnFiltrar.setBounds(530, 22, 150, 28);
		panelFiltro.add(btnFiltrar);

		JButton btnVerTodas = new JButton("Ver Todo");
		btnVerTodas.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cargarTodas();
			}
		});
		btnVerTodas.setBounds(695, 22, 115, 28);
		panelFiltro.add(btnVerTodas);

		String[] columnas = { "ID Factura", "Fecha Pago", "Cliente", "Concepto", "M\u00E9todo", "ITBIS", "Total Pagado" };
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
		scroll.setBounds(15, 92, 835, 270);
		panelCentro.add(scroll);

		JLabel lblCant = new JLabel("Facturas Cobradas:");
		lblCant.setBounds(15, 375, 140, 14);
		panelCentro.add(lblCant);

		txtCantFacturas = new JTextField();
		txtCantFacturas.setEditable(false);
		txtCantFacturas.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtCantFacturas.setBounds(15, 395, 160, 28);
		panelCentro.add(txtCantFacturas);

		JLabel lblItbis = new JLabel("Total ITBIS Recaudado:");
		lblItbis.setBounds(200, 375, 160, 14);
		panelCentro.add(lblItbis);

		txtTotalItbis = new JTextField();
		txtTotalItbis.setEditable(false);
		txtTotalItbis.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtTotalItbis.setBounds(200, 395, 200, 28);
		panelCentro.add(txtTotalItbis);

		JLabel lblTotal = new JLabel("Ingreso Total Neto:");
		lblTotal.setBounds(425, 375, 160, 14);
		panelCentro.add(lblTotal);

		txtTotalRecaudado = new JTextField();
		txtTotalRecaudado.setEditable(false);
		txtTotalRecaudado.setForeground(new Color(0, 128, 0));
		txtTotalRecaudado.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtTotalRecaudado.setBounds(425, 395, 220, 28);
		panelCentro.add(txtTotalRecaudado);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnCerrar.setBounds(730, 395, 120, 28);
		panelCentro.add(btnCerrar);

		cargarTodas();
	}

	private Date limpiarHora(Date fecha, boolean finDia)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		if (finDia)
		{
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
		}
		else
		{
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
		}
		return cal.getTime();
	}

	private void filtrar()
	{
		Date inicio = limpiarHora((Date) spnInicio.getValue(), false);
		Date fin = limpiarHora((Date) spnFin.getValue(), true);

		if (inicio.after(fin))
		{
			JOptionPane.showMessageDialog(this, "La fecha de inicio no puede ser mayor a la fecha fin.", "Rango inv\u00E1lido",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		llenarTabla(inicio, fin);
	}

	private void cargarTodas()
	{
		llenarTabla(null, null);
	}

	private void llenarTabla(Date inicio, Date fin)
	{
		model.setRowCount(0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int count = 0;
		float sumaItbis = 0.0f;
		float sumaTotal = 0.0f;

		for (int i = 0; i < Altice.getInstance().getMisPagos().size(); i++)
		{
			Pago p = Altice.getInstance().getMisPagos().get(i);
			if (p.isPagada() && p.getFechaPago() != null)
			{
				boolean cumple = true;
				if (inicio != null && fin != null)
				{
					cumple = !p.getFechaPago().before(inicio) && !p.getFechaPago().after(fin);
				}

				if (cumple)
				{
					Object[] fila = new Object[7];
					fila[0] = p.getId();
					fila[1] = sdf.format(p.getFechaPago());
					fila[2] = p.getMiCliente() != null ? p.getMiCliente().getNombre() : "N/A";
					fila[3] = p.getMiContrato() != null ? "Contrato " + p.getMiContrato().getId() : "Venta Equipos";
					fila[4] = p.getMetodoPago();
					fila[5] = String.format("RD$ %.2f", p.getItbis());
					fila[6] = String.format("RD$ %.2f", p.getTotalPago());
					model.addRow(fila);

					count++;
					sumaItbis += p.getItbis();
					sumaTotal += p.getTotalPago();
				}
			}
		}

		txtCantFacturas.setText(String.valueOf(count));
		txtTotalItbis.setText(String.format("RD$ %.2f", sumaItbis));
		txtTotalRecaudado.setText(String.format("RD$ %.2f", sumaTotal));
	}
}