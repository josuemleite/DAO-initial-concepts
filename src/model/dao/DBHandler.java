package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.ModelException;

public class DBHandler {
	private Connection connection = null;
	private PreparedStatement preparedStatement;

	public DBHandler() throws ModelException {
		connection = MySQLConnectionFactory.getConnection();
	}

	public void prepareStatement(String sqlInsert) throws ModelException {
		try {
			preparedStatement = connection.prepareStatement(sqlInsert);
		} catch (SQLException e) {
			throw new ModelException("Erro ao preparar a SQL", e);
		}
	}
	
	public void setString(int i, String value) throws ModelException {
		try {
			preparedStatement.setString(i, value);
		} catch (SQLException e) {
			throw new ModelException("Erro ao atribuir string", e);
		}
	}

	public void setInt(int i, int value) throws ModelException {
		try {
			preparedStatement.setInt(i, value);
		} catch (SQLException e) {
			throw new ModelException("Erro ao atribuir inteiro", e);
		}
	}

	public int executeUpdate() throws ModelException {
		try {
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new ModelException("Erro ao executar SQL", e);
		}
	}
}
