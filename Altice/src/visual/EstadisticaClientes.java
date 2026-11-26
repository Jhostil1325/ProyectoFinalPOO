package visual;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.Beans;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import logico.HiloEstadisticaClientes;

public class EstadisticaClientes extends JDialog
{
	private static final long serialVersionUID = 1L;
	private int cantAlDia = 0;
	private int cantPendiente = 0;
	private int cantSuspendido = 0;

	private JLabel lblEstado;
	private JPanel panelGrafico;
	private HiloEstadisticaClientes hilo;

	public EstadisticaClientes()
	{
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				if (hilo != null)
				{
					hilo.detener();
				}
			}
		});

		setTitle("Altice - Estad\u00EDstica de Clientes");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 480, 551);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(18, 18, 18));
		getContentPane().setLayout(null);

		JLabel lblTitulo = new JLabel("ALTICE | CLIENTES AL D\u00CDA");
		lblTitulo.setForeground(new Color(0, 168, 232));
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setBounds(25, 18, 404, 35);
		getContentPane().add(lblTitulo);

		panelGrafico = new JPanel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				String[] etiquetas = { "Clientes al d\u00EDa", "Con factura pendiente", "Suspendidos por deuda" };
				Color[] colores = { new Color(46, 204, 113), new Color(241, 196, 15), new Color(231, 76, 60) };
				float[] valores = { cantAlDia, cantPendiente, cantSuspendido };
				float total = cantAlDia + cantPendiente + cantSuspendido;

				int diametro = 195;
				int x = (getWidth() - diametro) / 2;
				int y = 25;

				if (total == 0)
				{
					g2.setColor(new Color(70, 70, 70));
					g2.fillOval(x, y, diametro, diametro);
					g2.setColor(Color.LIGHT_GRAY);
					g2.setFont(new Font("Tahoma", Font.PLAIN, 13));
					g2.drawString("Sin clientes registrados", x + 32, y + 102);
				}
				else
				{
					int anguloInicio = 90;
					for (int i = 0; i < valores.length; i++)
					{
						int barrido = Math.round((valores[i] / total) * 360.0f);
						if (i == valores.length - 1 && barrido > 0)
						{
							barrido = 360 - (90 - anguloInicio);
						}
						if (barrido > 0)
						{
							g2.setColor(colores[i]);
							g2.fillArc(x, y, diametro, diametro, anguloInicio, -barrido);
							anguloInicio -= barrido;
						}
					}
				}

				int yLeyenda = 255;
				g2.setFont(new Font("Tahoma", Font.PLAIN, 14));
				for (int i = 0; i < etiquetas.length; i++)
				{
					g2.setColor(colores[i]);
					g2.fillRoundRect(45, yLeyenda - 12, 15, 15, 4, 4);

					float porcentaje = (total > 0) ? (valores[i] / total) * 100.0f : 0.0f;
					String texto = etiquetas[i] + ": " + (int) valores[i] + String.format(" (%.1f%%)", porcentaje);
					g2.setColor(Color.WHITE);
					g2.drawString(texto, 72, yLeyenda);
					yLeyenda += 32;
				}
			}
		};
		panelGrafico.setBackground(new Color(18, 18, 18));
		panelGrafico.setBorder(new LineBorder(new Color(55, 55, 55), 1));
		panelGrafico.setBounds(25, 50, 424, 365);
		getContentPane().add(panelGrafico);

		lblEstado = new JLabel("Procesando clientes...");
		lblEstado.setForeground(new Color(180, 180, 180));
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEstado.setBounds(25, 428, 424, 20);
		getContentPane().add(lblEstado);

		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnActualizar.setBackground(new Color(0, 168, 232));
		btnActualizar.setForeground(Color.WHITE);
		btnActualizar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				iniciarHilo();
			}
		});
		btnActualizar.setBounds(115, 458, 115, 28);
		getContentPane().add(btnActualizar);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCerrar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (hilo != null)
				{
					hilo.detener();
				}
				dispose();
			}
		});
		btnCerrar.setBounds(245, 458, 115, 28);
		getContentPane().add(btnCerrar);

		if (!Beans.isDesignTime())
		{
			iniciarHilo();
		}
	}

	private void iniciarHilo()
	{
		if (hilo != null && hilo.isAlive())
		{
			hilo.detener();
		}
		cantAlDia = 0;
		cantPendiente = 0;
		cantSuspendido = 0;
		panelGrafico.repaint();

		hilo = new HiloEstadisticaClientes(this);
		hilo.start();
	}

	public void actualizarGrafico(int alDia, int pendiente, int suspendido, int procesados, int total)
	{
		this.cantAlDia = alDia;
		this.cantPendiente = pendiente;
		this.cantSuspendido = suspendido;

		if (total == 0)
		{
			lblEstado.setText("No hay clientes registrados.");
		}
		else if (procesados < total)
		{
			lblEstado.setText("Analizando clientes: " + procesados + " de " + total + "...");
		}
		else
		{
			lblEstado.setText("An\u00E1lisis completado (" + total + " clientes).");
		}

		panelGrafico.repaint();
	}
}