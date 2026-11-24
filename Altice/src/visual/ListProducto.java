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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Altice;
import logico.Producto;

public class ListProducto extends JDialog
{
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private JTable table;

	public ListProducto()
	{
		setTitle("Inventario de Productos");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 840, 470);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(new Color(18, 18, 18));
		panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
		JLabel lblTitulo = new JLabel("ALTICE | INVENTARIO GENERAL DE EQUIPOS Y PRODUCTOS");
		lblTitulo.setForeground(new Color(0, 168, 232));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		panelHeader.add(lblTitulo);
		getContentPane().add(panelHeader, BorderLayout.NORTH);

		String[] columnas = { "ID", "Descripci\u00F3n", "Marca", "Categor\u00EDa", "Precio", "Stock", "Estado" };
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

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
		panelBotones.setBackground(new Color(18, 18, 18));

		JButton btnNuevo = new JButton("Nuevo Producto");
		btnNuevo.setBackground(new Color(0, 136, 255));
		btnNuevo.setForeground(Color.WHITE);
		btnNuevo.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNuevo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				RegProducto reg = new RegProducto();
				reg.setVisible(true);
				cargarProductos();
			}
		});
		panelBotones.add(btnNuevo);

		JButton btnReponer = new JButton("Reponer Stock (+)");
		btnReponer.setBackground(new Color(0, 168, 232));
		btnReponer.setForeground(Color.WHITE);
		btnReponer.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnReponer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				reponerStock();
			}
		});
		panelBotones.add(btnReponer);

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
		cargarProductos();
	}

	private void cargarProductos()
	{
		model.setRowCount(0);
		for (int i = 0; i < Altice.getInstance().getMisProductos().size(); i++)
		{
			Producto p = Altice.getInstance().getMisProductos().get(i);
			Object[] fila = new Object[7];
			fila[0] = p.getId();
			fila[1] = p.getDescripcion();
			fila[2] = p.getMarca();
			fila[3] = p.getTipoProducto();
			fila[4] = String.format("RD$ %.2f", p.getPrecio());
			fila[5] = p.getStock();
			fila[6] = p.getStock() > 0 ? "Disponible" : "Agotado";
			model.addRow(fila);
		}
	}

	private void reponerStock()
	{
		int row = table.getSelectedRow();
		if (row < 0)
		{
			JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		String input = JOptionPane.showInputDialog(this, "Cantidad de unidades a ingresar al inventario:", "Reponer Stock",
				JOptionPane.QUESTION_MESSAGE);
		if (input == null || input.trim().isEmpty())
		{
			return;
		}

		try
		{
			int cant = Integer.parseInt(input.trim());
			if (cant <= 0)
			{
				throw new NumberFormatException();
			}
			Producto p = Altice.getInstance().getMisProductos().get(row);
			p.setStock(p.getStock() + cant);
			p.setDisponible(true);
			cargarProductos();
			JOptionPane.showMessageDialog(this, "Stock actualizado con \u00E9xito.", "Inventario",
					JOptionPane.INFORMATION_MESSAGE);
		}
		catch (NumberFormatException ex)
		{
			JOptionPane.showMessageDialog(this, "Ingrese una cantidad entera positiva.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}