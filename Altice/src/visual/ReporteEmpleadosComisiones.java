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

import logico.Administrativo;
import logico.Altice;
import logico.Comercial;
import logico.Empleado;

public class ReporteEmpleadosComisiones extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtTotalComisiones;
	private JTextField txtTotalNomina;

	public ReporteEmpleadosComisiones()
	{
		setTitle("Reporte - Empleados y Comisiones");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 920, 480);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
		JLabel lblTitulo = new JLabel("ALTICE | N\u00D3MINA, METAS Y COMISIONES DEL PERSONAL");
		lblTitulo.setForeground(new Color(0, 168, 232));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelHeader.add(lblTitulo);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new EmptyBorder(10, 15, 10, 15));
		panelCentro.setLayout(null);
		getContentPane().add(panelCentro, BorderLayout.CENTER);

		String[] columnas = { "ID", "Empleado", "Rol", "Local", "Sueldo Base", "Comisi\u00F3n Mes", "Total a Pagar", "Meta / Cargo" };
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
		scroll.setBounds(15, 15, 875, 300);
		panelCentro.add(scroll);

		JLabel lblCom = new JLabel("Total Comisiones del Mes:");
		lblCom.setBounds(15, 330, 200, 14);
		panelCentro.add(lblCom);

		txtTotalComisiones = new JTextField();
		txtTotalComisiones.setEditable(false);
		txtTotalComisiones.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtTotalComisiones.setBounds(15, 350, 210, 28);
		panelCentro.add(txtTotalComisiones);

		JLabel lblNomina = new JLabel("Total N\u00F3mina Mensual:");
		lblNomina.setBounds(250, 330, 200, 14);
		panelCentro.add(lblNomina);

		txtTotalNomina = new JTextField();
		txtTotalNomina.setEditable(false);
		txtTotalNomina.setForeground(new Color(0, 102, 204));
		txtTotalNomina.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtTotalNomina.setBounds(250, 350, 230, 28);
		panelCentro.add(txtTotalNomina);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		});
		btnCerrar.setBounds(770, 350, 120, 28);
		panelCentro.add(btnCerrar);

		cargarNomina();
	}

	private void cargarNomina()
	{
		model.setRowCount(0);
		float sumaComisiones = 0.0f;
		float sumaNomina = 0.0f;

		for (int i = 0; i < Altice.getInstance().getEmpleados().size(); i++)
		{
			Empleado emp = Altice.getInstance().getEmpleados().get(i);
			if (emp.getUsuario() != null && emp.getUsuario().equalsIgnoreCase("Admin"))
			{
				continue;
			}

			float comisionMes = 0.0f;
			String detalle = "";

			if (emp instanceof Comercial)
			{
				Comercial com = (Comercial) emp;
				comisionMes = Altice.getInstance().calcularComisionMensual(com);
				detalle = String.format("Meta: RD$ %.2f", com.getMetaVentas());
			}
			else if (emp instanceof Administrativo)
			{
				detalle = "Cargo: " + ((Administrativo) emp).getCargo();
			}

			float totalPagar = emp.getSueldobase() + comisionMes;

			Object[] fila = new Object[8];
			fila[0] = emp.getId();
			fila[1] = emp.getNombre();
			fila[2] = Altice.getInstance().obtenerTipoPersona(emp);
			fila[3] = emp.getMiLocal();
			fila[4] = String.format("RD$ %.2f", emp.getSueldobase());
			fila[5] = String.format("RD$ %.2f", comisionMes);
			fila[6] = String.format("RD$ %.2f", totalPagar);
			fila[7] = detalle;
			model.addRow(fila);

			sumaComisiones += comisionMes;
			sumaNomina += totalPagar;
		}

		txtTotalComisiones.setText(String.format("RD$ %.2f", sumaComisiones));
		txtTotalNomina.setText(String.format("RD$ %.2f", sumaNomina));
	}
}