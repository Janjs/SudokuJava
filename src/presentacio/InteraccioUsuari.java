package presentacio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import aplicacio.ControlJoc;

public class InteraccioUsuari {
	private  ControlJoc sudoku = new ControlJoc(false);	
	private  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private boolean acabarJoc = false;
	
	public static void main(String[] args) {
		new InteraccioUsuari().iniciJoc();
	}
	
	private void iniciJoc () {
		do {
			mostrarUsuari();
			llegirUsuari();
			sudoku.generarEquivalent();
		} while (!sudoku.jocCompletat()&&!acabarJoc);
		if(!acabarJoc){
			System.out.println("FI JOC. Felicitats, has emplenat totes les cel·les"); mostrarUsuari();
		}
		else System.out.println("FI JOC. Has finalitzat el joc");
	}
	private  void llegirUsuari(){
		String input;
		try {	
			input = br.readLine();
			if(!(input.length()==5||input.length()==2)) throw new IllegalArgumentException("ERROR: Format de dades incorrecte");
			//Comprovar si és vol acabar el joc
			if(input.length()==2){
				if(!(input.charAt(0)=='-'&&input.charAt(1)=='1')) throw new IllegalArgumentException("ERROR: Format de dades incorrecte");
				acabarJoc = true;
			} else {
				if(!(input.charAt(1)==','&&input.charAt(3)==',')) throw new IllegalArgumentException("ERROR: Format de dades incorrecte");
				int inputFila = (int)Character.getNumericValue(input.charAt(0));
				int inputCol = (int)Character.getNumericValue(input.charAt(2));
				int inputValor = (int)Character.getNumericValue(input.charAt(4));
				try{
					sudoku.afegirNouValor(inputFila,inputCol,inputValor);
				}catch(Exception e){System.out.println(e.getMessage());}
				System.out.println("");
			}
		} catch (IOException e) {
			System.out.println("Ha sorgit un problema inesperat amb la lectura de la linia. \n" + e.getMessage()+ '\n');
		}
		  catch (IllegalArgumentException e) {
			System.out.println("Ha sorgit un problema amb la lectura de dades. El format no es correcte o la posició indicada no es permesa. \n " + e.getMessage()+'\n');
	 	}	
	}
    private	void mostrarUsuari(){
		System.out.println(sudoku.getToStringTaulell());
		System.out.println(
				"Entra la fila, columna i numero a afegir separats per una coma. \n Exemple:   2,3,5 \n Entra un 0 com a ultim valor per esborrar la posicio indicada. \n -1 per finalitzar el joc");
		System.out.println("");
	}
}
