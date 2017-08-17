package aplicacio;

import domini.Taulell;
import prova.GraellaInicial;

public abstract class GenerarGraellaInicial {
	public static void crearGraella(Taulell t,boolean custom) throws Exception{
		GraellaInicial g = new GraellaInicial();
		g.iniciarGraella(t,custom);
	}
	public abstract void iniciarGraella(Taulell t,boolean custom) throws Exception;
}
