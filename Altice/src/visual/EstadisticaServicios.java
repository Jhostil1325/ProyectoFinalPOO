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

import logico.HiloEstadisticaServicios;

public class EstadisticaServicios extends JDialog
{
	private static final long serialVersionUID = 1L;
	private int cantInternet = 0;
	private int cantTv = 0;
	private int cantTelefonia = 0;

	private JLabel lblEstado;
	private JPanel panelGrafico;
	private HiloEstadisticaServicios hilo;

	public EstadisticaServicios()
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

		setTitle("Altice - Estad\u00EDstica de Servicios");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 482, 569);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(18, 18, 18));
		getContentPane().setLayout(null);

		JLabel lblTitulo = new JLabel("ALTICE | SERVICIOS SOLICITADOS");
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

				String[] etiquetas = { "Internet", "Televisi\u00F3n", "Telefon\u00EDa" };
				Color[] colores = { new Color(0, 168, 232), new Color(26, 188, 156), new Color(230, 126, 34) };
				float[] valores = { cantInternet, cantTv, cantTelefonia };
				float total = cantInternet + cantTv + cantTelefonia;

				int diametro = 195;
				int x = (getWidth() - diametro) / 2;
				int y = 25;

				if (total == 0)
				{
					g2.setColor(new Color(70, 70, 70));
					g2.fillOval(x, y, diametro, diametro);
					g2.setColor(Color.LIGHT_GRAY);
					g2.setFont(new Font("Tahoma", Font.PLAIN, 13));
					g2.drawString("Sin contratos registrados", x + 28, y + 102);
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
		panelGrafico.setBackground(new Color(28, 28, 28));
		panelGrafico.setBorder(new LineBorder(new Color(55, 55, 55), 1));
		panelGrafico.setBounds(25, 55, 424, 365);
		getContentPane().add(panelGrafico);

		lblEstado = new JLabel("Procesando contratos...");
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
		btnActualizar.setBounds(115, 469, 115, 28);
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
		btnCerrar.setBounds(245, 469, 115, 28);
		getContentPane().add(btnCerrar);

		if (!Beans.isDesignTime())
		{
			iniciarHilo();
		}
	}

	private void iniciarHilo()
	{
		if (hilo != null)
		{
			hilo.detener();
		}
		cantInternet = 0;
		cantTv = 0;
		cantTelefonia = 0;
		panelGrafico.repaint();

		hilo = new HiloEstadisticaServicios(this);
		hilo.start();
	}

	public void actualizarGrafico(int internet, int tv, int telefonia, int procesados, int total)
	{
		this.cantInternet = internet;
		this.cantTv = tv;
		this.cantTelefonia = telefonia;

		if (total == 0)
		{
			lblEstado.setText("No hay contratos registrados.");
		}
		else if (procesados < total)
		{
			lblEstado.setText("Analizando contratos: " + procesados + " de " + total + "...");
		}
		else
		{
			lblEstado.setText("An\u00E1lisis completado (" + total + " contratos).");
		}

		panelGrafico.repaint();
	}
}