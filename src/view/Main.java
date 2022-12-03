package view;

import model.ModelException;
import model.Post;
import model.User;
import model.dao.MySQLPostDAO;
import model.dao.PostDAO;

public class Main {
	public static void main(String[] args) throws ModelException {
		Post p = new Post(11);
//		p.setContent("Primeiro post via DAO 3");
		
//		User u = new User(1);
//		p.setUser(u);
		
		PostDAO dao = new MySQLPostDAO();
//		dao.save(p);
		
//		p.setContent("Primeiro post via DAO atualizado");
//		dao.update(p);
		
		dao.delete(p);
		
		for (Post post : dao.listAll())
			System.out.printf("'%s' postou '%s' em '%tA, %<te de %<tB de %<tY' %n", post.getUser().getName(), post.getContent(), post.getPostDate());
	}
}
