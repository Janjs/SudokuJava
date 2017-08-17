package domini;

public class Jugador {
	private String nom;
	private boolean isPlaying;
	
	public Jugador(String nom, boolean isPlaying){
		this.nom = nom;
		this.isPlaying = isPlaying;
	}
	
	public String getNom() {
		return nom;
	}
	public boolean isPlaying(){
		return this.isPlaying;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setIsPlaying(boolean isPlaying){
		this.isPlaying = isPlaying;
	}
}
