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
	public boolean update(Post post) throws ModelException {

		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE posts SET content = ? WHERE user_id = ?;";

		db.prepareStatement(sqlUpdate);
		db.setString(1, post.getContent());
		db.setInt(2, post.getUser().getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Post post) throws ModelException {

		DBHandler db = new DBHandler();

		String sqlDelete = "DELETE FROM posts WHERE id = ?;";

		db.prepareStatement(sqlDelete);
		db.setInt(1, post.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public List<Post> listAll() throws ModelException {

		DBHandler db = new DBHandler();

		List<Post> posts = new ArrayList<Post>();

		String sqlQuery = "SELECT u.id AS user_id, u.nome, p.* FROM users u INNER JOIN posts p ON u.id = p.user_id;";

		db.createStatement();

		db.executeQuery(sqlQuery);

		while (db.next()) {
			Post p = new Post(db.getInt("id"));
			p.setContent(db.getString("content"));
			p.setPostDate(db.getDate("post_date"));

			User user = new User(db.getInt("user_id"));
			user.setName(db.getString("nome"));
			p.setUser(user);

			posts.add(p);
		}
		
		return posts;
	}
	
	@Override
	public Post findById(int id) throws ModelException {
		
		DBHandler db = new DBHandler();
		
		String sql = "SELECT * FROM posts WHERE id = ?;";
		
		db.prepareStatement(sql);
		db.setInt(1, id);
		db.executeQuery();
		
		Post p = null;
		while (db.next()) {
			p = new Post(db.getInt("id"));
			p.setContent(db.getString("content"));
			p.setPostDate(db.getDate("post_date"));
			
			User u = new User(db.getInt("user_id"));
			p.setUser(u);
			break;
		}
		
		return p;
	}
}