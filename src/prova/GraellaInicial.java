package prova;

import aplicacio.GenerarGraellaInicial;
import domini.Taulell;

public class GraellaInicial extends GenerarGraellaInicial{
	public void iniciarGraella(Taulell t,boolean custom) throws Exception{
		
		if(!custom){
		t.addValor(1, 1, 5, true);
		t.addValor(1, 2, 3, true);
		t.addValor(1, 5, 7, true);
		t.addValor(2, 1, 6, true);
		t.addValor(2, 4, 1, true);
		t.addValor(2, 5, 9, true);
		t.addValor(2, 6, 5, true);
		t.addValor(3, 2, 9, true);
		t.addValor(3, 3, 8, true);
		t.addValor(3, 8, 6, true);
		t.addValor(4, 1, 8, true);
		t.addValor(4, 5, 6, true);
		t.addValor(4, 9, 3, true);
		t.addValor(5, 1, 4, true);
		t.addValor(5, 4, 8, true);
		t.addValor(5, 6, 3, true);
		t.addValor(5, 9, 1, true);
		t.addValor(6, 1, 7, true);
		t.addValor(6, 5, 2, true);
		t.addValor(6, 9, 6, true);
		t.addValor(7, 2, 6, true);
		t.addValor(7, 7, 2, true);
		t.addValor(7, 8, 8, true);
		t.addValor(8, 4, 4, true);
		t.addValor(8, 5, 1, true);
		t.addValor(8, 6, 9, true);
		t.addValor(8, 9, 5, true);
		t.addValor(9, 5, 8, true);
		t.addValor(9, 8, 7, true);
		t.addValor(9, 9, 9, true);
		}
	}
}
