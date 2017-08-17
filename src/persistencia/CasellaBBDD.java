package persistencia;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import domini.Casella;
import domini.Sudoku;
import domini.Taulell;
public class CasellaBBDD {
	
	public void updateCaselles(Sudoku sudoku) throws Exception {
		ConnectionBBDD connection = ConnectionBBDD.getInstance();

		Casella[][] graella = sudoku.getGraella();
		
		String sql = "UPDATE CASELLA SET VALUE = ?, ISRESTRICTED = ? WHERE USERNAME = ? AND ID = ? AND COORDX = ? AND COORDY = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.clearParameters();
		preparedStatement.setString(3, sudoku.getNomJugador());
		preparedStatement.setInt(4, sudoku.getIdPartida());
		
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				Casella c = graella[x][y];
				preparedStatement.setInt(1, c.getValor());
				preparedStatement.setBoolean(2, !c.getModificable());
				preparedStatement.setInt(5, x + 1);
				preparedStatement.setInt(6, y + 1);
				preparedStatement.executeQuery();

			}
		}
	}

	public void insertCaselles(Sudoku sudoku) throws Exception {

		ConnectionBBDD connection = ConnectionBBDD.getInstance();

		Casella[][] graella = sudoku.getGraella();
		String sql = "INSERT INTO CASELLA VALUES (?,?,?,?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.clearParameters();
		preparedStatement.setString(1, sudoku.getNomJugador());
		preparedStatement.setInt(2, sudoku.getIdPartida());
		
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				Casella c = graella[x][y];
				preparedStatement.setInt(3, x + 1);
				preparedStatement.setInt(4, y + 1);
				preparedStatement.setInt(5, c.getValor());
				preparedStatement.setBoolean(6, !c.getModificable());
				preparedStatement.executeQuery();
			}
		}
	}
	public Taulell getTaulell(Sudoku sudoku) throws Exception {

		Taulell t = new Taulell(true);
		
		ConnectionBBDD connection = ConnectionBBDD.getInstance();

		String sql = "SELECT COORDX,COORDY,VALUE,ISRESTRICTED FROM CASELLA WHERE USERNAME = ? AND ID = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.clearParameters();
		preparedStatement.setString(1, sudoku.getNomJugador());
		preparedStatement.setInt(2, sudoku.getIdPartida());
		ResultSet rs = preparedStatement.executeQuery();
		
		while(rs.next()) {
			t.addValor(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4));
		}	
		return t;
	}
}
