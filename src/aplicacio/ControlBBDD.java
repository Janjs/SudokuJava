package aplicacio;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import domini.Jugador;
import domini.Sudoku;
import domini.Taulell;
import persistencia.*;

public class ControlBBDD {
	
	private JugadorBBDD jugadorBBDD = new JugadorBBDD();
	private SudokuBBDD sudokuBBDD = new SudokuBBDD();
	private CasellaBBDD casellaBBDD = new CasellaBBDD();
	
	private Jugador jugador;
	private Sudoku sudoku;
	private Map<Integer,Date> partides;
	private boolean online = false;

	public void login() throws Exception {
		ConnectionBBDD.getInstance();
	}
	
	public synchronized void initPlayer(String username)throws Exception{
		jugador = new Jugador(username,true);
		if(!this.existsPlayer(jugador)){
			createJugador(jugador);
			partides = jugadorBBDD.getTimestamps(jugador);
		}
		else if(jugadorBBDD.isPlaying(jugador)) throw new IllegalStateException("El jugador ja está jugant");
		else{
			jugadorBBDD.setPlaying(jugador);
			partides = jugadorBBDD.getTimestamps(jugador);
		}	
	}
	
	public void initPartida(int id) throws Exception{
		if(this.jugador == null) throw new IllegalStateException("No s'ha carregat el jugador");
		if(id == 0){
			id = 1;
			while(partides.keySet().contains(id)) id++;
			Calendar calendar = Calendar.getInstance();
			java.util.Date now = calendar.getTime();
			this.sudoku = new Sudoku(id,new java.sql.Timestamp(now.getTime()),this.jugador);
			this.sudoku.setNew(true);
		}
		else{
			this.sudoku = new Sudoku(id,partides.get(id),this.jugador);
			this.sudoku.setNew(false);
			sudoku.setTaulell(casellaBBDD.getTaulell(this.sudoku));
		}
	}
	
	public Map<Integer,Date> partides() throws Exception{
		if(this.jugador == null) throw new IllegalStateException("No s'ha carregat el jugador");
		return this.jugadorBBDD.getTimestamps(this.jugador);
	}
	public Taulell getTaulell(){
		if(this.sudoku == null) throw new IllegalStateException("No s'ha carregat la partida");
		return sudoku.getTaulell(); 
		}
	public int gameCount(){
		return partides.values().size();
	}
	public int idPartidaAnterior(){
		for(Integer i : partides.keySet()) return i;
		throw new IllegalStateException("No hi han partides");
	}

	public boolean existsPlayer (Jugador jugador) throws Exception{
		return this.jugadorBBDD.exists(jugador);
	}

	public void createJugador(Jugador jugador) throws Exception{
		jugadorBBDD.insert(jugador);
	}

	public void guardarSudoku() throws Exception{
		if(this.sudoku == null) throw new IllegalStateException("No s'ha carregat la partida");
		if(this.isNew()) sudokuBBDD.insert(sudoku);
		else sudokuBBDD.update(sudoku);
	}
	public void deleteSudoku() throws Exception{
		sudokuBBDD.delete(this.sudoku);
	}
	public boolean playerInitialized(){
		return this.jugador != null;
	}

	public void setTaulell(Taulell t) {
		if (online) {
			if (this.sudoku == null)
				throw new IllegalStateException("No s'ha inicialitzat la partida");
			this.sudoku.setTaulell(t);
		}
	}

	public boolean isNew() {
		if(this.sudoku == null) return true;
		return this.sudoku.isNew();
	}
	public void setNew(boolean isNew){
		if(this.sudoku == null) throw new IllegalStateException("No s'ha inicialitzat la partida");
		this.sudoku.isNew();
	}
	public void finalitzar() throws Exception{
		this.jugador.setIsPlaying(false);
		this.jugadorBBDD.setPlaying(this.jugador);
	}
	public void setOnline(boolean online) { this.online = online; }
	public boolean isOnline(){ return this.online; }
}
