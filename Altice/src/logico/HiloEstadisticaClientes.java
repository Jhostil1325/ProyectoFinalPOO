package logico;

import java.util.ArrayList;

import visual.EstadisticaClientes;

public class HiloEstadisticaClientes extends Thread
{
	private EstadisticaClientes ventana;
	private boolean activo;

	public HiloEstadisticaClientes(EstadisticaClientes ventana)
	{
		super();
		this.ventana = ventana;
		this.activo = true;
	}

	@Override
	public void run()
	{
		int alDia = 0;
		int pendiente = 0;
		int suspendido = 0;

		ArrayList<Cliente> clientes = Altice.getInstance().getClientes();

		for (int i = 0; i < clientes.size(); i++)
		{
			if (!activo)
			{
				break;
			}

			Cliente c = clientes.get(i);
			if (!c.isActivo() || c.getFacturaPendientes() > 2)
			{
				suspendido++;
			}
			else if (c.getFacturaPendientes() > 0 || c.getDeuda() > 0)
			{
				pendiente++;
			}
			else
			{
				alDia++;
			}

			ventana.actualizarGrafico(alDia, pendiente, suspendido, i + 1, clientes.size());
			esperarMilisegundos(120);
		}

		if (activo && clientes.size() == 0)
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