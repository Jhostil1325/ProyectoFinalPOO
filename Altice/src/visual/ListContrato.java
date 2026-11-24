package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Administrativo;
import logico.Altice;
import logico.Contrato;
import logico.Plan;

public class ListContrato extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;

	public ListContrato()
	{
		setTitle("Listar Contratos");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 820, 460);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));

		JLabel lblTitulo = new JLabel("ALTICE | LISTADO DE CONTRATOS");
		lblTitulo.setForeground(new Color(0, 168, 232));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelHeader.add(lblTitulo);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

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
		table.getTableHeader().setBackground(new Color(30, 30, 30));
		table.getTableHeader().setForeground(Color.WHITE);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(new EmptyBorder(10, 15, 5, 15));
		getContentPane().add(scroll, BorderLayout.CENTER);

		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setEnabled(esAdministrador());
		if (!esAdministrador())
		{
			btnModificar.setToolTipText("Solo un administrador puede modificar contratos");
		}
		btnModificar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				modificarContrato();
			}
		});
		panelSur.add(btnModificar);

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

	private boolean esAdministrador()
	{
		return Altice.getLoginUser() instanceof Administrativo;
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

	private void modificarContrato()
	{
		if (!esAdministrador())
		{
			JOptionPane.showMessageDialog(this, "Solo un administrador puede modificar contratos.", "Acceso denegado",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		int row = table.getSelectedRow();
		if (row < 0)
		{
			JOptionPane.showMessageDialog(this, "Seleccione un contrato de la tabla.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		Contrato contrato = Altice.getInstance().getMisContratos().get(row);

		ArrayList<Plan> planes = new ArrayList<Plan>();
		JComboBox<String> cbxPlan = new JComboBox<String>();
		int seleccionado = 0;
		for (int i = 0; i < Altice.getInstance().getMisPlanes().size(); i++)
		{
			Plan p = Altice.getInstance().getMisPlanes().get(i);
			if (p.isEstado() || p == contrato.getMiPlan())
			{
				if (p == contrato.getMiPlan())
				{
					seleccionado = planes.size();
				}
				planes.add(p);
				cbxPlan.addItem(p.getId() + " - RD$ " + String.format("%.2f", p.getPrecioMensual()));
			}
		}
		cbxPlan.setSelectedIndex(seleccionado);

		JComboBox<String> cbxMetodo = new JComboBox<String>(new String[] { "Efectivo", "Tarjeta", "Transferencia" });
		cbxMetodo.setSelectedItem(contrato.getMetodoPago());

		JComboBox<String> cbxEstado = new JComboBox<String>(new String[] { "Activo", "Suspendido" });
		cbxEstado.setSelectedIndex(contrato.isActivo() ? 0 : 1);

		JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
		panel.add(new JLabel("ID Contrato:"));
		panel.add(new JLabel(contrato.getId()));
		panel.add(new JLabel("Cliente:"));
		panel.add(new JLabel(contrato.getMiCliente().getNombre()));
		panel.add(new JLabel("Plan:"));
		panel.add(cbxPlan);
		panel.add(new JLabel("M\u00E9todo de pago:"));
		panel.add(cbxMetodo);
		panel.add(new JLabel("Estado:"));
		panel.add(cbxEstado);

		int opcion = JOptionPane.showConfirmDialog(this, panel, "Modificar Contrato", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (opcion != JOptionPane.OK_OPTION)
		{
			return;
		}

		Plan plan = planes.get(cbxPlan.getSelectedIndex());
		String metodo = cbxMetodo.getSelectedItem().toString();
		boolean activo = cbxEstado.getSelectedIndex() == 0;

		Altice.getInstance().modificarContrato(contrato, plan, metodo, activo);
		JOptionPane.showMessageDialog(this, "Contrato modificado con \u00E9xito.", "Modificar",
				JOptionPane.INFORMATION_MESSAGE);
		cargarContratos();
	}
}
