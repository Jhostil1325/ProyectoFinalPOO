package logico;

import java.util.ArrayList;

import visual.EstadisticaServicios;

public class HiloEstadisticaServicios extends Thread
{
	private EstadisticaServicios ventana;
	private boolean activo;

	public HiloEstadisticaServicios(EstadisticaServicios ventana)
	{
		super();
		this.ventana = ventana;
		this.activo = true;
	}

	@Override
	public void run()
	{
		int internet = 0;
		int tv = 0;
		int telefonia = 0;

		ArrayList<Contrato> contratos = Altice.getInstance().getMisContratos();

		for (int i = 0; i < contratos.size(); i++)
		{
			if (!activo)
			{
				break;
			}

			Plan plan = contratos.get(i).getMiPlan();
			if (plan != null && plan.getMisServicios() != null)
			{
				for (int j = 0; j < plan.getMisServicios().size(); j++)
				{
					Servicio s = plan.getMisServicios().get(j);
					if (s instanceof Internet)
					{
						internet++;
					}
					else if (s instanceof Television)
					{
						tv++;
					}
					else if (s instanceof Telefonia)
					{
						telefonia++;
					}
				}
			}

			ventana.actualizarGrafico(internet, tv, telefonia, i + 1, contratos.size());
			esperarMilisegundos(120);
		}

		if (activo && contratos.size() == 0)
		{
			ventana.actualizarGrafico(0, 0, 0, 0, 0);
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