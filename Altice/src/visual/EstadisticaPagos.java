package visual;

import java.awt.BasicStroke;
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

import logico.HiloEstadisticaPagos;

public class EstadisticaPagos extends JDialog
{
	private static final long serialVersionUID = 1L;
	private float montoEfectivo = 0.0f;
	private float montoTarjeta = 0.0f;
	private float montoTransferencia = 0.0f;

	private JLabel lblEstado;
	private JPanel panelGrafico;
	private HiloEstadisticaPagos hilo;

	public EstadisticaPagos()
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

		setTitle("Altice - Estad\u00EDstica de Pagos");
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 480, 571);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setBackground(new Color(18, 18, 18));
		getContentPane().setLayout(null);

		JLabel lblTitulo = new JLabel("ALTICE | INGRESOS POR TIPO DE PAGO");
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

				String[] etiquetas = { "Efectivo", "Tarjeta", "Transferencia" };
				Color[] colores = { new Color(0, 168, 232), new Color(46, 204, 113), new Color(155, 89, 182) };
				float[] valores = { montoEfectivo, montoTarjeta, montoTransferencia };

				float maximo = 0;
				for (int i = 0; i < valores.length; i++)
				{
					if (valores[i] > maximo)
					{
						maximo = valores[i];
					}
				}

				int baseY = 305;
				int alturaMax = 225;
				int anchoBarra = 72;
				int espacio = 45;
				int inicioX = 58;

				g2.setColor(new Color(90, 90, 90));
				g2.setStroke(new BasicStroke(1.5f));
				g2.drawLine(35, baseY, getWidth() - 35, baseY);

				if (maximo == 0)
				{
					g2.setColor(Color.LIGHT_GRAY);
					g2.setFont(new Font("Tahoma", Font.PLAIN, 13));
					g2.drawString("Sin pagos registrados", 142, 180);
				}

				for (int i = 0; i < etiquetas.length; i++)
				{
					int x = inicioX + i * (anchoBarra + espacio);
					int altura = 0;
					if (maximo > 0)
					{
						altura = Math.round((valores[i] / maximo) * alturaMax);
					}

					g2.setColor(colores[i]);
					g2.fillRoundRect(x, baseY - altura, anchoBarra, altura, 8, 8);

					g2.setColor(Color.WHITE);
					g2.setFont(new Font("Tahoma", Font.BOLD, 12));
					String montoTexto = String.format("RD$%.0f", valores[i]);
					g2.drawString(montoTexto, x - 2, baseY - altura - 8);

					g2.setFont(new Font("Tahoma", Font.PLAIN, 13));
					g2.drawString(etiquetas[i], x + 2, baseY + 24);
				}
			}
		};
		panelGrafico.setBackground(new Color(28, 28, 28));
		panelGrafico.setBorder(new LineBorder(new Color(55, 55, 55), 1));
		panelGrafico.setBounds(25, 55, 424, 365);
		getContentPane().add(panelGrafico);

		lblEstado = new JLabel("Procesando pagos...");
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
		btnActualizar.setBounds(109, 475, 115, 28);
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
		btnCerrar.setBounds(247, 475, 115, 28);
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
		montoEfectivo = 0.0f;
		montoTarjeta = 0.0f;
		montoTransferencia = 0.0f;
		panelGrafico.repaint();

		hilo = new HiloEstadisticaPagos(this);
		hilo.start();
	}

	public void actualizarGrafico(float efectivo, float tarjeta, float transferencia, int procesados, int total)
	{
		this.montoEfectivo = efectivo;
		this.montoTarjeta = tarjeta;
		this.montoTransferencia = transferencia;

		if (total == 0)
		{
			lblEstado.setText("No hay pagos registrados.");
		}
		else if (procesados < total)
		{
			lblEstado.setText("Analizando pagos: " + procesados + " de " + total + "...");
		}
		else
		{
			lblEstado.setText("An\u00E1lisis completado (" + total + " pagos).");
		}

		panelGrafico.repaint();
	}
}