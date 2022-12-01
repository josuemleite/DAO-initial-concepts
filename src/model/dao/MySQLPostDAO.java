package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.Post;
import model.User;

public class MySQLPostDAO implements PostDAO {

	@Override
	public boolean save(Post post) throws ModelException {

		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO posts VALUES (DEFAULT, ?, CURDATE(), ?);";

		db.prepareStatement(sqlInsert);

		db.setString(1, post.getContent());

		db.setInt(2, post.getUser().getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Post post) {

		Connection connection = new MySQLConnectionFactory().getConnection();

		if (connection == null)
			return false;

		PreparedStatement preparedStatement = null;

		String sqlUpdate = "UPDATE posts SET content = ? WHERE user_id = ?;";

		try {
			preparedStatement = connection.prepareStatement(sqlUpdate);

			preparedStatement.setString(1, post.getContent());

			preparedStatement.setInt(2, post.getUser().getId());

			int rowsAffected = preparedStatement.executeUpdate();

			return rowsAffected > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();

				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	@Override
	public boolean delete(Post post) {

		Connection connection = new MySQLConnectionFactory().getConnection();

		if (connection == null)
			return false;

		PreparedStatement preparedStatement = null;

		String sqlDelete = "DELETE FROM posts WHERE id = ?;";

		try {
			preparedStatement = connection.prepareStatement(sqlDelete);

			preparedStatement.setInt(1, post.getId());

			int numberRowsAffected = preparedStatement.executeUpdate();

			return numberRowsAffected > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();

				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	@Override
	public List<Post> listAll() {

		Connection connection = new MySQLConnectionFactory().getConnection();
		Statement statement = null;
		List<Post> posts = new ArrayList<Post>();

		if (connection == null)
			return posts;

		String sqlQuery = "SELECT u.id AS user_id, u.nome, p.* FROM users u INNER JOIN posts p ON u.id = p.user_id;";

		ResultSet rs = null;

		try {
			statement = connection.createStatement();

			rs = statement.executeQuery(sqlQuery);

			while (rs.next()) {
				String userName = rs.getString("nome");

				Post p = new Post(rs.getInt("id"));
				p.setContent(rs.getString("content"));
				p.setPostDate(rs.getDate("post_date"));

				User user = new User(rs.getInt("user_id"));
				user.setName(rs.getString("nome"));
				p.setUser(user);

				posts.add(p);
			}

			return posts;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();

				if (statement != null)
					statement.close();

				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return posts;
	}
}
