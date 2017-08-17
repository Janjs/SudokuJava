package aplicacio;

import domini.Casella;
import domini.GenerarEquivalent;
import domini.Taulell;

public class ControlJoc {
	private Taulell t;
	private ControlBBDD controlBBDD;
	public ControlJoc(ControlBBDD controlBBDD){
		this.controlBBDD = controlBBDD;
	}
	public ControlJoc(boolean custom){	// Deprecated constructor
		this.generarTaulell(custom);
	}
	public ControlJoc(Taulell t){
		this.t = t;
	}
	public void afegirNouValor(int x, int y, int valor) throws Exception{
		t.addValor(x, y, valor, false);
	}	
	public void afegirNouValor(int x, int y) throws Exception{
		t.addValor(x, y, Casella.getPosBuida(), false);
	}	
	public int getValor(int x,int y){
		return t.getValor(x, y);
	}
	public String getToStringTaulell(){return t.toString();}
	public boolean jocCompletat(){return t.isComplete();}
	public int[][] getValorsGraella(){return t.getValors();}
	public int[] getErrorTaulell(){
		return t.getError();
	}
	public void generarEquivalent(){
		new GenerarEquivalent(t.getGraella());
	}
	public void confirmarCaselles(){
		t.confirmarCaselles();
	}
	public void setTaulell(Taulell t){
		this.t = t;
	}
	public boolean getModificable(int x, int y) { return this.t.getModificable(x, y); }
	public Taulell getTaulell(){ return this.t; }
	public void generarTaulell(boolean custom){
		t = new Taulell(custom);
		this.controlBBDD.setTaulell(this.t);
	}
	public void carregarTaulell(){
		this.t = controlBBDD.getTaulell();
	}
}
