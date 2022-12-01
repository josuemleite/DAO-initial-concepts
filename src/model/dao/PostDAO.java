package model.dao;

import java.util.List;

import model.Post;

public interface PostDAO {
	boolean save(Post post);
	boolean update(Post post);
	boolean delete(Post post);
	List<Post> listAll();
}
