package logico;

import java.util.ArrayList;

import visual.EstadisticaPagos;

public class HiloEstadisticaPagos extends Thread
{
	private EstadisticaPagos ventana;
	private boolean activo;

	public HiloEstadisticaPagos(EstadisticaPagos ventana)
	{
		super();
		this.ventana = ventana;
		this.activo = true;
	}

	@Override
	public void run()
	{
		float efectivo = 0.0f;
		float tarjeta = 0.0f;
		float transferencia = 0.0f;

		ArrayList<Pago> pagos = Altice.getInstance().getMisPagos();

		for (int i = 0; i < pagos.size(); i++)
		{
			if (!activo)
			{
				break;
			}

			Pago p = pagos.get(i);
			if (p.isPagada() && p.getMetodoPago() != null)
			{
				String metodo = p.getMetodoPago().trim();
				if (metodo.equalsIgnoreCase("Efectivo"))
				{
					efectivo += p.getTotalPago();
				}
				else if (metodo.equalsIgnoreCase("Tarjeta"))
				{
					tarjeta += p.getTotalPago();
				}
				else if (metodo.equalsIgnoreCase("Transferencia"))
				{
					transferencia += p.getTotalPago();
				}
			}

			ventana.actualizarGrafico(efectivo, tarjeta, transferencia, i + 1, pagos.size());
			esperarMilisegundos(120);
		}

		if (activo && pagos.size() == 0)
		{
			ventana.actualizarGrafico(0.0f, 0.0f, 0.0f, 0, 0);
		}
	}

	private void esperarMilisegundos(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void detener()
	{
		this.activo = false;
	}
}