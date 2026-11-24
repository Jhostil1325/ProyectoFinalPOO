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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Cliente;
import logico.Pago;
import logico.Persona;

public class MisFacturas extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtDeuda;

	public MisFacturas()
	{
		setTitle("Portal Cliente - Mis Facturas");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 840, 480);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));

		Persona user = Altice.getLoginUser();
		String nombre = (user != null) ? user.getNombre() : "Cliente";
		JLabel lblTitulo = new JLabel("ALTICE | ESTADO DE CUENTA Y FACTURAS DE " + nombre.toUpperCase());
		lblTitulo.setForeground(new Color(0, 168, 232));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelHeader.add(lblTitulo);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new EmptyBorder(10, 15, 10, 15));
		panelCentro.setLayout(null);
		getContentPane().add(panelCentro, BorderLayout.CENTER);

		String[] columnas = { "ID Factura", "Fecha", "Concepto", "M\u00E9todo", "ITBIS", "Total", "Estado" };
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
		scroll.setBounds(15, 15, 795, 305);
		panelCentro.add(scroll);

		JLabel lblDeuda = new JLabel("Balance Pendiente por Pagar:");
		lblDeuda.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDeuda.setBounds(15, 340, 210, 25);
		panelCentro.add(lblDeuda);

		txtDeuda = new JTextField();
		txtDeuda.setEditable(false);
		txtDeuda.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtDeuda.setForeground(new Color(180, 0, 0));
		txtDeuda.setBounds(225, 340, 180, 28);
		panelCentro.add(txtDeuda);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnCerrar.setBounds(690, 340, 120, 28);
		panelCentro.add(btnCerrar);

		cargarFacturas();
	}

	private void cargarFacturas()
	{
		model.setRowCount(0);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Persona user = Altice.getLoginUser();
		ArrayList<Pago> lista = (user instanceof Cliente) ? ((Cliente) user).getMisPagos()
				: Altice.getInstance().getMisPagos();

		float pendiente = 0.0f;
		for (int i = 0; i < lista.size(); i++)
		{
			Pago p = lista.get(i);
			Object[] fila = new Object[7];
			fila[0] = p.getId();
			fila[1] = p.getFechaPago() != null ? sdf.format(p.getFechaPago()) : "N/A";
			fila[2] = p.getMiContrato() != null ? "Mensualidad " + p.getMiContrato().getId() : "Compra Equipo";
			fila[3] = p.getMetodoPago();
			fila[4] = String.format("RD$ %.2f", p.getItbis());
			fila[5] = String.format("RD$ %.2f", p.getTotalPago());
			fila[6] = p.isPagada() ? "Pagada" : "PENDIENTE";
			model.addRow(fila);

			if (!p.isPagada())
			{
				pendiente += p.getTotalPago();
			}
		}

		txtDeuda.setText(String.format("RD$ %.2f", pendiente));
	}
}