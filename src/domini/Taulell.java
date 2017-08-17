package domini;

import java.util.LinkedList;

import prova.GraellaInicial;

public class Taulell {
	private Casella[][] graella;
	private Coordenada error;
	public Taulell(boolean custom){
		graella = new Casella[9][9];
		try {
			emplenarGraella();
			GraellaInicial.crearGraella(this,custom);
		} catch (Exception e) {}
	}
	
	public void emplenarGraella() { 
		for(int x=0;x<9;x++){
			for(int y=0;y<9;y++){
				graella[x][y] = new Casella (); 
			}
		}
	}
	
	public void addValor(int x, int y, int valor, boolean esInicial) throws Exception{
		x = x-1;
		y = y-1;
		Coordenada coordenada = new Coordenada (x,y);
		
		if(valor == Casella.getPosBuida()){
			graella[x][y].buidar();
		} else {
			//Comprovar si el valor es repeteix
			LinkedList<Coordenada> columnes = coordenada.mateixesColumnes(); //Coordenades de la mateixa columna
			LinkedList<Coordenada> files = coordenada.mateixesFiles(); //Coordenades de la mateixa fila
			LinkedList<Coordenada> area = coordenada.mateixesArea(); //Coordenades de la mateixa regió
			for(Coordenada a: area){
				if(graella[a.getX()][a.getY()].getValor()==valor) {
					this.error=new Coordenada(a.getX(),a.getY());
					throw new Exception("ERROR: El valor "+valor+" ja existeix en aquesta regió (a la posició: "+(a.getX()+1)+", "+(a.getY()+1)+")");
				}
					
			}
			for(Coordenada c: columnes){
				if(graella[c.getX()][c.getY()].getValor()==valor){
					this.error=new Coordenada(c.getX(),c.getY());
					throw new Exception("ERROR: El valor "+valor+" ja existeix en aquesta columna (a la posició: "+(c.getX()+1)+", "+(c.getY()+1)+")");
				}
					
			}
			for(Coordenada f: files){
				if(graella[f.getX()][f.getY()].getValor()==valor) {
					this.error=new Coordenada(f.getX(),f.getY());
					throw new Exception("ERROR: La xifra "+valor+" ja existeix en aquesta fila (a la posició: "+(f.getX()+1)+", "+(f.getY()+1)+")");
				}		
				
			}
			
			//afegir el valor a la graella
			graella[x][y].setValor(valor, esInicial);
		}
	}
	
	public boolean isComplete() {
		for (int i = 0; i < graella.length; i++) {
			for (int j = 0; j < graella[i].length; j++) {
				if (graella[i][j].getValor() == Casella.getPosBuida())
					return false;
			}
	    } 
		return true;
	}
	public String toString(){
		int num = 1;
		String output = "     ";
		for (int i = 1; i <= 9; i++) {
			output += i + "   ";
			if (i == 3 || i == 6) {
				output += "|   ";
			}
		}
		output += '\n';
		output += "----------------------------------------------" + '\n';
		for (int i = 0; i < graella.length; i++) {
			output += num + "|   ";
			num++;
			for (int j = 0; j < graella[i].length; j++) {
				if(graella[i][j].getValor()!= Casella.getPosBuida()){
					output += graella[i][j].getValor()+"   "; 
				}
				else{ output += "?   "; }
				if (j == 2 || j == 5) {
					output += "|   ";
				}
			}
			output += '\n';
			if (i == 2 || i == 5)
				output += "----------------------------------------------" + '\n';
		}
		return output;
	}
	public Casella[][] getGraella(){
		return graella;
	}
	public void setGraella(Casella[][] graellaEquivalent){
		graella = graellaEquivalent;
	}
	public int getValor(int x,int y){
		return graella[x][y].getValor();
	}
	public int[][] getValors(){
		int[][] valors = new int[9][9];
		for (int i = 0; i < graella.length; i++) {
			for (int j = 0; j < graella[i].length; j++) {
				valors[i][j] = graella[i][j].getValor();
			}
	    } 
		return valors;
	}
	public int[] getError(){
		int[] error = new int[2];
		error[0]=this.error.getX();
		error[1]=this.error.getY();
		return error;	
	}
	public void nouEquivalent(){
		new GenerarEquivalent(graella);
	}
	public boolean getModificable(int x, int y){
		return graella[x][y].getModificable();
	}
	
	public void confirmarCaselles(){
		for (int i = 0; i < graella.length; i++) {
			for (int j = 0; j < graella[i].length; j++) {
				if(graella[i][j].getValor()!=Casella.getPosBuida())graella[i][j].setModificable(false);
			}
		}
	}
}
