package domini;

import java.util.Date;

public class Sudoku {
	private int idPartida;
	private Date date;
	private Taulell taulell;
	private Jugador jugador;
	private boolean isNew;
	
	
	public Sudoku(int idPartida,Date date, Jugador jugador) {
		this.idPartida = idPartida;
		this.date = date;
		this.jugador = jugador;
	}
	
	public int getIdPartida() {
		return idPartida;
	}
	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}
	public Date getDate() {
		return date;
	}
	public long getTime(){
		return this.date.getTime();
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public Taulell getTaulell() {
		return taulell;
	}

	public void setTaulell(Taulell taulell) {
		this.taulell = taulell;
	}

	public Jugador getJugador() {
		return jugador;
	}
	
	public String getNomJugador(){
		return jugador.getNom();
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Casella[][] getGraella() {
		return this.taulell.getGraella();
	}
	public boolean isNew(){ return this.isNew; }
	public void setNew(boolean isNew){this.isNew = isNew; }
}
