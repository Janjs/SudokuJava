package domini;

public class Casella {
	
	private int valor;
	private boolean modificable;
	private static final int POS_BUIDA = 0;
	
	public Casella() {
		this.valor = POS_BUIDA;
		this.modificable = true;
	}
	
	public void setValor(int nouValor, boolean esInicial) throws Exception{
		if(nouValor<1||nouValor>9) throw new Exception("ERROR: El format del valor és incorrecte");
		if (!this.modificable)throw new Exception("ERROR: La casella no es modificable");
		this.valor = nouValor;
		this.modificable = !esInicial;
	}
	
	public void buidar() throws Exception{
		if (!this.modificable)	throw new Exception("ERROR: La casella no es modificable");
		this.valor = POS_BUIDA;
	}
	
	public int getValor(){
		return this.valor;
		}
	public boolean getModificable(){return this.modificable;}
	public void setModificable(boolean modificable){this.modificable=modificable;}
	public static int getPosBuida() {
		return POS_BUIDA;
	}
}
