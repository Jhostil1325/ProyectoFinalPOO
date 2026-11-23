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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Cliente;

public class ReporteClientesDeuda extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtTotalMorosos;
	private JTextField txtDeudaGlobal;

	public ReporteClientesDeuda()
	{
		setTitle("Reporte - Clientes con Deuda");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 840, 480);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
		JLabel lblTitulo = new JLabel("ALTICE | CARTERA DE CLIENTES CON DEUDA PENDIENTE");
		lblTitulo.setForeground(new Color(255, 85, 85));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelHeader.add(lblTitulo);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new EmptyBorder(10, 15, 10, 15));
		panelCentro.setLayout(null);
		getContentPane().add(panelCentro, BorderLayout.CENTER);

		String[] columnas = { "ID", "Nombre del Cliente", "C\u00E9dula", "Tel\u00E9fono", "Facturas Pend.", "Deuda Total", "Estado" };
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
		scroll.setBounds(15, 15, 795, 300);
		panelCentro.add(scroll);

		JLabel lblMorosos = new JLabel("Clientes con Saldo Pendiente:");
		lblMorosos.setBounds(15, 330, 200, 14);
		panelCentro.add(lblMorosos);

		txtTotalMorosos = new JTextField();
		txtTotalMorosos.setEditable(false);
		txtTotalMorosos.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtTotalMorosos.setBounds(15, 350, 180, 28);
		panelCentro.add(txtTotalMorosos);

		JLabel lblDeuda = new JLabel("Deuda Total por Cobrar:");
		lblDeuda.setBounds(220, 330, 200, 14);
		panelCentro.add(lblDeuda);

		txtDeudaGlobal = new JTextField();
		txtDeudaGlobal.setEditable(false);
		txtDeudaGlobal.setForeground(new Color(180, 0, 0));
		txtDeudaGlobal.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtDeudaGlobal.setBounds(220, 350, 220, 28);
		panelCentro.add(txtDeudaGlobal);

		JButton btnIrCaja = new JButton("Ir a Caja / Cobrar");
		btnIrCaja.setBackground(new Color(0, 136, 255));
		btnIrCaja.setForeground(Color.WHITE);
		btnIrCaja.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnIrCaja.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
				Facturacion fac = new Facturacion();
				fac.setVisible(true);
			}
		});
		btnIrCaja.setBounds(520, 350, 160, 28);
		panelCentro.add(btnIrCaja);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnCerrar.setBounds(695, 350, 115, 28);
		panelCentro.add(btnCerrar);

		cargarDeudores();
	}

	private void cargarDeudores()
	{
		model.setRowCount(0);
		int morosos = 0;
		float deudaTotal = 0.0f;

		for (int i = 0; i < Altice.getInstance().getClientes().size(); i++)
		{
			Cliente c = Altice.getInstance().getClientes().get(i);
			if (c.getDeuda() > 0 || c.getFacturaPendientes() > 0)
			{
				Object[] fila = new Object[7];
				fila[0] = c.getId();
				fila[1] = c.getNombre();
				fila[2] = c.getCedula();
				fila[3] = c.getTelefono();
				fila[4] = c.getFacturaPendientes();
				fila[5] = String.format("RD$ %.2f", c.getDeuda());
				fila[6] = c.isActivo() ? "Activo (En Mora)" : "SUSPENDIDO (>2 Fact.)";
				model.addRow(fila);

				morosos++;
				deudaTotal += c.getDeuda();
			}
		}

		txtTotalMorosos.setText(String.valueOf(morosos));
		txtDeudaGlobal.setText(String.format("RD$ %.2f", deudaTotal));
	}
}