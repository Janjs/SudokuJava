package persistencia;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

import domini.Sudoku;

import persistencia.CasellaBBDD;

public class SudokuBBDD {

	private CasellaBBDD casellaBBDD = new CasellaBBDD();

	public void insert(Sudoku sudoku) throws Exception {

		ConnectionBBDD connection = ConnectionBBDD.getInstance();

		String sql = "INSERT INTO SUDOKU VALUES (?,?,?)";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.clearParameters();
		preparedStatement.setString(1, sudoku.getNomJugador());
		preparedStatement.setInt(2, sudoku.getIdPartida());
		preparedStatement.setTimestamp(3, new Timestamp(sudoku.getTime()));
		preparedStatement.executeQuery();

		casellaBBDD.insertCaselles(sudoku);
	}

	public void update(Sudoku sudoku) throws Exception {
		casellaBBDD.updateCaselles(sudoku);
	}

	public void delete(Sudoku sudoku) throws Exception {

		ConnectionBBDD connection = ConnectionBBDD.getInstance();

		String sql = "DELETE FROM SUDOKU WHERE ID = ? AND USERNAME = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.clearParameters();
		preparedStatement.setInt(1, sudoku.getIdPartida());
		preparedStatement.setString(2, sudoku.getNomJugador());
		preparedStatement.executeQuery();
	}
}